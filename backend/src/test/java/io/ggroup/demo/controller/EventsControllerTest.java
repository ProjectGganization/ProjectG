package io.ggroup.demo.controller;

import io.ggroup.demo.model.Event;
import io.ggroup.demo.repository.EventRepository;
import io.ggroup.demo.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventsControllerTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private EventsController eventsController;

    @Test
    void getEventCount_returnsCount() {
        when(eventRepository.count()).thenReturn(5L);

        ResponseEntity<Long> response = eventsController.getEventCount();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(5L);
    }

    @Test
    void getAllEvents_whenEventsExist_returnsOk() {
        Event event = new Event();
        when(eventRepository.findAll()).thenReturn(List.of(event));

        ResponseEntity<?> response = eventsController.getAllEvents();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getAllEvents_whenNoEvents_returnsNotFound() {
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = eventsController.getAllEvents();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
