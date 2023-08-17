package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.pojo.Amount;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Me {
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("test")
    private Boolean test;
    @JsonProperty("fiscalization_enabled")
    private Boolean fiscalizationEnabled;
    @JsonProperty("payment_methods")
    private List<String> paymentMethods;
    @JsonProperty("itn")
    private String itn;
    @JsonProperty("payout_methods")
    private List<String> payoutMethods;
    @JsonProperty("name")
    private String name;
    @JsonProperty("payout_balance")
    private Amount payoutBalance;
}
