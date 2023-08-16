package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Webhook;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class WebhookProcessor {

    private static final String BASE_PATH = "/webhooks";

    private final ApiClient client;

    public PaginatedResponse<Webhook> findAll() {
        return client.sendRequest("GET", BASE_PATH, null, null, new TypeReference<>() {});
    }

    public Webhook create(Webhook webhookParams, String idempotencyKey) {
        if (webhookParams == null) {
            throw new IllegalArgumentException("webhookParams must not be null");
        }
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), webhookParams, Webhook.class);
    }

    public Webhook removeById(String webhookId, String idempotencyKey) {
        if (webhookId == null || webhookId.isEmpty()) {
            throw new IllegalArgumentException("webhookId must not be null or empty");
        }
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        String path = BASE_PATH + "/" + webhookId;
        return client.sendRequest("DELETE", path, Map.of("Idempotence-Key", idempotencyKey), null, Webhook.class);
    }
}
