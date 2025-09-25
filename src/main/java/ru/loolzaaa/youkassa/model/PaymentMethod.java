package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;
import ru.loolzaaa.youkassa.pojo.Card;
import ru.loolzaaa.youkassa.pojo.Confirmation;
import ru.loolzaaa.youkassa.pojo.Recipient;

/**
 * This class represents payment method object.
 * <p>
 * The PaymentMethod object contains all the current
 * information about the user's payment method.
 * It is generated when the payment method is created
 * and is returned in response to any request related
 * to payment methods.
 * <p>
 * The set of returned parameters depends on the object's
 * status (the value of the status parameter)
 * and the parameters you passed in the request
 * to create the payment method.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethod implements RequestBody {
    @JsonProperty("type")
    private String type;
    @JsonProperty("card")
    private Card card;
    @JsonProperty("id")
    private String id;
    @JsonProperty("saved")
    private Boolean saved;
    @JsonProperty("status")
    private String status;
    @JsonProperty("holder")
    private Recipient holder;
    @JsonProperty("type")
    private String title;
    @JsonProperty("type")
    private Confirmation confirmation;

    public static void createValidation(PaymentMethod paymentMethod) {
        if (paymentMethod.getType() == null) {
            throw new IllegalArgumentException("Type must not be null");
        }
        if (paymentMethod.getCard() != null) {
            paymentMethod.getCard().validate();
        }
        if (paymentMethod.getHolder() != null) {
            paymentMethod.getHolder().validate();
        }
        if (paymentMethod.getConfirmation() != null) {
            paymentMethod.getConfirmation().validate();
        }
    }
}
