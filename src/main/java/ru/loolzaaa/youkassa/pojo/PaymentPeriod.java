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
public class PaymentPeriod implements Validated {

    private static final int MIN_MONTH_VALUE = 1;
    private static final int MAX_MONTH_VALUE = 12;
    private static final int MIN_YEAR_VALUE = 1920;
    private static final int MAX_YEAR_VALUE = 2050;

    @JsonProperty("month")
    private Integer month;
    @JsonProperty("year")
    private Integer year;

    @Override
    public void validate() {
        if (month == null || year == null) {
            throw new IllegalArgumentException("Month and year must not be null");
        }
        if (month < MIN_MONTH_VALUE || month > MAX_MONTH_VALUE) {
            throw new IllegalArgumentException("Incorrect month. Min value: %d. Max value: %d"
                    .formatted(MIN_MONTH_VALUE, MAX_MONTH_VALUE));
        }
        if (year < MIN_YEAR_VALUE || year > MAX_YEAR_VALUE) {
            throw new IllegalArgumentException("Incorrect year. Min value: %d. Max value: %d"
                    .formatted(MIN_YEAR_VALUE, MAX_YEAR_VALUE));
        }
    }
}
