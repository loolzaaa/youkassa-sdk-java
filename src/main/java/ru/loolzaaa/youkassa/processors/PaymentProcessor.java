package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Payment;
import ru.loolzaaa.youkassa.pojo.list.PaymentList;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link Payment} entity.
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class PaymentProcessor {

    private static final String BASE_PATH = "/payments";

    private final ApiClient client;

    /**
     * Receive information about some {@link Payment}
     * by its identifier.
     *
     * @param paymentId payment identifier
     * @return payment entity with actual status
     * @throws IllegalArgumentException if payment id is null
     *                                  or empty
     */

    public Payment findById(String paymentId) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("paymentId must not be null or empty");
        }
        String path = BASE_PATH + "/" + paymentId;
        return client.sendRequest("GET", path, null, null, Payment.class);
    }

    /**
     * Receive information about all payments
     * with some filter conditions.
     *
     * @param paymentList collection with filter conditions
     * @return listed payment entities
     * @see PaginatedResponse
     */

    public PaginatedResponse<Payment> findAll(PaymentList paymentList) {
        String path = BASE_PATH + paymentList.toQueryString();
        return client.sendRequest("GET", path, null, null, new TypeReference<>() {
        });
    }

    /**
     * Creates new {@link Payment} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param paymentParams  parameters for new payment
     * @param idempotencyKey idempotency key
     * @return new payment entity with actual status
     * @throws IllegalArgumentException if payment parameters is null
     */

    public Payment create(Payment paymentParams, String idempotencyKey) {
        if (paymentParams == null) {
            throw new IllegalArgumentException("paymentParams must not be null");
        }
        Payment.createValidation(paymentParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), paymentParams, Payment.class);
    }

    /**
     * Capture {@link Payment} that holds in waiting_for_capture status.
     * <p>
     * Needs when payment was creates
     * with capture = false (default) parameter.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param paymentId      payment identifier to be capture
     * @param paymentParams  capture parameters
     * @param idempotencyKey idempotency key
     * @return payment entity with actual status
     * @throws IllegalArgumentException if payment identifier is null or empty,
     *                                  and if capture parameters is null
     */

    public Payment capture(String paymentId, Payment paymentParams, String idempotencyKey) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("paymentId must not be null or empty");
        }
        if (paymentParams == null) {
            throw new IllegalArgumentException("paymentParams must not be null");
        }
        Payment.captureValidation(paymentParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        String path = BASE_PATH + "/" + paymentId + "/capture";
        return client.sendRequest("POST", path, Map.of("Idempotence-Key", idempotencyKey), paymentParams, Payment.class);
    }

    /**
     * Cancel already created {@link Payment} by its identifier.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param paymentId      payment identifier to be canceled
     * @param idempotencyKey idempotency key
     * @return payment entity with actual status
     * @throws IllegalArgumentException if payment identifier is null
     *                                  or empty
     */

    public Payment cancel(String paymentId, String idempotencyKey) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("paymentId must not be null or empty");
        }
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        String path = BASE_PATH + "/" + paymentId + "/cancel";
        return client.sendRequest("POST", path, Map.of("Idempotence-Key", idempotencyKey), null, Payment.class);
    }
}
