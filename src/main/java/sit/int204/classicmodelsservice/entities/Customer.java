package sit.int204.classicmodelsservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private Integer customerNumber;
    private String customerName;
    private String contactLastName;
    private String contactFirstName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    @ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employee salesEmployee;
    private Double creditLimit;
    @OneToMany(mappedBy = "customerNumber")
    private Set<Order> orders = new LinkedHashSet<>();
    
    private String password;
    private String role;
}
