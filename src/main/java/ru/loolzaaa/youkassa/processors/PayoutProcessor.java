package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Payout;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link Payout} entity.
 * <p>
 * A payout is the amount of money you transfer
 * to an individual or self-employed person.
 * Using the API, you can create a payout
 * and get up-to-date information about it.
 * <p>
 * Payments are used in the following YuKassa payment solutions:
 * <ul>
 *     <li>Payouts - you as a company transfer money
 *     to individuals and the self-employed
 *     (for example, pay out cashback to users).</li>
 *     <li>Safe Deal - your platform transfers payment
 *     from one individual to another as part
 *     of the created transaction.</li>
 * </ul>
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class PayoutProcessor {

    private static final String BASE_PATH = "/payouts";

    private final ApiClient client;

    /**
     * Receive information about some {@link Payout}
     * by its identifier.
     *
     * @param payoutId payout identifier
     * @return payout entity with actual status
     * @throws IllegalArgumentException if payout id is null
     *                                  or empty
     */

    public Payout findById(String payoutId) {
        if (payoutId == null || payoutId.isEmpty()) {
            throw new IllegalArgumentException("payoutId must not be null or empty");
        }
        String path = BASE_PATH + "/" + payoutId;
        return client.sendRequest("GET", path, null, null, Payout.class);
    }

    /**
     * Creates new {@link Payout} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param payoutParams   parameters for new payout
     * @param idempotencyKey idempotency key
     * @return new payout entity with actual status
     * @throws IllegalArgumentException if payout parameters is null
     */

    public Payout create(Payout payoutParams, String idempotencyKey) {
        if (payoutParams == null) {
            throw new IllegalArgumentException("payoutParams must not be null");
        }
        Payout.createValidation(payoutParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), payoutParams, Payout.class);
    }
}
