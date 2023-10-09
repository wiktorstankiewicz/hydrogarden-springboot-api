package com.hydrogarden.server.controllers.requestResponseEntities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmExecutionOfTaskDTO {
    @NotNull
    private Integer id;
}
