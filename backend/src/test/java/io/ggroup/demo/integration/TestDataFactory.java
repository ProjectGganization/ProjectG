package io.ggroup.demo.integration;

import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* Tää luokka luo testidataa integraatiotesteja varten. 
Samaa testidataa voi sitten käyttää monessa eri testissä  */

@Component
public class TestDataFactory {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    // Laskuri, joka luo uniikkeja sähköposteja. Estää myös duplikaatit.
    private static int counter = 0;

    // Test Customer luonti ja tellentaminen tietokantaan
    public Customer createPersistedCustomer() {
        Customer customer = new Customer();
        customer.setFirstname("Test");
        customer.setLastname("Customer");
        customer.setEmail("customer" + (++counter) + "@test.com");
        customer.setPhone("000000000");

        return customerRepository.save(customer);
    }

    // Test Seller luonti ja tellentaminen tietokantaan
    // Täytyy luoda user, koska seller tarvitsee aina käyttäjätilin, toisin kuin customer
    // siksi tarvii user ja accountstatus
    public Seller createPersistedSeller() {
        AccountStatus status = new AccountStatus();
        status.setAccountStatus("active");
        accountStatusRepository.save(status);

        User user = new User();
        user.setEmail("seller" + counter + "@test.com");
        user.setPasswordHash("password");
        user.setAccountStatus(status);
        userRepository.save(user);

        Seller seller = new Seller();
        seller.setName("Test Seller");
        seller.setEmail("seller" + counter + "@test.com");
        seller.setPhone("000000000");
        seller.setUser(user);

        return sellerRepository.save(seller);
    }
}
