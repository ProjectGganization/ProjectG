package io.ggroup.demo.integration;

import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
    Tää luokka luo testidataa integraatiotestejä varten.
    Samaa testidataa voi käyttää monessa eri testissä.
*/
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

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    private static final AtomicInteger counter = new AtomicInteger(0);

    private int next() {
        return counter.incrementAndGet();
    }

    public Customer createPersistedCustomer() {
        int id = next();

        Customer customer = new Customer();
        customer.setFirstname("Test");
        customer.setLastname("Customer");
        customer.setEmail("customer" + id + "@test.com");
        customer.setPhone("04000000" + id);

        return customerRepository.save(customer);
    }

    public AccountStatus createPersistedAccountStatus() {
        return accountStatusRepository.findById("active")
                .orElseGet(() -> accountStatusRepository.save(new AccountStatus("active")));
    }

    public User createPersistedUser() {
        int id = next();

        User user = new User();
        user.setEmail("user" + id + "@test.com");
        user.setPasswordHash("password");

        return userRepository.save(user);
    }

    public Seller createPersistedSeller() {
        int id = next();

        User user = new User();
        user.setEmail("selleruser" + id + "@test.com");
        user.setPasswordHash("password");
        User savedUser = userRepository.save(user);

        Seller seller = new Seller();
        seller.setName("Test Seller " + id);
        seller.setEmail("seller" + id + "@test.com");
        seller.setPhone("05000000" + id);
        seller.setUser(savedUser);

        return sellerRepository.save(seller);
    }

    public PostalCode createPersistedPostalCode() {
        return postalCodeRepository.findById("00100")
                .orElseGet(() -> {
                    PostalCode postalCode = new PostalCode();
                    postalCode.setPostalCode("00100");
                    postalCode.setCity("Helsinki");
                    return postalCodeRepository.save(postalCode);
                });
    }

    public Venue createPersistedVenue() {
        int id = next();

        Venue venue = new Venue();
        venue.setName("Ruttis " + id);
        venue.setAddress("Oman onnen tie " + id);
        venue.setPostalCode(createPersistedPostalCode());

        return venueRepository.save(venue);
    }

    public Category createPersistedCategory() {
        int id = next();
        return categoryRepository.save(new Category("music-" + id));
    }

    public EventStatus createPersistedEventStatus() {
        int id = next();
        return eventStatusRepository.save(new EventStatus("upcoming-" + id));
    }

    public PaymentMethod createPersistedPaymentMethod() {
        return paymentMethodRepository.findById("card")
            .orElseGet(() ->
                paymentMethodRepository.save(new PaymentMethod("card"))
            );
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
        event.setSeller(createPersistedSeller());

        return eventRepository.save(event);
    }

    public Order createPersistedOrder() {
        Customer customer = createPersistedCustomer();

        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(LocalDateTime.now());
        order.setIsPaid(true);
        order.setIsRefunded(false);

        return order;
    }
}