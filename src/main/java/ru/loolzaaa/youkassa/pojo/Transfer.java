package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("platform_fee_amount")
    private Amount platformFeeAmount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("metadata")
    private Map<String, String> metadata;
}
