package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Payment;
import ru.loolzaaa.youkassa.pojo.CapturePayment;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class PaymentProcessor {

    private static final String BASE_PATH = "/payments";

    private final ApiClient client;

    public Payment findById(String paymentId) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("paymentId must not be null or empty");
        }
        String path = BASE_PATH + "/" + paymentId;
        return client.sendRequest("GET", path, null, null, Payment.class);
    }

    public PaginatedResponse<Payment> findAll() {
        return client.sendRequest("GET", BASE_PATH, null, null, new TypeReference<>() {});
    }

    public Payment create(Payment paymentParams, String idempotencyKey) {
        if (paymentParams == null) {
            throw new IllegalArgumentException("paymentParams must not be null");
        }
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), paymentParams, Payment.class);
    }

    public Payment capture(String paymentId, CapturePayment capturePayment, String idempotencyKey) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("paymentId must not be null or empty");
        }
        if (capturePayment == null) {
            throw new IllegalArgumentException("paymentParams must not be null");
        }
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        String path = BASE_PATH + "/" + paymentId + "/capture";
        return client.sendRequest("POST", path, Map.of("Idempotence-Key", idempotencyKey), capturePayment, Payment.class);
    }

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
