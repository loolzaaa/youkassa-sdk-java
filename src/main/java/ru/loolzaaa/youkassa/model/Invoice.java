package ru.loolzaaa.youkassa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestBody;
import ru.loolzaaa.youkassa.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * This class represents Invoice object.
 * <p>
 * The Invoice object contains all the current
 * invoice information. It is generated when an invoice
 * is created and is returned in response
 * to any invoice-related request.
 * <p>
 * The set of returned parameters depends on
 * the object's status (the value of the status parameter)
 * and the parameters you passed
 * in the invoice creation request.
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Invoice implements RequestBody {

    private static final int MAX_DESCRIPTION_LENGTH = 128;
    private static final int MAX_METADATA_SIZE = 16;
    private static final int MAX_METADATA_KEY_LENGTH = 32;
    private static final int MAX_METADATA_VALUE_LENGTH = 512;

    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("payment_data")
    private PaymentData paymentData;
    @JsonProperty("cart")
    private List<Cart> cart;
    @JsonProperty("delivery_method")
    private DeliveryMethod deliveryMethod;
    @JsonProperty("delivery_method_data")
    private DeliveryMethod deliveryMethodData;
    @JsonProperty("payment_details")
    private PaymentDetails paymentDetails;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("expires_at")
    private String expiresAt;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("description")
    private String description;
    @JsonProperty("cancellation_details")
    private CancellationDetails cancellationDetails;
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public static void createValidation(Invoice invoice) {
        if (invoice.getPaymentData() == null) {
            throw new IllegalArgumentException("Payment data must not be null");
        }
        invoice.getPaymentData().validate();
        if (invoice.getCart() == null || invoice.getCart().isEmpty()) {
            throw new IllegalArgumentException("Cart must not be null or empty");
        }
        for (Cart cart : invoice.getCart()) {
            cart.validate();
        }
        if (invoice.getDeliveryMethodData() != null) {
            invoice.getDeliveryMethodData().validate();
        }
        if (invoice.getDescription() != null && invoice.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Too long description. Max length: " + MAX_DESCRIPTION_LENGTH);
        }
        if (invoice.getMetadata() != null) {
            if (invoice.getMetadata().size() > MAX_METADATA_SIZE) {
                throw new IllegalArgumentException("Incorrect metadata size. Max size: " + MAX_METADATA_SIZE);
            }
            for (Map.Entry<String, String> pair : invoice.getMetadata().entrySet()) {
                if (pair.getKey().length() > MAX_METADATA_KEY_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata key. Max Length: " + MAX_METADATA_KEY_LENGTH);
                }
                if (pair.getValue().length() > MAX_METADATA_VALUE_LENGTH) {
                    throw new IllegalArgumentException("Too long metadata value. Max Length: " + MAX_METADATA_VALUE_LENGTH);
                }
            }
        }
    }

    public static class Status {
        public static final String PENDING = "pending";
        public static final String SUCCEEDED = "succeeded ";
        public static final String CANCELED = "canceled ";
    }
}
