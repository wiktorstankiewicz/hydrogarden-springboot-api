package com.hydrogarden.server.controllers.requestResponseEntities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateCircuitScheduleDTO {
    @JsonProperty()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonProperty()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate startDate;
    @JsonProperty()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate endDate;
    @JsonProperty()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalTime startTime;
    @JsonProperty()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer frequencyDays;
    @JsonProperty()
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer wateringTime;
}
