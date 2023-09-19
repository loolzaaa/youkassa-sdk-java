package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Me;

/**
 * Processor for {@link Me} entity.
 * <p>
 * In YooKassa, each store and gateway has certain
 * settings that affect the integration and processing
 * of payments and payouts. Settings are available
 * in your personal account, but in some YooKassa payment
 * solutions it is important to receive settings via API.
 * <p>
 * Settings must be recognized via API in the following
 * YooKassa payment solutions:
 * <ul>
 *     <li>Payment splitting - you can find out the status
 *     of the seller's store connected to your site.</li>
 *     <li>Partner program - you can get help information
 *     about the store on behalf of which you perform
 *     transactions, for example, you can find out if you
 *     need to transfer data to send a receipt.</li>
 *     <li>Payouts - you can find out the payout balance
 *     of your gateway.</li>
 * </ul>
 * <p>
 * Use {@link ApiClient} for API server communication.
 *
 * @apiNote Only for those who use Split Payments,
 * Partner Program or Payouts.
 */

@RequiredArgsConstructor
public class MeProcessor {

    private static final String BASE_PATH = "/me";

    private final ApiClient client;

    /**
     * Receive information about current shop.
     * <p>
     * Can be used only if OAuth authentication
     * method configured.
     *
     * @return entity that represents current shop
     */

    public Me findMe() {
        return client.sendRequest("GET", BASE_PATH, null, null, Me.class);
    }
}
