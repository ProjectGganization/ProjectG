package io.ggroup.demo.integration;

import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;

import java.time.LocalDateTime;

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

    @Autowired
    private PostalCodeRepository postalCodeRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EventStatusRepository eventStatusRepository;

    // Laskuri, joka luo uniikkeja sähköposteja. Estää myös duplikaatit.
    private static int counter = 0;

    // Test Customer luonti ja tallentaminen tietokantaan
    public Customer createPersistedCustomer() {
        Customer customer = new Customer();
        customer.setFirstname("Test");
        customer.setLastname("Customer");
        customer.setEmail("customer" + (++counter) + "@test.com");
        customer.setPhone("000000000");

        return customerRepository.save(customer);
    }

    // Test Seller luonti ja tallentaminen tietokantaan
    // Täytyy luoda user, koska seller tarvitsee aina käyttäjätilin, toisin kuin customer
    // siksi tarvii user ja accountstatus
    public Seller createPersistedSeller() {
        AccountStatus status = new AccountStatus();
        status.setAccountStatus("active");
        accountStatusRepository.save(status);

        User user = new User();
        user.setEmail("seller" + (++counter) + "@test.com");
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

    // Postinumero, venue ja event luonti
    public PostalCode createPersistedPostalCode() {
        PostalCode postalCode = new PostalCode();
        postalCode.setPostalCode("00100");
        postalCode.setCity("Helsinki");

        return postalCodeRepository.save(postalCode);
    }

    public Venue createPersistedVenue() {
        Venue venue = new Venue();
        venue.setName("Ruttis");
        venue.setAddress("Oman onnen tie");
        venue.setPostalCode((createPersistedPostalCode()));

        return venueRepository.save(venue);
    }

    public Category createPersistedCategory() {
        
        return categoryRepository.save(new Category("music"));
    }


    public EventStatus createPersistedEventStatus() {
        EventStatus status = new EventStatus("upcoming");
            
        return eventStatusRepository.save(status);
    }

    public Event createPersistedEvent(String title) {
        Event event = new Event();
        event.setTitle(title);
        event.setCategory(createPersistedCategory());
        event.setEventStatus(createPersistedEventStatus());
        event.setDescription("very good very nice");
        event.setStartTime(LocalDateTime.now().plusDays(1));
        event.setEndTime(LocalDateTime.now().plusDays(2));
        event.setVenue(createPersistedVenue());

        return eventRepository.save(event);
    }
}