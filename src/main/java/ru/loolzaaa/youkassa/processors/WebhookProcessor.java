package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Webhook;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link Webhook} entity.
 * <p>
 * Webhook is a mechanism for automatically
 * notifying your system about events
 * that happen to created objects.
 * For example, YooKassa can tell you
 * when a payment object created in your application
 * enters the {@code waiting_for_capture} status.
 * <p>
 * Using the API, you can set up a webhook
 * (create, delete, view the list of created ones)
 * for the passed OAuth token.
 * <p>
 * Use {@link ApiClient} for API server communication.
 *
 * @apiNote Authentication by OAuth token only.
 * Available through the Partner API
 */

@RequiredArgsConstructor
public class WebhookProcessor {

    private static final String BASE_PATH = "/webhooks";

    private final ApiClient client;

    /**
     * Receive information about all existing webhooks.
     * <p>
     * Can be used only if OAuth authentication
     * method configured.
     *
     * @return listed webhook entities
     * @see PaginatedResponse
     */

    public PaginatedResponse<Webhook> findAll() {
        return client.sendRequest("GET", BASE_PATH, null, null, new TypeReference<>() {
        });
    }

    /**
     * Creates new {@link Webhook} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     * <p>
     * Can be used only if OAuth authentication
     * method configured.
     *
     * @param webhookParams  parameters for new webhook
     * @param idempotencyKey idempotency key
     * @return new webhook entity
     * @throws IllegalArgumentException if webhook parameters is null
     */

    public Webhook create(Webhook webhookParams, String idempotencyKey) {
        if (webhookParams == null) {
            throw new IllegalArgumentException("webhookParams must not be null");
        }
        Webhook.createValidation(webhookParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), webhookParams, Webhook.class);
    }

    /**
     * Remove already existing {@link Webhook} by its identifier.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param webhookId      webhook identifier to be removed
     * @param idempotencyKey idempotency key
     * @return webhook entity
     * @throws IllegalArgumentException if webhook identifier is null
     *                                  or empty
     */

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
