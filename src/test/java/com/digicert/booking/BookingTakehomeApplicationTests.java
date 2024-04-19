package com.digicert.booking;

import com.digicert.booking.dto.BaseBookingDTO;
import com.digicert.booking.dto.CreateBookingDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = BookingTakehomeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookingTakehomeApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @Order(1)
    public void getAllBookings_shouldReturnAnEmptyList() {
        ResponseEntity<List> entity = this.restTemplate.getForEntity("/bookings",
                List.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).hasSize(0);
    }

    @Test
    @Order(2)
    public void createBooking_shouldSucceed() {
        CreateBookingDTO bookingRequestDTO = createSampleBookingDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateBookingDTO> request = new HttpEntity<>(bookingRequestDTO, headers);

        ResponseEntity<BaseBookingDTO> entity = this.restTemplate.postForEntity(
                "/bookings", request, BaseBookingDTO.class
        );
        BaseBookingDTO responseBookingDTO = entity.getBody();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBookingDTO.getId()).isNotNull();
        assertThat(responseBookingDTO.getCustomerEmail()).isEqualTo("test@email.com");
    }

    @Test
    @Order(3)
    public void getBooking_shouldSucceed() {
        Map<String, Long> urlVariablesMap = new HashMap<>();
        urlVariablesMap.put("id", 1L);
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/bookings/{id}",
                String.class, urlVariablesMap);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(4)
    public void getAllBookings_shouldReturnAListWithOneItem() {
        ResponseEntity<List> entity = this.restTemplate.getForEntity("/bookings",
                List.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).hasSize(1);
    }

    // Delete test
    @Test
    @Order(5)
    public void getBooking_shouldReturn404() {
        Map<String, Long> urlVariablesMap = new HashMap<>();
        urlVariablesMap.put("id", 1L);

        this.restTemplate.delete("/bookings/{id}", urlVariablesMap);
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/bookings/{id}",
                String.class, urlVariablesMap);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isEqualTo("We've run into an issue. Booking not found");
    }


    private CreateBookingDTO createSampleBookingDTO() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
        String checkInDateString = "18-04-2024";
        String checkOutDateString = "05-05-2024";

        try {
            CreateBookingDTO bookingRequestDTO = new CreateBookingDTO();
            bookingRequestDTO.setCustomerEmail("test@email.com");
            bookingRequestDTO.setCheckInDate(formatter.parse(checkInDateString));
            bookingRequestDTO.setCheckOutDate(formatter.parse(checkOutDateString));
            bookingRequestDTO.setNumAdults(1);
            bookingRequestDTO.setNumBeds(1);


            return bookingRequestDTO;
        } catch (ParseException e) {
            fail("Failed to create booking DTO");
            return null;
        }
    }

}
