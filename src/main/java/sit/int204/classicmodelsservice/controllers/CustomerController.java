package sit.int204.classicmodelsservice.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.classicmodelsservice.dtos.NewCustomerDto;
import sit.int204.classicmodelsservice.dtos.SimpleCustomerDTO;
import sit.int204.classicmodelsservice.entities.Customer;
import sit.int204.classicmodelsservice.entities.Order;
import sit.int204.classicmodelsservice.services.CustomerService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customers/")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @GetMapping("")
//    public List<Customer> getAllCustomer() {
//        return customerService.getAllCustomer();
//    }

//    @GetMapping("/{id}")
//    public Customer getCustomerById(@PathVariable Integer id) {
//        return customerService.getCustomerById(id);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.findById(id);
        SimpleCustomerDTO simpleCustomerDTO = modelMapper.map(customer, SimpleCustomerDTO.class);
        return ResponseEntity.ok(simpleCustomerDTO);
    }

    @GetMapping("/{id}/orders")
    public Set<Order> getCustomerOrderById(@PathVariable Integer id) {
        return customerService.getCustomerOrderById(id);
    }

    @GetMapping
    public List<NewCustomerDto> getCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("")
    public NewCustomerDto createCustomer(@Valid @RequestBody NewCustomerDto newCustomer) {
        return customerService.createCustomer(newCustomer);
    }
}
