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

/**
 * This class represents the store or gateway
 * settings object (Me).
 * <p>
 * Contains up-to-date information about
 * the settings of the requested store
 * or gateway.
 * <p>
 * Returned in response to a request
 * for configuration information.
 * <p>
 * The set of returned parameters depends on
 * whose settings are requested - a merchant
 * for accepting payments or a gateway for payments.
 *
 * @apiNote Only for those who use Split Payments,
 * Partner Program or Payouts.
 */

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
