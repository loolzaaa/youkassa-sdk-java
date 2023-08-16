package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Airline {
    @JsonProperty("ticker_number")
    private String ticketNumber;
    @JsonProperty("booking_reference")
    private String bookingReference;
    @JsonProperty("passengers")
    private List<Passenger> passengers;
    @JsonProperty("legs")
    private List<Leg> legs;
}
