package com.digicert.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.Date;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BookingStatus status;
    private String customerEmail;
    private int numAdults;
    private int numChildren;
    private Date startDate;
    private Date endDate;
    private int numBeds;
    private String notes;

    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date modifiedDate;

    public enum BookingStatus {
        PENDING("pending"),
        ACCEPTED("accepted"),
        CANCELLED("cancelled");

        private final String bookingStatus;

        BookingStatus(String bookingStatusString) {
            bookingStatus = bookingStatusString;
        }
        public String toString() {
            return bookingStatus;
        }

        public static String getEnumByString(String code) {
            for(BookingStatus e : BookingStatus.values()){
                if(e.bookingStatus.equals(code)) return e.name();
            }
            return null;
        }
    }
}
