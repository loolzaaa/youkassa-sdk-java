package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.SelfEmployed;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link SelfEmployed} entity.
 * <p>
 * The API allows you to create self-employed,
 * as well as receive information about them.
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class SelfEmployedProcessor {

    private static final String BASE_PATH = "/self-employed";

    private final ApiClient client;

    /**
     * Receive information about some {@link SelfEmployed}
     * by its identifier.
     *
     * @param selfEmployedId self-employed identifier
     * @return self-employed entity with actual status
     * @throws IllegalArgumentException if self-employed id is null
     *                                  or empty
     */

    public SelfEmployed findById(String selfEmployedId) {
        if (selfEmployedId == null || selfEmployedId.isEmpty()) {
            throw new IllegalArgumentException("selfEmployedId must not be null or empty");
        }
        String path = BASE_PATH + "/" + selfEmployedId;
        return client.sendRequest("GET", path, null, null, SelfEmployed.class);
    }

    /**
     * Creates new {@link SelfEmployed} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param selfEmployedParams parameters for new self-employed
     * @param idempotencyKey     idempotency key
     * @return new self-employed entity with actual status
     * @throws IllegalArgumentException if self-employed parameters is null
     */

    public SelfEmployed create(SelfEmployed selfEmployedParams, String idempotencyKey) {
        if (selfEmployedParams == null) {
            throw new IllegalArgumentException("selfEmployedParams must not be null");
        }
        SelfEmployed.createValidation(selfEmployedParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), selfEmployedParams, SelfEmployed.class);
    }
}
