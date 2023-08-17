package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.loolzaaa.youkassa.client.Validated;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Amount implements Validated {
    @JsonProperty("value")
    private String value;
    @JsonProperty("currency")
    private String currency;

    @Override
    public void validate() {
        if (value == null || !value.matches("\\d+\\.\\d{1,2}")) {
            throw new IllegalArgumentException("Incorrect value: " + value);
        }
        if (!Currency.validate(currency)) {
            throw new IllegalArgumentException("Incorrect currency: " + currency);
        }
    }
}
