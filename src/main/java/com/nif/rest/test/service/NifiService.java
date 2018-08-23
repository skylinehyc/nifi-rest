package com.nif.rest.test.service;

import com.nif.rest.test.service.builder.ConnectionBuilder;
import com.nif.rest.test.service.util.NifiClient;
import org.apache.nifi.web.api.entity.ConnectionEntity;
import org.apache.nifi.web.api.entity.ProcessGroupEntity;
import org.apache.nifi.web.api.entity.ProcessorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NifiService {

    private NifiClient nifiClientUtil;

    @Autowired
    public void setNifiClientUtil(NifiClient nifiClientUtil) {
        this.nifiClientUtil = nifiClientUtil;
    }

    public ConnectionEntity connectProcessors(
            ProcessorEntity source, ProcessorEntity destination, String... connectionRelationships) {

        ConnectionBuilder connectionBuilder = new ConnectionBuilder()
                .source(source)
                .destination(destination);

        for (String connectionRelationship : connectionRelationships) {
            connectionBuilder.addConnectionRelationship(connectionRelationship);
        }

        ConnectionEntity connection = connectionBuilder.build();

        return this.nifiClientUtil.addConnection(connection);
    }

    public ProcessGroupEntity addProcessGroup(ProcessGroupEntity processGroup) {
        return this.nifiClientUtil.addProcessGroup(processGroup);
    }

}
