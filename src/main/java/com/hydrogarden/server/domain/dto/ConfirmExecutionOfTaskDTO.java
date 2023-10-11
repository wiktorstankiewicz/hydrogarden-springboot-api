package com.hydrogarden.server.domain.dto;

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
