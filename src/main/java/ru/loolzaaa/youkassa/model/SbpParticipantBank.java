package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;

/**
 * This class represents SBP participants object.
 * <p>
 * The SBP participant object (SbpParticipantBank)
 * contains information about the bank or payment service
 * connected to the SBP. It is returned in the list
 * when requesting a list of SBP participants.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SbpParticipantBank implements RequestBody {
    @JsonProperty("bank_id")
    private String bankId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("bic")
    private String bic;
}
