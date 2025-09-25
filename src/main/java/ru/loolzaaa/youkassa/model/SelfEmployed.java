package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;
import ru.loolzaaa.youkassa.pojo.Confirmation;

/**
 * This class represents self-employed object.
 * <p>
 * The Self-Employed object contains all the current
 * information about the self-employed individual.
 * It is generated when the self-employed individual
 * is created and is returned in response
 * to any request related to self-employed individuals.
 * <p>
 * The set of returned parameters depends on the object's
 * status (the value of the status parameter)
 * and the parameters you passed in the request
 * to create the self-employed individual.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelfEmployed implements RequestBody {

    private static final int ITN_LENGTH = 12;

    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("itn")
    private String itn;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("confirmation")
    private Confirmation confirmation;
    @JsonProperty("test")
    private Boolean test;

    public static class Status {
        public static final String PENDING = "pending";
        public static final String CONFIRMED = "confirmed ";
        public static final String CANCELED = "canceled ";
        public static final String UNREGISTERED = "unregistered ";
    }

    public static void createValidation(SelfEmployed selfEmployed) {
        if (selfEmployed.getItn() == null && selfEmployed.getPhone() == null) {
            throw new IllegalArgumentException("Itn or phone must be defined");
        }
        if (selfEmployed.getItn() != null && selfEmployed.getItn().length() != ITN_LENGTH) {
            throw new IllegalArgumentException("Incorrect itn. Length must be equal to " + ITN_LENGTH);
        }
        if (selfEmployed.getConfirmation() != null) {
            selfEmployed.getConfirmation().validate();
        }
    }
}
