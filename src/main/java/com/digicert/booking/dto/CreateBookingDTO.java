package com.digicert.booking.dto;


import com.digicert.booking.entity.BookingEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class CreateBookingDTO {
    @JsonProperty(value = "customer_email", required = true)
    private String customerEmail;

    @JsonProperty(value = "num_adults", required = true)
    @Min(value = 1, message = "There must be at least one adult.")
    private int numAdults;
    @JsonProperty("num_children")
    private int numChildren;
    @JsonProperty("num_beds")
    @Min(value = 1, message = "At least one bed expected.")
    private int numBeds;

    @JsonProperty(value = "check_in_date", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @JsonProperty(value = "check_out_date", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkOutDate;

    @JsonProperty("notes")
    private String notes;
}
