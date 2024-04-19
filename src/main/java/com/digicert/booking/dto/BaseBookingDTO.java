package com.digicert.booking.dto;

import com.digicert.booking.entity.BookingEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BaseBookingDTO {
    @JsonProperty("id")
    @NonNull
    private Long id;

    @JsonProperty("status")
    private BookingEntity.BookingStatus status;

    @JsonProperty("customer_email")
    private String customerEmail;

    @JsonProperty("num_adults")
    private int numAdults;
    @JsonProperty("num_children")
    private int numChildren;
    @JsonProperty("num_beds")
    private int numBeds;

    @JsonProperty(value = "check_in_date", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @JsonProperty(value = "check_out_date", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkOutDate;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createDate;

    @JsonProperty("modified_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date modifiedDate;
}
