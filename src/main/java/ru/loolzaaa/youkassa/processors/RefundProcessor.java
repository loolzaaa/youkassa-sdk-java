package ru.loolzaaa.youkassa.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.PaginatedResponse;
import ru.loolzaaa.youkassa.model.Refund;
import ru.loolzaaa.youkassa.pojo.list.RefundList;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link Refund} entity.
 * <p>
 * Using the API, you can return payments - in full
 * or in part. The refund procedure depends on
 * the payment method (payment_method) of the original payment.
 * When paying with a bank card, the money is returned
 * to the card that was used to make the payment.
 * <p>
 * Some payment methods (for example, cash) do not support returns.
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class RefundProcessor {

    private static final String BASE_PATH = "/refunds";

    private final ApiClient client;

    /**
     * Receive information about some {@link Refund}
     * by its identifier.
     *
     * @param refundId refund identifier
     * @return refund entity with actual status
     * @throws IllegalArgumentException if refund id is null
     *                                  or empty
     */

    public Refund findById(String refundId) {
        if (refundId == null || refundId.isEmpty()) {
            throw new IllegalArgumentException("refundId must not be null or empty");
        }
        String path = BASE_PATH + "/" + refundId;
        return client.sendRequest("GET", path, null, null, Refund.class);
    }

    /**
     * Receive information about all refunds
     * with some filter conditions.
     *
     * @param refundList collection with filter conditions
     * @return listed refund entities
     * @see PaginatedResponse
     */

    public PaginatedResponse<Refund> findAll(RefundList refundList) {
        String path = BASE_PATH + refundList.toQueryString();
        return client.sendRequest("GET", path, null, null, new TypeReference<>() {
        });
    }

    /**
     * Creates new {@link Refund} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param refundParams   parameters for new refund
     * @param idempotencyKey idempotency key
     * @return new refund entity with actual status
     * @throws IllegalArgumentException if refund parameters is null
     */

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
