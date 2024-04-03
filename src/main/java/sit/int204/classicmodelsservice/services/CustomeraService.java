package sit.int204.classicmodelsservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sit.int204.classicmodelsservice.dtos.SimpleCustomerDTO;
import sit.int204.classicmodelsservice.entities.Customer;
import sit.int204.classicmodelsservice.entities.Customera;
import sit.int204.classicmodelsservice.repositories.CustomeraRepository;

import java.util.List;

@Service
public class CustomeraService {
    @Autowired
    private CustomeraRepository customeraRepository;



    public List<Customera> insertCustomers(List<Customera> customeras) {
        return customeraRepository.saveAll(customeras);
    }
}
