package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Airline implements Validated {

    private static final String TICKET_NUMBER_PATTERN = "\\d{1,150}";

    @JsonProperty("ticker_number")
    private String ticketNumber;
    @JsonProperty("booking_reference")
    private String bookingReference;
    @JsonProperty("passengers")
    private List<Passenger> passengers;
    @JsonProperty("legs")
    private List<Leg> legs;

    @Override
    public void validate() {
        if (ticketNumber == null && bookingReference == null) {
            throw new IllegalArgumentException("Both ticket number and booking reference are null");
        }
        if (ticketNumber != null && !ticketNumber.matches(TICKET_NUMBER_PATTERN)) {
            throw new IllegalArgumentException("Incorrect ticket number. Correct pattern: " + TICKET_NUMBER_PATTERN);
        }
        if (bookingReference != null && (bookingReference.length() < 1 || bookingReference.length() > 20)) {
            throw new IllegalArgumentException("Incorrect booking reference length. Min: 1. Max: 20");
        }
        for (Passenger passenger : passengers) {
            passenger.validate();
        }
        for (Leg leg : legs) {
            leg.validate();
        }
    }
}
