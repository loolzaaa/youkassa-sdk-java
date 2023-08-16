package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Leg {
    @JsonProperty("departure_airport")
    private String departureAirport;
    @JsonProperty("destination_airport")
    private String destinationAirport;
    @JsonProperty("departure_date")
    private String departureDate;
    @JsonProperty("carrier_code")
    private String carrierCode;
}
