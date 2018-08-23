package com.nif.rest.test.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class ControllerServiceDTO {

    @NotNull
    private String name;

    @NotNull
    private String type;

    private Map<String, String> properties;

}
