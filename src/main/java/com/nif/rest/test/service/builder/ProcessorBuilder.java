package com.nif.rest.test.service.builder;

import org.apache.nifi.web.api.dto.*;
import org.apache.nifi.web.api.entity.ProcessorEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessorBuilder {

    private String name;
    private String type;
    private String schedulingPeriod;
    private PositionDTO position;
    private Map<String, String> config = new HashMap<>();
    private Set<String> autoTerminatedRelationships = new HashSet<>();

    public ProcessorBuilder() {
    }

    public ProcessorBuilder(ProcessorEntity processor) {
        this.name = processor.getComponent().getName();
        this.type = processor.getComponent().getType();
        this.position = processor.getComponent().getPosition();
        this.autoTerminatedRelationships = processor.getComponent().getRelationships()
                .stream()
                .filter(RelationshipDTO::isAutoTerminate)
                .map(RelationshipDTO::getName)
                .collect(Collectors.toSet());
    }

    public ProcessorBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ProcessorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProcessorBuilder position(PositionDTO position) {
        this.position = position;
        return this;
    }

    public ProcessorBuilder autoTerminateAt(String relationship) {
        this.autoTerminatedRelationships.add(relationship);
        return this;
    }

    public ProcessorBuilder config(Map<String, String> config) {
        this.config = config;
        return this;
    }

    public ProcessorBuilder addConfigProperty(String property, String value) {
        this.config.put(property, value);
        return this;
    }

    public ProcessorBuilder scheduling(String schedulingPeriod) {
        this.schedulingPeriod = schedulingPeriod;
        return this;
    }

    public ProcessorEntity build() {
        RevisionDTO revision = new RevisionDTO();
        revision.setVersion(0L);

        ProcessorConfigDTO processorConfig = new ProcessorConfigDTO();
        processorConfig.setSchedulingPeriod(this.schedulingPeriod);
        processorConfig.setProperties(this.config);
        processorConfig.setAutoTerminatedRelationships(this.autoTerminatedRelationships);

        ProcessorDTO component = new ProcessorDTO();
        component.setName(this.name);
        component.setType(this.type);
        component.setConfig(processorConfig);
        component.setPosition(this.position);

        ProcessorEntity processorEntity = new ProcessorEntity();
        processorEntity.setRevision(revision);
        processorEntity.setComponent(component);

        return processorEntity;
    }

}
