package sit.int204.classicmodelsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int204.classicmodelsservice.entities.Customera;
import sit.int204.classicmodelsservice.services.CustomeraService;

import java.util.List;

@RestController
@RequestMapping("/customeras/")
public class CustomeraController {
    private CustomeraService customeraService;

    @Autowired
    public CustomeraController(CustomeraService customeraService) {
        this.customeraService = customeraService;
    }

    @PostMapping("")
    public List<Customera> insertCustomers(@RequestBody List<Customera> customeras) {
        return  customeraService.insertCustomers(customeras);
    }
}
