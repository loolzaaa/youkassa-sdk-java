package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.SbpParticipantBank;

/**
 * Processor for {@link SbpParticipantBank} entity.
 * <p>
 * The API allows you to receive information
 * about SBP participants.
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class SbpParticipantBankProcessor {

    private static final String BASE_PATH = "/sbp_banks";

    private final ApiClient client;

    /**
     * Receive information about SBP participants.
     *
     * @return entity that represents SBP participants
     */

    public SbpParticipantBank findSbpParticipants() {
        return client.sendRequest("GET", BASE_PATH, null, null, SbpParticipantBank.class);
    }
}
