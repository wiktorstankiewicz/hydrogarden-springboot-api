package com.hydrogarden.server.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RenameCircuitDTO {
    @NotNull
    private Integer id;
    @NotNull
    @NotBlank
    private String circuitName;
}
