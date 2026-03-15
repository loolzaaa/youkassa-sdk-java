package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.Validated;
import ru.loolzaaa.youkassa.helper.ApiHelper;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VatData implements Validated {

    private static final List<String> AVAILABLE_RATES = List.of("5", "7", "10", "20", "22");

    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("rate")
    private String rate;

    @Override
    public void validate() {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        ApiHelper.checkObjectType(this, "type", String.class, "Type");
        if (type.equals(Type.CALCULATED)) {
            if (amount == null) {
                throw new IllegalArgumentException("Amount must not be null");
            }
            amount.validate();
            if (rate == null) {
                throw new IllegalArgumentException("Rate must not be null");
            }
            if (!AVAILABLE_RATES.contains(rate)) {
                throw new IllegalArgumentException("Incorrect rate: Available values: " + AVAILABLE_RATES);
            }
        }
        if (type.equals(Type.MIXED)) {
            if (amount == null) {
                throw new IllegalArgumentException("Amount must not be null");
            }
            amount.validate();
        }
    }

    public static class Type {
        public static final String UNTAXED = "untaxed";
        public static final String CALCULATED = "calculated";
        public static final String MIXED = "mixed";
    }
}
