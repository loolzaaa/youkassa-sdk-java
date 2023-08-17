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
public class Source implements Validated {
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("platform_fee_amount")
    private Amount platformFeeAmount;

    @Override
    public void validate() {
        if (accountId == null) {
            throw new IllegalArgumentException("Account id must not be null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        amount.validate();
        if (platformFeeAmount != null) {
            platformFeeAmount.validate();
        }
    }
}
