package com.nif.rest.test.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
public class ControllerService implements Serializable {

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "state")
    private String state;

    private Map<String, String> properties;

}
