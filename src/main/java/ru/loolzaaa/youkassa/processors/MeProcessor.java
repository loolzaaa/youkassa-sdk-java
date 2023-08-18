package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Me;

/**
 * Processor for {@link Me} entity.
 * <p>
 * Use {@link ApiClient} for API server communication.
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
