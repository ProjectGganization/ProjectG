package io.ggroup.demo.E2E;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import io.ggroup.demo.model.AccountStatus;
import io.ggroup.demo.model.Category;
import io.ggroup.demo.model.Event;
import io.ggroup.demo.model.EventStatus;
import io.ggroup.demo.model.PostalCode;
import io.ggroup.demo.model.Seller;
import io.ggroup.demo.model.Ticket;
import io.ggroup.demo.model.TicketType;
import io.ggroup.demo.model.User;
import io.ggroup.demo.model.Venue;
import io.ggroup.demo.repository.AccountStatusRepository;
import io.ggroup.demo.repository.CategoryRepository;
import io.ggroup.demo.repository.EventRepository;
import io.ggroup.demo.repository.EventStatusRepository;
import io.ggroup.demo.repository.PostalCodeRepository;
import io.ggroup.demo.repository.SellerRepository;
import io.ggroup.demo.repository.TicketRepository;
import io.ggroup.demo.repository.TicketTypeRepository;
import io.ggroup.demo.repository.UserRepository;
import io.ggroup.demo.repository.VenueRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SellerEventFlowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PostalCodeRepository postalCodeRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EventStatusRepository eventStatusRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("Seller can search event and create ticket")
    void sellerCanSearchEventAndCreateTicket() throws Exception {

        long ticketsBefore = ticketRepository.count();
        String unique = UUID.randomUUID().toString().substring(0, 8);

        AccountStatus accountStatus = accountStatusRepository.findById("active")
                .orElseGet(() -> accountStatusRepository.save(new AccountStatus("active")));

        User user = new User();
        user.setEmail("selleruser_" + unique + "@test.com");
        user.setPasswordHash("password");
        user.setAccountStatus(accountStatus);
        User savedUser = userRepository.save(user);

        Seller seller = new Seller();
        seller.setName("Test Seller " + unique);
        seller.setEmail("seller_" + unique + "@test.com");
        seller.setPhone("0401234567");
        seller.setUser(savedUser);
        sellerRepository.save(seller);

        PostalCode postalCode = postalCodeRepository.findById("00100")
                .orElseGet(() -> {
                    PostalCode pc = new PostalCode();
                    pc.setPostalCode("00100");
                    pc.setCity("Helsinki");
                    return postalCodeRepository.save(pc);
                });

        Venue venue = new Venue();
        venue.setName("Test Venue " + unique);
        venue.setAddress("Testikatu 1");
        venue.setPostalCode(postalCode);
        Venue savedVenue = venueRepository.save(venue);

        Category category = categoryRepository.save(new Category("music-" + unique));
        EventStatus eventStatus = eventStatusRepository.save(new EventStatus("upcoming-" + unique));

        Event event = new Event();
        event.setTitle("Random Test Event " + unique);
        event.setDescription("Test description");
        event.setStartTime(LocalDateTime.now().plusDays(1));
        event.setEndTime(LocalDateTime.now().plusDays(2));
        event.setVenue(savedVenue);
        event.setCategory(category);
        event.setEventStatus(eventStatus);
        Event savedEvent = eventRepository.save(event);

        mockMvc.perform(get("/api/events")
                .with(user("seller@test.com").roles("SELLER"))
                .param("query", "Random"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(savedEvent.getTitle())));

        ticketTypeRepository.findById("VIP")
                .orElseGet(() -> ticketTypeRepository.save(new TicketType("VIP")));

        String ticketJson = """
                {
                  "ticketType": {
                    "ticketType": "VIP"
                  },
                  "event": {
                    "eventId": %d
                  },
                  "unitPrice": 49.90,
                  "inStock": 100,
                  "orderLimit": 4
                }
                """.formatted(savedEvent.getEventId());

        mockMvc.perform(post("/api/tickets")
                .with(user("seller@test.com").roles("SELLER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isCreated());

        assertThat(ticketRepository.count()).isEqualTo(ticketsBefore + 1);

        Ticket savedTicket = ticketRepository.findAll().get((int) ticketRepository.count() - 1);
        assertThat(savedTicket).isNotNull();
        assertThat(savedTicket.getEvent().getEventId()).isEqualTo(savedEvent.getEventId());
        assertThat(savedTicket.getTicketType().getTicketType()).isEqualTo("VIP");
        assertThat(savedTicket.getInStock()).isEqualTo(100);
        assertThat(savedTicket.getOrderLimit()).isEqualTo(4);
    }
}