package ru.loolzaaa.youkassa.processors;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.PersonalData;

import java.util.Map;
import java.util.UUID;

/**
 * Processor for {@link PersonalData} entity.
 * <p>
 * The API allows you to create personal data,
 * as well as receive information about them.
 * <p>
 * Use {@link ApiClient} for API server communication.
 */

@RequiredArgsConstructor
public class PersonalDataProcessor {

    private static final String BASE_PATH = "/personal_data";

    private final ApiClient client;

    /**
     * Receive information about some {@link PersonalData}
     * by its identifier.
     *
     * @param personalDataId personal data identifier
     * @return personal data entity with actual status
     * @throws IllegalArgumentException if personal data id is null
     *                                  or empty
     */

    public PersonalData findById(String personalDataId) {
        if (personalDataId == null || personalDataId.isEmpty()) {
            throw new IllegalArgumentException("personalDataId must not be null or empty");
        }
        String path = BASE_PATH + "/" + personalDataId;
        return client.sendRequest("GET", path, null, null, PersonalData.class);
    }

    /**
     * Creates new {@link PersonalData} entity
     * with certain parameters.
     * <p>
     * Generate random idempotency key
     * if corresponding argument is null.
     *
     * @param personalDataParams parameters for new personal data
     * @param idempotencyKey     idempotency key
     * @return new personal data entity with actual status
     * @throws IllegalArgumentException if personal data parameters is null
     */

    public PersonalData create(PersonalData personalDataParams, String idempotencyKey) {
        if (personalDataParams == null) {
            throw new IllegalArgumentException("personalDataParams must not be null");
        }
        PersonalData.createValidation(personalDataParams);
        if (idempotencyKey == null) {
            idempotencyKey = UUID.randomUUID().toString();
        }
        return client.sendRequest("POST", BASE_PATH, Map.of("Idempotence-Key", idempotencyKey), personalDataParams, PersonalData.class);
    }
}
