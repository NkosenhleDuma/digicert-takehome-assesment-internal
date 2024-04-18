package com.digicert.booking.service;

import com.digicert.booking.dto.BaseBookingDTO;
import com.digicert.booking.dto.CreateBookingDTO;
import com.digicert.booking.dto.UpdateBookingDTO;
import com.digicert.booking.entity.BookingEntity;
import com.digicert.booking.repository.BookingRepository;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Path("/bookings")
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    BookingRepository bookingRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
        log.info("Getting all bookings");
         try {
             List<BookingEntity> bookingEntities = bookingRepository.findAll();
             List<BaseBookingDTO> bookingDTOs = bookingEntities
                     .stream().map(this::entityToDTO).toList();
             return Response.ok(bookingDTOs).build();
         } catch (Exception e) {
             log.error(e.getMessage());
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("We've run into an issue. Failed to get bookings").build();
         }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooking(@PathParam("id") Long id) {
        log.info("Getting booking");
        try {
            BookingEntity bookingEntity = bookingRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Booking not found"));
            BaseBookingDTO bookingDTO = entityToDTO(bookingEntity);
            return Response.ok(bookingDTO).build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("We've run into an issue. Booking not found")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("We've run into an issue. Failed to get booking").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") Long id) {
        log.info("Deleting booking");
        try {
            BookingEntity bookingEntity = bookingRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Booking not found"));

            bookingRepository.delete(bookingEntity);
            return Response.ok().build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("We've run into an issue. Booking not found")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("We've run into an issue. Failed to get bookings").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(@Valid CreateBookingDTO createBookingDTO) {
        log.info("Creating booking, {}", createBookingDTO.toString());
        try {
            BookingEntity bookingEntity = new BookingEntity();

            bookingEntity.setId(createBookingDTO.getId());
            bookingEntity.setStatus(BookingEntity.BookingStatus.PENDING); // Created as PENDING
            bookingEntity.setCustomerEmail(createBookingDTO.getCustomerEmail());

            bookingEntity.setNumAdults(createBookingDTO.getNumAdults());
            bookingEntity.setNumChildren(createBookingDTO.getNumChildren());
            bookingEntity.setNumBeds(createBookingDTO.getNumBeds());

            bookingEntity.setStartDate(createBookingDTO.getStartDate());
            bookingEntity.setEndDate(createBookingDTO.getEndDate());

            bookingEntity.setNotes(createBookingDTO.getNotes());

            BookingEntity savedBookingEntity = bookingRepository.save(bookingEntity);

            return Response.ok(entityToDTO(savedBookingEntity)).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("We've run into an issue. Failed to create booking")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatedBooking(@PathParam("id") Long id, @Valid UpdateBookingDTO updateBookingDTO) {
        log.info("Updating booking, {}", updateBookingDTO.toString());
        try {
            BookingEntity bookingEntity = bookingRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("Booking not found")
                    );

            bookingEntity.setNumAdults(updateBookingDTO.getNumAdults());
            bookingEntity.setNumChildren(updateBookingDTO.getNumChildren());
            bookingEntity.setNumBeds(updateBookingDTO.getNumBeds());
            bookingEntity.setStartDate(updateBookingDTO.getStartDate());
            bookingEntity.setEndDate(updateBookingDTO.getEndDate());
            bookingEntity.setNotes(updateBookingDTO.getNotes());

            BookingEntity savedBookingEntity = bookingRepository.save(bookingEntity);
            return Response.ok(entityToDTO(savedBookingEntity)).build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("We've run into an issue. Failed update the booking")
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("We've run into an issue. Failed update the booking")
                    .build();
        }
    }

    // DTO Mappings... FIXME
    private BaseBookingDTO entityToDTO(BookingEntity bookingEntity) {
        BaseBookingDTO bookingDTO = new BaseBookingDTO();

        bookingDTO.setId(bookingEntity.getId());
        bookingDTO.setStatus(bookingEntity.getStatus());
        bookingDTO.setCustomerEmail(bookingEntity.getCustomerEmail());

        bookingDTO.setNumAdults(bookingEntity.getNumAdults());
        bookingDTO.setNumChildren(bookingEntity.getNumChildren());
        bookingDTO.setNumBeds(bookingEntity.getNumBeds());

        bookingDTO.setStartDate(bookingEntity.getStartDate());
        bookingDTO.setEndDate(bookingEntity.getEndDate());
        bookingDTO.setNotes(bookingEntity.getNotes());

        bookingDTO.setCreateDate(bookingEntity.getCreateDate());
        bookingDTO.setModifiedDate(bookingEntity.getModifiedDate());

        return bookingDTO;
    }
}