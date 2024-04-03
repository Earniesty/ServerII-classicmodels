package sit.int204.classicmodelsservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sit.int204.classicmodelsservice.dtos.VerySimpleProductDTO;
import sit.int204.classicmodelsservice.entities.Product;
import sit.int204.classicmodelsservice.exceptions.ItemNotFoundException;
import sit.int204.classicmodelsservice.repositories.ProductRepository;
import sit.int204.classicmodelsservice.utils.ListMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ListMapper listMapper;

//    public List<VerySimpleProductDTO> getProductByExample() {
//        Product productExample = new Product();
//        productExample.setProductLine("Classic Cars");
//        List<Product> productList = productRepository.findAll(Example.of(productExample));
//        return listMapper.mapList(productList, VerySimpleProductDTO.class, modelMapper);
//    }

public List<Product> getProductByExample() {
    Product productExample = new Product();
    productExample.setProductLine("Classic Cars");
    List<Product> productList = productRepository.findAll(Example.of(productExample));
    return productList; 
}

    public List<Product> getProductsByCategory(String category) {
//        return productRepository.findByProductLineStartingWith(category);
        return productRepository.getProductbyCategory(category + "%");
    }

    public Page<Product> getProducts(String partOfProduct, Double lower, Double upper, String[] sortBy, String[] direction, int pageNo, int pageSize) {
        if (lower <= 0 && upper <= 0) {
            upper = productRepository.findFirstByOrderByPriceDesc().getPrice();
        }
        if (lower > upper) {

            double tmp = lower;
            lower = upper;
            upper = tmp;
        }
//        Sort.Order sortOrder = null;
        if (sortBy != null && sortBy.length > 0) {
            List<Sort.Order> sortOrderList = new ArrayList<>();
            for (int i = 0; i < sortBy.length; i++) {
                sortOrderList.add(new Sort.Order(Sort.Direction.valueOf(direction[i].toUpperCase()), sortBy[i]));
            }
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrderList));
            return productRepository.findByPriceBetweenAndProductNameContains(lower, upper, partOfProduct, pageable);

        } else {
            Pageable pageable = PageRequest.of(pageNo, pageSize);

            return productRepository.findByPriceBetweenAndProductNameContains(lower, upper, partOfProduct, pageable);

        }
    }


    public List<Product> getProducts(String partOfProduct, Double lower, Double upper, String[] sortBy, String[] direction) {
        if (lower <= 0 && upper <= 0) {
            upper = productRepository.findFirstByOrderByPriceDesc().getPrice();
        }
        if (lower > upper) {
            double tmp = lower;
            lower = upper;
            upper = tmp;
        }
//        Sort.Order sortOrder = null;
        if (sortBy != null && sortBy.length > 0) {
            List<Sort.Order> sortOrderList = new ArrayList<>();
            for (int i = 0; i < sortBy.length; i++) {
                sortOrderList.add(new Sort.Order(Sort.Direction.valueOf(direction[i].toUpperCase()), sortBy[i]));
            }
            return productRepository.findByPriceBetweenAndProductNameContains(lower, upper, partOfProduct, Sort.by(sortOrderList));

        } else {
            return productRepository.findByPriceBetweenAndProductNameContains(lower, upper, partOfProduct);

        }
    }

    public List<Product> getProducts(String partOfProduct, Double lower, Double upper) {
        return getProducts(partOfProduct, lower, upper, null, null);
    }

    public Product getProductById(String productCode) {
        return productRepository.findById(productCode).orElseThrow(() ->
                new ItemNotFoundException("Product code: " +
                        productCode + " does not exists !!!"));


    }


}
