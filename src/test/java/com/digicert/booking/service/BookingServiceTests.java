package com.digicert.booking.service;

import com.digicert.booking.dto.BaseBookingDTO;
import com.digicert.booking.dto.CreateBookingDTO;
import com.digicert.booking.dto.UpdateBookingDTO;
import com.digicert.booking.entity.BookingEntity;
import com.digicert.booking.repository.BookingRepository;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTests {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;


    private BookingEntity createTestBookingEntity(Long id, BookingEntity.BookingStatus status) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
        String checkInDateString = "18-04-2024";
        String checkOutDateString = "05-05-2024";

        try {
            BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setId(id == null ? 0L : id);
            bookingEntity.setCustomerEmail("test@email.com");
            bookingEntity.setCheckInDate(formatter.parse(checkInDateString));
            bookingEntity.setCheckOutDate(formatter.parse(checkOutDateString));
            bookingEntity.setStatus(status != null ? status : BookingEntity.BookingStatus.PENDING);
            bookingEntity.setNumAdults(1);
            bookingEntity.setNumBeds(1);

            bookingEntity.setCreateDate(formatter.parse(checkInDateString));
            bookingEntity.setModifiedDate(formatter.parse(checkInDateString));

            return bookingEntity;
        } catch (ParseException e) {
            fail("Failed to create booking DTO");
            return null;
        }
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


            return bookingRequestDTO;
        } catch (ParseException e) {
            fail("Failed to create booking DTO");
            return null;
        }
    }

    @Test
    public void testGetAllBookings_shouldSucceed() {
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

        Response allBookingsResponse = bookingService.getAllBookings();

        assertThat(allBookingsResponse.getStatus()).isEqualTo(200);
        assertThat((List) allBookingsResponse.getEntity()).hasSize(0);
    }

    @Test
    public void testGetBooking_shouldSucceed() {
        Long bookingId = 1L;
        when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(createTestBookingEntity(bookingId, null)));

        Response bookingResponse = bookingService.getBooking(bookingId);
        assertThat(bookingResponse.getStatus()).isEqualTo(200);
    }

    @Test
    public void testGetBooking_shouldThrow500() {
        Long bookingId = 1L;
        when(bookingRepository.findById(1L)).thenThrow(new RuntimeException("Generic error message"));

        Response bookingResponse = bookingService.getBooking(bookingId);
        assertThat(bookingResponse.getStatus()).isEqualTo(500);
        assertThat((String) bookingResponse.getEntity()).isEqualTo("We've run into an issue. Failed to get booking");
    }

    @Test
    public void testGetBooking_shouldReturn404() {
        Long bookingId = 1L;
        when(bookingRepository.findById(1L)).thenThrow(new NotFoundException("Generic error message"));

        Response bookingResponse = bookingService.getBooking(bookingId);
        assertThat(bookingResponse.getStatus()).isEqualTo(404);
        assertThat((String) bookingResponse.getEntity()).isEqualTo("We've run into an issue. Booking not found");
    }

    @Test
    public void testBookingCreation_shouldSucceed() {
        BookingEntity booking = new BookingEntity();
        booking.setId(1L);
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(booking);

        Response createBookingResponse = bookingService.createBooking(Objects.requireNonNull(createSampleBookingDTO()));

        assertThat(createBookingResponse.getStatus()).isEqualTo(200);
        verify(bookingRepository).save(any(BookingEntity.class));
    }

    @Test
    public void testBookingUpdate_shouldSucceed() {
        BookingEntity booking = createTestBookingEntity(1L, null);
        when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(booking));
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(booking);

        UpdateBookingDTO updateBookingDTO = new UpdateBookingDTO();
        updateBookingDTO.setNotes("Booking update notes");
        Response createBookingResponse = bookingService.updateBooking(1L, updateBookingDTO);

        assertThat(createBookingResponse.getStatus()).isEqualTo(200);
        verify(bookingRepository).save(any(BookingEntity.class));
    }

    @Test
    public void testBookingUpdate_shouldReturn400() {
        BookingEntity booking = Objects.requireNonNull(createTestBookingEntity(1L, null));
        booking.setStatus(BookingEntity.BookingStatus.CANCELLED);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        UpdateBookingDTO updateBookingDTO = new UpdateBookingDTO();
        updateBookingDTO.setNotes("Booking update notes");
        try (Response createBookingResponse = bookingService.updateBooking(1L, updateBookingDTO)) {
            assertThat(createBookingResponse.getStatus()).isEqualTo(400);
        }
    }

    @Test
    public void testBookingDelete_shouldReturn200() {
        BookingEntity booking = Objects.requireNonNull(createTestBookingEntity(1L, null));
        booking.setStatus(BookingEntity.BookingStatus.CANCELLED);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepository).delete(any(BookingEntity.class));

        try (Response createBookingResponse = bookingService.deleteBooking(1L)) {
            assertThat(createBookingResponse.getStatus()).isEqualTo(200);
        }
    }
}
