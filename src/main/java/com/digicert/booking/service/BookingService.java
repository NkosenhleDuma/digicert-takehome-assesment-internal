package com.digicert.booking.service;

import com.digicert.booking.entity.Booking;
import com.digicert.booking.repository.BookingRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Path("/bookings")
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    BookingRepository bookingRepository;

    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello() {
        return "Hello from Spring";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(Booking booking) {
        log.info("Creating booking");
        Booking savedBooking = bookingRepository.save(booking);
        return Response.ok(savedBooking).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
        log.info("getAllBookings");
         try {
             return Response.ok(bookingRepository.findAll()).build();
         } catch (Exception e) {
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
         }
    }
}