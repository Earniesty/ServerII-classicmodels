package sit.int204.classicmodelsservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import sit.int204.classicmodelsservice.dtos.VerySimpleProductDTO;
import sit.int204.classicmodelsservice.entities.Product;
import sit.int204.classicmodelsservice.exceptions.ErrorResponse;
import sit.int204.classicmodelsservice.exceptions.ItemNotFoundException;
import sit.int204.classicmodelsservice.services.ProductService;
import sit.int204.classicmodelsservice.utils.ListMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    @GetMapping("/queryByExample")
    public ResponseEntity<Object> findByExample() {
        return ResponseEntity.ok(productService.getProductByExample());
    }


    @GetMapping("")
    public ResponseEntity<Object> findAllProducts(
            @RequestParam(defaultValue = "0") Double lower,
            @RequestParam(defaultValue = "0") Double upper,
            @RequestParam(defaultValue = "") String partOfProductName,
            @RequestParam(defaultValue = "") String[] sortBy,
            @RequestParam(defaultValue = "ASC") String[] direction,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize

    ) {
//        String response = "lower=" + lower + "\nupper" + upper + "\npartOfProduct" + partOfProductName;
        Page<Product> productPage = productService.getProducts(partOfProductName, lower, upper, sortBy, direction, pageNo, pageSize);
        List<VerySimpleProductDTO> verySimpleProduct = productPage.stream().map(p -> modelMapper.map(p, VerySimpleProductDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(listMapper.toPageDTO(productPage, VerySimpleProductDTO.class, modelMapper));
    }

    @GetMapping("/product-line/{id}")
    public ResponseEntity<Object> getProductsByCategory(@PathVariable String id) {
        List<Product> productList = productService.getProductsByCategory(id);
        List<VerySimpleProductDTO> vsp = productList.stream().map(p -> modelMapper.map(p, VerySimpleProductDTO.class)).collect(Collectors.toList());

        return ResponseEntity.ok(vsp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleItemNotFound(ItemNotFoundException exception, WebRequest request) {
        ErrorResponse er = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }




}
