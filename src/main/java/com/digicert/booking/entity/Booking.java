package com.digicert.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Booking {
    /*
     * Booking:
     * id: UUID
     * numAdults: int
     * numChildren: int
     * startDate: Date
     * endDate: Date
     * numBeds: int
     * notes: String
     *
     * status: BookingStatus
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BookingStatus status;

    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;

    private int numAdults;
    private int numChildren;

    private Date startDate;
    private Date endDate;

    private int numBeds;

    private String notes;

    enum BookingStatus {
        PENDING("pending"),
        ACCEPTED("accepted"),
        CANCELLED("cancelled");

        private final String bookingStatus;

        BookingStatus(String bookingStatusString) {
            bookingStatus = bookingStatusString;
        }
        public String toString(){
            return bookingStatus;
        }

        public static String getEnumByString(String code){
            for(BookingStatus e : BookingStatus.values()){
                if(e.bookingStatus.equals(code)) return e.name();
            }
            return null;
        }
    }
}
