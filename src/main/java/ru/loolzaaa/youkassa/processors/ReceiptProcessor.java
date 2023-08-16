package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Receipt;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ReceiptProcessor {

    private static final String BASE_PATH = "/receipts";

    private final ApiClient client;

    public Receipt findById(String receiptId) {
        if (receiptId == null || receiptId.isEmpty()) {
            throw new IllegalArgumentException("receiptId must not be null or empty");
        }
        String path = BASE_PATH + "/" + receiptId;
        return client.sendRequest("GET", path, null, null, Receipt.class);
    }

    public PaginatedResponse<Receipt> findAll() {
        return client.sendRequest("GET", BASE_PATH, null, null, new TypeReference<>() {});
    }

    public Receipt create(Receipt receiptParams, String idempotencyKey) {
        if (receiptParams == null) {
            throw new IllegalArgumentException("receiptParams must not be null");
        }
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), receiptParams, Receipt.class);
    }
}
