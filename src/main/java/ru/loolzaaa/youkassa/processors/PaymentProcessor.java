package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Payment;

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

    public Payment findAll() {
        //TODO: return list
        return client.sendRequest("GET", BASE_PATH, null, null, Payment.class);
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

    public Payment capture(String paymentId, Object paymentCaptureParams, String idempotencyKey) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("paymentId must not be null or empty");
        }
        if (paymentCaptureParams == null) {
            throw new IllegalArgumentException("paymentParams must not be null");
        }
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        String path = BASE_PATH + "/" + paymentId + "/capture";
        return client.sendRequest("POST", path, Map.of("Idempotence-Key", idempotencyKey), paymentCaptureParams, Payment.class);
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
