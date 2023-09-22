package com.hydrogarden.server.controllers.requestResponseEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydrogarden.server.domain.dto.CircuitDto;
import com.hydrogarden.server.domain.dto.CircuitScheduleDto;
import com.hydrogarden.server.domain.entities.Circuit;
import com.hydrogarden.server.domain.entities.CircuitSchedule;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public class CircuitInfoResponse {
    @JsonProperty("circuit")
    @NotNull
    private CircuitDto circuitDto;

    @JsonProperty("circuitSchedule")
    private CircuitScheduleDto circuitScheduleDto;

    public CircuitInfoResponse(Circuit circuit, CircuitSchedule circuitSchedule) {
        circuitDto = new CircuitDto(circuit);
        if(circuitSchedule != null){
            circuitScheduleDto = new CircuitScheduleDto(circuitSchedule);
        }
    }
}
