package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestValidated;
import ru.loolzaaa.youkassa.pojo.Amount;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Deal implements RequestValidated {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("fee_moment")
    private String feeMoment;
    @JsonProperty("description")
    private String description;
    @JsonProperty("balance")
    private Amount balance;
    @JsonProperty("payout_balance")
    private Amount payoutBalance;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("expires_at")
    private String expiresAt;
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    @JsonProperty("test")
    private boolean test;

    @Override
    public void validate() {
        //
    }
}
