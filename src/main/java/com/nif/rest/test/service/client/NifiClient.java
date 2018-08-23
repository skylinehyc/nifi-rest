package com.nif.rest.test.service.client;

import com.nif.rest.test.config.NifiProperties;
import org.apache.nifi.web.api.entity.ConnectionEntity;
import org.apache.nifi.web.api.entity.ControllerServiceEntity;
import org.apache.nifi.web.api.entity.ProcessGroupEntity;
import org.apache.nifi.web.api.entity.ProcessorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class NifiClient {

    private RestTemplate restTemplate;

    private NifiProperties nifiProperties;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setNifiProperties(NifiProperties nifiProperties) {
        this.nifiProperties = nifiProperties;
    }

    public ProcessorEntity addProcessor(ProcessorEntity processor) {
        return restTemplate.postForObject(nifiProperties.getUrl().getCreateProcessors(), processor, ProcessorEntity.class, "root");
    }

    public ConnectionEntity addConnection(ConnectionEntity connection) {
        return restTemplate.postForObject(nifiProperties.getUrl().getCreateConnections(), connection, ConnectionEntity.class, "root");
    }

    public ControllerServiceEntity addControllerService(ControllerServiceEntity controllerService) {
        return restTemplate.postForObject(nifiProperties.getUrl().getCreateControllerServices(), controllerService, ControllerServiceEntity.class, "root");
    }

    public ProcessGroupEntity addProcessGroup(ProcessGroupEntity processGroup) {
        return restTemplate.postForObject(nifiProperties.getUrl().getCreateProcessGroups(), processGroup, ProcessGroupEntity.class, "root");
    }

    public ProcessorEntity getProcessor(String id) {
        return restTemplate.getForObject(nifiProperties.getUrl().getProcessors(), ProcessorEntity.class, id);
    }

    public ControllerServiceEntity getControllerService(String id) {
        return restTemplate.getForObject(nifiProperties.getUrl().getControllerServices(), ControllerServiceEntity.class, id);
    }

    public void putControllerService(String id, HttpEntity<Map<String, Object>> requestEntity) {
        restTemplate.put(nifiProperties.getUrl().getControllerServices(), requestEntity, id);
    }

    public void putProcessor(String id, HttpEntity<Map<String, Object>> requestEntity) {
        restTemplate.put(nifiProperties.getUrl().getProcessors(), requestEntity, id);
    }

}
