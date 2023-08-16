package ru.loolzaaa.youkassa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.loolzaaa.youkassa.client.RequestValidated;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CapturePayment implements RequestValidated {
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("receipt")
    private Receipt receipt;
    @JsonProperty("airline")
    private Airline airline;
    @JsonProperty("transfers")
    private List<Transfer> transfers;

    @Override
    public void validate() {
        //
    }
}
