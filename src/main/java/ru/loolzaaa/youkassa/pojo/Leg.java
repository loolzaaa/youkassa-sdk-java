package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Leg implements Validated {

    private static final String AIRPORT_PATTERN = "[A-Z]{3}";
    private static final String DEPARTURE_DATE_PATTERN = "^\\d{4}-(0\\d|1[0-2])-([0-2]\\d|3[01])$";
    private static final String CARRIER_CODE_PATTERN = "[A-Z]{2,3}";

    @JsonProperty("departure_airport")
    private String departureAirport;
    @JsonProperty("destination_airport")
    private String destinationAirport;
    @JsonProperty("departure_date")
    private String departureDate;
    @JsonProperty("carrier_code")
    private String carrierCode;

    @Override
    public void validate() {
        if (departureAirport == null) {
            throw new IllegalArgumentException("Departure airport must not be null");
        }
        if (!departureAirport.matches(AIRPORT_PATTERN)) {
            throw new IllegalArgumentException("Incorrect departure airport. Correct pattern: " + AIRPORT_PATTERN);
        }
        if (destinationAirport == null) {
            throw new IllegalArgumentException("Destination airport must not be null");
        }
        if (!destinationAirport.matches(AIRPORT_PATTERN)) {
            throw new IllegalArgumentException("Incorrect destination airport. Correct pattern: " + AIRPORT_PATTERN);
        }
        if (departureDate == null) {
            throw new IllegalArgumentException("Departure date must not be null");
        }
        if (!departureDate.matches(DEPARTURE_DATE_PATTERN)) {
            throw new IllegalArgumentException("Incorrect departure date. Correct pattern: " + DEPARTURE_DATE_PATTERN);
        }
        if (carrierCode != null && !carrierCode.matches(CARRIER_CODE_PATTERN)) {
            throw new IllegalArgumentException("Incorrect carrier code. Correct pattern: " + CARRIER_CODE_PATTERN);
        }
    }
}
