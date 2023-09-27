package com.hydrogarden.server.controllers.requestResponseEntities;

import lombok.Data;

@Data
public class RenameCircuitRequest {
    private int id;
    private String circuitName;
}
