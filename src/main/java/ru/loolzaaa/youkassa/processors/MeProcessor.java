package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Me;

@RequiredArgsConstructor
public class MeProcessor {

    private static final String BASE_PATH = "/me";

    private final ApiClient client;

    public Me findMe() {
        return client.sendRequest("GET", BASE_PATH, null, null, Me.class);
    }
}
