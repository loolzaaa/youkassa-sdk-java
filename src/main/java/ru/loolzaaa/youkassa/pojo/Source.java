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
public class Source {
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("platform_fee_amount")
    private Amount platformFeeAmount;
}
