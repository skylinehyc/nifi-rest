package com.nif.rest.test.service;

import com.nif.rest.test.service.client.NifiClient;
import org.apache.nifi.web.api.dto.ProcessorConfigDTO;
import org.apache.nifi.web.api.entity.ControllerServiceEntity;
import org.apache.nifi.web.api.entity.ProcessorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.nif.rest.test.service.util.RequestUtil.addHeader;
import static com.nif.rest.test.service.util.RequestUtil.getRevision;

@Service
public class ProcessorService {

    private NifiClient nifiClientUtil;

    @Autowired
    public void setNifiClientUtil(NifiClient nifiClientUtil) {
        this.nifiClientUtil = nifiClientUtil;
    }

    public ProcessorEntity getProcessor(String id) {
        return nifiClientUtil.getProcessor(id);
    }

    public ProcessorEntity addProcessor(ProcessorEntity processor) {
        return this.nifiClientUtil.addProcessor(processor);
    }

    //TODO: needs return value
    // allowable state values = "RUNNING, STOPPED, DISABLED"
    public void switchProcessorState(ProcessorEntity processorEntity, String state) {
        if (processorEntity.getComponent().getState().equals(state)) return;

        Map<String, Object> component = new HashMap<>();
        component.put("id", processorEntity.getId());
        component.put("state", state);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("revision", getRevision(processorEntity));
        requestBody.put("component", component);

        this.nifiClientUtil.putProcessor(processorEntity.getId(), addHeader(requestBody));
    }


    public void configureControllerService(ControllerServiceEntity controllerServiceEntity, Map<String, String> properties) {
        Map<String, Object> component = new HashMap<>();
        component.put("id", controllerServiceEntity.getId());
        component.put("name", controllerServiceEntity.getComponent().getName());
        component.put("properties", properties);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("revision", getRevision(controllerServiceEntity));
        requestBody.put("component", component);

        this.nifiClientUtil.putControllerService(controllerServiceEntity.getId(), addHeader(requestBody));
    }

    public void configureProcessor(ProcessorEntity processorEntity, Map<String, String> properties) {
        ProcessorConfigDTO config = processorEntity.getComponent().getConfig();
        config.setProperties(properties);

        Map<String, Object> component = new HashMap<>();
        component.put("config", config);
        component.put("id", processorEntity.getId());
        component.put("name", processorEntity.getComponent().getName());
        component.put("state", processorEntity.getComponent().getState());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("revision", getRevision(processorEntity));
        requestBody.put("component", component);

        nifiClientUtil.putProcessor(processorEntity.getId(), addHeader(requestBody));
    }

    // STOP processor
    public void preConfigureProcessor(String id) {
        ProcessorEntity processorEntity = getProcessor(id);
        if (processorEntity.getComponent().getState().equals("RUNNING")) {
            switchProcessorState(processorEntity, "STOPPED");
        }
    }
}
