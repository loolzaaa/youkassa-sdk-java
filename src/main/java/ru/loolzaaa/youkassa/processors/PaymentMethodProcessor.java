package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.PaymentMethod;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link PaymentMethod} entity.
 * <p>
 * The API allows you to create and save
 * payment methods, as well as receive
 * information about them.
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class PaymentMethodProcessor {

    private static final String BASE_PATH = "/payment_methods";

    private final ApiClient client;

    /**
     * Receive information about some {@link PaymentMethod}
     * by its identifier.
     *
     * @param paymentMethodId payment method identifier
     * @return payment entity with actual status
     * @throws IllegalArgumentException if payment method id is null
     *                                  or empty
     */

    public PaymentMethod findById(String paymentMethodId) {
        if (paymentMethodId == null || paymentMethodId.isEmpty()) {
            throw new IllegalArgumentException("paymentMethodId must not be null or empty");
        }
        String path = BASE_PATH + "/" + paymentMethodId;
        return client.sendRequest("GET", path, null, null, PaymentMethod.class);
    }

    /**
     * Creates new {@link PaymentMethod} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param paymentMethodParams parameters for new payment
     * @param idempotencyKey      idempotency key
     * @return new payment method entity with actual status
     * @throws IllegalArgumentException if payment parameters is null
     */

    public PaymentMethod create(PaymentMethod paymentMethodParams, String idempotencyKey) {
        if (paymentMethodParams == null) {
            throw new IllegalArgumentException("paymentMethodParams must not be null");
        }
        PaymentMethod.createValidation(paymentMethodParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), paymentMethodParams, PaymentMethod.class);
    }
}
