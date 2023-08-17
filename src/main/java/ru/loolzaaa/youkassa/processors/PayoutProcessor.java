package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Payout;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class PayoutProcessor {

    private static final String BASE_PATH = "/payouts";

    private final ApiClient client;

    public Payout findById(String payoutId) {
        if (payoutId == null || payoutId.isEmpty()) {
            throw new IllegalArgumentException("payoutId must not be null or empty");
        }
        String path = BASE_PATH + "/" + payoutId;
        return client.sendRequest("GET", path, null, null, Payout.class);
    }

    public Payout create(Payout payoutParams, String idempotencyKey) {
        if (payoutParams == null) {
            throw new IllegalArgumentException("payoutParams must not be null");
        }
        Payout.createValidation(payoutParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), payoutParams, Payout.class);
    }
}
