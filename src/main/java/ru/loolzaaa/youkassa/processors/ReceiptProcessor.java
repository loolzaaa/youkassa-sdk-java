package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Receipt;
import ru.loolzaaa.youkassa.pojo.list.ReceiptList;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link Receipt} entity.
 * <p>
 * Using the API, you can receive information
 * about receipts for which you sent data
 * through YooKassa.
 * <p>
 * Use {@link ApiClient} for API server communication.
 *
 * @apiNote For those who use the YooKassa solution for 54-FZ
 */

@RequiredArgsConstructor
public class ReceiptProcessor {

    private static final String BASE_PATH = "/receipts";

    private final ApiClient client;

    /**
     * Receive information about some {@link Receipt}
     * by its identifier.
     *
     * @param receiptId receipt identifier
     * @return receipt entity with actual status
     * @throws IllegalArgumentException if receipt id is null
     *                                  or empty
     */

    public Receipt findById(String receiptId) {
        if (receiptId == null || receiptId.isEmpty()) {
            throw new IllegalArgumentException("receiptId must not be null or empty");
        }
        String path = BASE_PATH + "/" + receiptId;
        return client.sendRequest("GET", path, null, null, Receipt.class);
    }

    /**
     * Receive information about all receipts
     * with some filter conditions.
     *
     * @param receiptList collection with filter conditions
     * @return listed payout entities
     * @see PaginatedResponse
     */

    public PaginatedResponse<Receipt> findAll(ReceiptList receiptList) {
        String path = BASE_PATH + receiptList.toQueryString();
        return client.sendRequest("GET", path, null, null, new TypeReference<>() {
        });
    }

    /**
     * Creates new {@link Receipt} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param receiptParams  parameters for new receipt
     * @param idempotencyKey idempotency key
     * @return new receipt entity with actual status
     * @throws IllegalArgumentException if receipt parameters is null
     */

    public Receipt create(Receipt receiptParams, String idempotencyKey) {
        if (receiptParams == null) {
            throw new IllegalArgumentException("receiptParams must not be null");
        }
        Receipt.createValidation(receiptParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), receiptParams, Receipt.class);
    }
}
