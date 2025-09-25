package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Invoice;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link Invoice} entity.
 * <p>
 * The API allows you to create invoices,
 * as well as receive information about them.
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class InvoiceProcessor {

    private static final String BASE_PATH = "/invoices";

    private final ApiClient client;

    /**
     * Receive information about some {@link Invoice}
     * by its identifier.
     *
     * @param invoiceId invoice identifier
     * @return invoice entity with actual status
     * @throws IllegalArgumentException if invoice id is null
     *                                  or empty
     */

    public Invoice findById(String invoiceId) {
        if (invoiceId == null || invoiceId.isEmpty()) {
            throw new IllegalArgumentException("invoiceId must not be null or empty");
        }
        String path = BASE_PATH + "/" + invoiceId;
        return client.sendRequest("GET", path, null, null, Invoice.class);
    }

    /**
     * Creates new {@link Invoice} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param invoiceParams  parameters for new payment
     * @param idempotencyKey idempotency key
     * @return new invoice entity with actual status
     * @throws IllegalArgumentException if invoice parameters is null
     */

    public Invoice create(Invoice invoiceParams, String idempotencyKey) {
        if (invoiceParams == null) {
            throw new IllegalArgumentException("invoiceParams must not be null");
        }
        Invoice.createValidation(invoiceParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), invoiceParams, Invoice.class);
    }
}
