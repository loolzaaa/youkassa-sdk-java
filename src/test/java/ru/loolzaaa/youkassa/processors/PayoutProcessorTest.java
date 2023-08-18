package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.Payout;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.Currency;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PayoutProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    PayoutProcessor payoutProcessor;

    @BeforeEach
    void setUp() {
        payoutProcessor = new PayoutProcessor(client);
    }

    @Test
    void executeFindById() {
        payoutProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/payouts/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> payoutProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> payoutProcessor.findById(""));
    }

    @Test
    void executeCreate() {
        Payout payoutParams = Payout.builder()
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .payoutDestinationData(Payout.PayoutDestination.builder()
                        .type(Payout.PayoutDestination.Type.SBP)
                        .bankId("1")
                        .phone("1234567890")
                        .build())
                .build();
        payoutProcessor.create(payoutParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/payouts", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(payoutParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        Payout payoutParams = Payout.builder()
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .payoutDestinationData(Payout.PayoutDestination.builder()
                        .type(Payout.PayoutDestination.Type.SBP)
                        .bankId("1")
                        .phone("1234567890")
                        .build())
                .build();
        payoutProcessor.create(payoutParams, null);

        assertEquals("POST", client.method);
        assertEquals("/payouts", client.path);
        assertEquals(payoutParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> payoutProcessor.create(null, null));
    }
}