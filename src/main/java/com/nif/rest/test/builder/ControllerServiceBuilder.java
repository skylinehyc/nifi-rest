package com.nif.rest.test.builder;

import org.apache.nifi.web.api.dto.ControllerServiceDTO;
import org.apache.nifi.web.api.dto.RevisionDTO;
import org.apache.nifi.web.api.entity.ControllerServiceEntity;

import java.util.HashMap;
import java.util.Map;

public class ControllerServiceBuilder {

    private String name;
    private String type;
    private String state;
    private Map<String, String> properties = new HashMap<>();

    public ControllerServiceBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ControllerServiceBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ControllerServiceBuilder state(String state) {
        this.state = state;
        return this;
    }

    public ControllerServiceBuilder addProperty(String property, String value) {
        this.properties.put(property, value);
        return this;
    }

    public ControllerServiceEntity build() {
        RevisionDTO revision = new RevisionDTO();
        revision.setVersion(0L);

        ControllerServiceDTO component = new ControllerServiceDTO();
        component.setName(this.name);
        component.setType(this.type);
        component.setState(this.state);
        component.setProperties(this.properties);

        ControllerServiceEntity controllerService = new ControllerServiceEntity();
        controllerService.setRevision(revision);
        controllerService.setComponent(component);

        return controllerService;
    }
}
