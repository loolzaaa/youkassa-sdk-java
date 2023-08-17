package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Deal;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class DealProcessor {

    private static final String BASE_PATH = "/deals";

    private final ApiClient client;

    public Deal findById(String dealId) {
        if (dealId == null || dealId.isEmpty()) {
            throw new IllegalArgumentException("dealId must not be null or empty");
        }
        String path = BASE_PATH + "/" + dealId;
        return client.sendRequest("GET", path, null, null, Deal.class);
    }

    public PaginatedResponse<Deal> findAll() {
        return client.sendRequest("GET", BASE_PATH, null, null, new TypeReference<>() {});
    }

    public Deal create(Deal dealParams, String idempotencyKey) {
        if (dealParams == null) {
            throw new IllegalArgumentException("dealParams must not be null");
        }
        Deal.createValidation(dealParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), dealParams, Deal.class);
    }
}
