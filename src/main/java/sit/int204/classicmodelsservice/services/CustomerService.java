package sit.int204.classicmodelsservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.classicmodelsservice.dtos.NewCustomerDto;
import sit.int204.classicmodelsservice.dtos.SimpleCustomerDTO;
import sit.int204.classicmodelsservice.entities.Customer;
import sit.int204.classicmodelsservice.entities.Order;
import sit.int204.classicmodelsservice.repositories.CustomerRepository;
import sit.int204.classicmodelsservice.utils.ListMapper;

import java.util.List;
import java.util.Set;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ListMapper listMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findByCustomerNumber(id);
    }

    public Customer findById(Integer id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer id " + id + "Does Not Exist !!!")
        );
    }


    public Set<Order> getCustomerOrderById(Integer id) {
        Customer foundCustomer = customerRepository.findByCustomerNumber(id);
        return foundCustomer.getOrders();
    }

    public NewCustomerDto createCustomer(NewCustomerDto newCustomer) {
        if (customerRepository.existsById(newCustomer.getCustomerNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate customer for id " + newCustomer.getCustomerNumber());
        }
        Customer customer = mapper.map(newCustomer, Customer.class);
        return mapper.map(customerRepository.saveAndFlush(customer), NewCustomerDto.class);
    }

    public List<NewCustomerDto> getAllCustomers() {
        return listMapper.mapList(customerRepository.findAll(), NewCustomerDto.class, mapper);
    }

}