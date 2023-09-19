package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Deal;
import ru.loolzaaa.youkassa.pojo.list.DealList;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link Deal} entity.
 * <p>
 * A Safe Deal is a YooKassa solution that allows
 * you to accept payment from one participant
 * in a transaction and transfer it to another
 * participant at the time the terms
 * of the transaction are fulfilled.
 * <p>
 * Using the API, you can create a deal
 * and get up-to-date information about it.
 * <p>
 * Use {@link ApiClient} for API server communication.
 *
 * @apiNote Only for those using Safe Deal.
 */

@RequiredArgsConstructor
public class DealProcessor {

    private static final String BASE_PATH = "/deals";

    private final ApiClient client;

    /**
     * Receive information about some {@link Deal}
     * by its identifier.
     *
     * @param dealId deal identifier
     * @return deal entity with actual status
     * @throws IllegalArgumentException if deal id is null
     *                                  or empty
     */

    public Deal findById(String dealId) {
        if (dealId == null || dealId.isEmpty()) {
            throw new IllegalArgumentException("dealId must not be null or empty");
        }
        String path = BASE_PATH + "/" + dealId;
        return client.sendRequest("GET", path, null, null, Deal.class);
    }

    /**
     * Receive information about all deals
     * with some filter conditions.
     *
     * @param dealList collection with filter conditions
     * @return listed deal entities
     * @see PaginatedResponse
     */

    public PaginatedResponse<Deal> findAll(DealList dealList) {
        String path = BASE_PATH + dealList.toQueryString();
        return client.sendRequest("GET", path, null, null, new TypeReference<>() {
        });
    }

    /**
     * Creates new {@link Deal} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param dealParams     parameters for new deal
     * @param idempotencyKey idempotency key
     * @return new deal entity with actual status
     * @throws IllegalArgumentException if deal parameters is null
     */

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
