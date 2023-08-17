package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Refund;
import ru.loolzaaa.youkassa.pojo.list.RefundList;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class RefundProcessor {

    private static final String BASE_PATH = "/refunds";

    private final ApiClient client;

    public Refund findById(String refundId) {
        if (refundId == null || refundId.isEmpty()) {
            throw new IllegalArgumentException("refundId must not be null or empty");
        }
        String path = BASE_PATH + "/" + refundId;
        return client.sendRequest("GET", path, null, null, Refund.class);
    }

    public PaginatedResponse<Refund> findAll(RefundList refundList) {
        String path = BASE_PATH + refundList.toQueryString();
        return client.sendRequest("GET", path, null, null, new TypeReference<>() {});
    }

    public Refund create(Refund refundParams, String idempotencyKey) {
        if (refundParams == null) {
            throw new IllegalArgumentException("refundParams must not be null");
        }
        Refund.createValidation(refundParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), refundParams, Refund.class);
    }
}
