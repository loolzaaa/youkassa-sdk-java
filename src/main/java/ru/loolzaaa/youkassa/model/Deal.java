package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;
import ru.loolzaaa.youkassa.pojo.Amount;

import java.util.Map;

/**
 * This class represents Deal object.
 * <p>
 * Contains all information about the deal
 * that is relevant at the current time.
 * <p>
 * It is generated when a deal is created
 * and comes in response to any request
 * related to deals.
 *
 * @apiNote Only for those using Safe Deal.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Deal implements RequestBody {

    private static final int MAX_DESCRIPTION_LENGTH = 128;
    private static final int MAX_METADATA_SIZE = 16;
    private static final int MAX_METADATA_KEY_LENGTH = 32;
    private static final int MAX_METADATA_VALUE_LENGTH = 512;

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
    private Boolean test;

    public static void createValidation(Deal deal) {
        if (deal.getType() == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        if (deal.getFeeMoment() == null) {
            throw new IllegalArgumentException("fee moment must not be null");
        }
        if (deal.getMetadata() != null) {
            if (deal.getMetadata().size() > MAX_METADATA_SIZE) {
                throw new IllegalArgumentException("Incorrect metadata size. Max size: " + MAX_METADATA_SIZE);
            }
            for (Map.Entry<String, String> pair : deal.getMetadata().entrySet()) {
                if (pair.getKey().length() > MAX_METADATA_KEY_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata key. Max Length: " + MAX_METADATA_KEY_LENGTH);
                }
                if (pair.getValue().length() > MAX_METADATA_VALUE_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata value. Max Length: " + MAX_METADATA_VALUE_LENGTH);
                }
            }
        }
        if (deal.getDescription() != null && deal.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Too long description. Max length: " + MAX_DESCRIPTION_LENGTH);
        }
    }
}
