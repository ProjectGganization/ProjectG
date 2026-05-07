package io.ggroup.demo.config;

import io.ggroup.demo.model.*;
import io.ggroup.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
@Profile("h2")
public class H2DataSeeder {

    @Bean
    @Order(2) // Runs after TestLoginConfig (@Order(1) by default)
    public CommandLineRunner seedH2Data(
            CategoryRepository categoryRepository,
            EventStatusRepository eventStatusRepository,
            PostalCodeRepository postalCodeRepository,
            VenueRepository venueRepository,
            SellerRepository sellerRepository,
            EventRepository eventRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository,
            PaymentMethodRepository paymentMethodRepository) {

        return args -> {

            // --- Payment methods ---
            if (paymentMethodRepository.count() == 0) {
                paymentMethodRepository.save(new PaymentMethod("card"));
                paymentMethodRepository.save(new PaymentMethod("cash"));
                paymentMethodRepository.save(new PaymentMethod("bank"));
                System.out.println("H2 seeder: payment methods created");
            }

            // --- Categories ---
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Music"));
                categoryRepository.save(new Category("Sports"));
                categoryRepository.save(new Category("Theater"));
                categoryRepository.save(new Category("Art & Gallery"));
                System.out.println("H2 seeder: categories created");
            }

            // --- Event statuses ---
            if (eventStatusRepository.count() == 0) {
                eventStatusRepository.save(new EventStatus("upcoming"));
                eventStatusRepository.save(new EventStatus("ongoing"));
                eventStatusRepository.save(new EventStatus("cancelled"));
                eventStatusRepository.save(new EventStatus("finished"));
                System.out.println("H2 seeder: event statuses created");
            }

            // --- Postal codes ---
            if (postalCodeRepository.count() == 0) {
                postalCodeRepository.save(new PostalCode("00100", "Helsinki"));
                postalCodeRepository.save(new PostalCode("33100", "Tampere"));
                postalCodeRepository.save(new PostalCode("20100", "Turku"));
                System.out.println("H2 seeder: postal codes created");
            }

            // --- Venues ---
            if (venueRepository.count() == 0) {
                PostalCode hki = postalCodeRepository.findById("00100").orElseThrow();
                PostalCode tre = postalCodeRepository.findById("33100").orElseThrow();

                venueRepository.save(new Venue("Hartwall Arena", "Areenankuja 1", hki));
                venueRepository.save(new Venue("Nokia Arena", "Kansalaistori 1", tre));
                venueRepository.save(new Venue("Olympiastadion", "Paavo Nurmen tie 1", hki));
                System.out.println("H2 seeder: venues created");
            }

            // --- Seller (linked to seller@test.com created by TestLoginConfig) ---
            if (sellerRepository.count() == 0) {
                userRepository.findByEmail("seller@test.com").ifPresent(sellerUser -> {
                    Seller seller = new Seller();
                    seller.setName("Test Seller");
                    seller.setEmail("seller@test.com");
                    seller.setPhone("+358401234567");
                    seller.setUser(sellerUser);
                    sellerRepository.save(seller);
                    System.out.println("H2 seeder: seller created");
                });
            }

            // --- Events & Tickets ---
            if (eventRepository.count() == 0) {
                Seller seller = sellerRepository.findAll().get(0);
                Category music = categoryRepository.findById("Music").orElseThrow();
                Category sports = categoryRepository.findById("Sports").orElseThrow();
                Category theater = categoryRepository.findById("Theater").orElseThrow();
                EventStatus upcoming = eventStatusRepository.findById("upcoming").orElseThrow();
                Venue hartwall = venueRepository.findAll().get(0);
                Venue nokia = venueRepository.findAll().get(1);
                Venue olympia = venueRepository.findAll().get(2);

                // Event 1: Music festival
                Event e1 = new Event(
                    "Summer Music Festival",
                    "The biggest outdoor music event of the year featuring top international artists.",
                    null,
                    LocalDateTime.of(2026, 7, 15, 18, 0),
                    LocalDateTime.of(2026, 7, 15, 23, 0),
                    upcoming, hartwall, music, seller
                );
                eventRepository.save(e1);
                ticketRepository.save(new Ticket("General Admission", e1, new BigDecimal("49.00"), 500, 6));
                ticketRepository.save(new Ticket("VIP", e1, new BigDecimal("149.00"), 50, 2));

                // Event 2: Football
                Event e2 = new Event(
                    "HJK vs FC Inter",
                    "Finnish Premier League derby at Olympiastadion.",
                    null,
                    LocalDateTime.of(2026, 8, 3, 19, 0),
                    LocalDateTime.of(2026, 8, 3, 21, 0),
                    upcoming, olympia, sports, seller
                );
                eventRepository.save(e2);
                ticketRepository.save(new Ticket("General Admission", e2, new BigDecimal("25.00"), 1000, 4));
                ticketRepository.save(new Ticket("Tribune A", e2, new BigDecimal("45.00"), 200, 4));

                // Event 3: Theater
                Event e3 = new Event(
                    "Hamlet — Shakespeare Revisited",
                    "A modern interpretation of Shakespeare's timeless tragedy.",
                    null,
                    LocalDateTime.of(2026, 9, 20, 19, 30),
                    LocalDateTime.of(2026, 9, 20, 22, 0),
                    upcoming, nokia, theater, seller
                );
                eventRepository.save(e3);
                ticketRepository.save(new Ticket("Standard", e3, new BigDecimal("35.00"), 300, 6));
                ticketRepository.save(new Ticket("Premium", e3, new BigDecimal("65.00"), 80, 2));

                System.out.println("H2 seeder: 3 events + 6 tickets created");
            }
        };
    }
}
