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
public class Settlement implements Validated {
    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private Amount amount;

    @Override
    public void validate() {
        //TODO: check type values by available in subclass
        if (type == null || amount == null) {
            throw new IllegalArgumentException("Type and amount must not be null");
        }
        amount.validate();
    }

    public static class Type {
        public static final String PAYOUT = "payout";

        public static final String CASHLESS = "cashless";
        public static final String PREPAYMENT = "prepayment";
        public static final String POSTPAYMENT = "postpayment";
        public static final String CONSIDERATION = "consideration";
    }
}
