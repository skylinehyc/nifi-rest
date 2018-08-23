package com.nif.rest.test.service;

import com.nif.rest.test.config.NifiProperties;
import com.nif.rest.test.service.util.NifiClient;
import org.apache.nifi.web.api.entity.ControllerServiceEntity;
import org.apache.nifi.web.api.entity.ControllerServiceReferencingComponentEntity;
import org.apache.nifi.web.api.entity.ProcessorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.nif.rest.test.service.util.RequestUtil.addHeader;
import static com.nif.rest.test.service.util.RequestUtil.getRevision;
import static org.apache.nifi.web.api.entity.ActivateControllerServicesEntity.STATE_DISABLED;
import static org.apache.nifi.web.api.entity.ActivateControllerServicesEntity.STATE_ENABLED;

@Service
public class ControllerServiceService {

    private static Map<String, String> DATABASE_DRIVER = new HashMap<String, String>() {
        {
            put("mysql", "com.mysql.jdbc.Driver");
        }
    };

    private NifiClient nifiClientUtil;

    private ProcessorService processorService;

    private NifiProperties nifiProperties;

    @Autowired
    public void setNifiClientUtil(NifiClient nifiClientUtil) {
        this.nifiClientUtil = nifiClientUtil;
    }

    @Autowired
    public void setProcessorService(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @Autowired
    public void setNifiProperties(NifiProperties nifiProperties) {
        this.nifiProperties = nifiProperties;
    }

    public ControllerServiceEntity addControllerService(ControllerServiceEntity controllerService) {
        return this.nifiClientUtil.addControllerService(controllerService);
    }

    public ControllerServiceEntity getControllerService(String id) {
        return this.nifiClientUtil.getControllerService(id);
    }

    public void switchControllerServiceState(ControllerServiceEntity controllerServiceEntity, String state) {
        if (controllerServiceEntity.getComponent().getState().equals(state)) return;

        Map<String, Object> component = new HashMap<>();
        component.put("id", controllerServiceEntity.getId());
        component.put("state", state);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("revision", getRevision(controllerServiceEntity));
        requestBody.put("component", component);

        this.nifiClientUtil.putControllerService(controllerServiceEntity.getId(), addHeader(requestBody));
    }

    // STOP all referencing processors and DISABLE controller service
    public void preConfigureControllerService(String id) {
        ControllerServiceEntity controllerServiceEntity = getControllerService(id);

        Set<ControllerServiceReferencingComponentEntity> referencingComponents = controllerServiceEntity.getComponent().getReferencingComponents();
        for (ControllerServiceReferencingComponentEntity componentEntity : referencingComponents) {
            //TODO: could skip?
            ProcessorEntity processorEntity = processorService.getProcessor(componentEntity.getComponent().getId());
            if (processorEntity.getComponent().getState().equals("RUNNING")) {
                processorService.switchProcessorState(processorEntity, "STOPPED");
            }
        }

        if (controllerServiceEntity.getComponent().getState().equals(STATE_ENABLED)) {
            switchControllerServiceState(controllerServiceEntity, STATE_DISABLED);
        }
    }

    public Map<String, String> resolveProperties(String type, Map<String, String> properties) {
        Map<String, String> resolvedProperties = new HashMap<>();
        switch (type) {
            case "org.apache.nifi.dbcp.DBCPConnectionPool":
                resolvedProperties = dbcpResolver(properties);
                break;
            case "org.apache.nifi.processors.standard.QueryDatabaseTable":
                resolvedProperties = queryDatabaseTableResolver(properties);
                break;
            case "org.apache.nifi.processors.standard.ConvertJSONToSQL":
                resolvedProperties = jsonToSqlResolver(properties);
                break;
            case "org.apache.nifi.processors.standard.PutSQL":
                resolvedProperties = putSqlResolver(properties);
                break;
        }
        return resolvedProperties;
    }

    private Map<String, String> dbcpResolver(Map<String, String> properties) {
        Map<String, String> resolvedProperties = new HashMap<>();
        String jdbcUrl = "jdbc:" + properties.get("databaseType") + "://" + properties.get("ip") + ":" + properties.get("port") + "/" + properties.get("database") + "?useUnicode=true&characterEncoding=utf-8";
        resolvedProperties.put("Database Connection URL", jdbcUrl);
        resolvedProperties.put("Database Driver Class Name", DATABASE_DRIVER.get(properties.get("databaseType")));
        resolvedProperties.put("database-driver-locations", nifiProperties.getDriverLocation().get(properties.get("databaseType")));
        resolvedProperties.put("Database User", properties.get("databaseUser"));
        resolvedProperties.put("Password", properties.get("password"));
        return resolvedProperties;
    }

    private Map<String, String> queryDatabaseTableResolver(Map<String, String> properties) {
        Map<String, String> resolvedProperties = new HashMap<>();
        resolvedProperties.put("Database Connection Pooling Service", "");
        resolvedProperties.put("Database Type", "");
        resolvedProperties.put("Table Name", "");
        resolvedProperties.put("Columns to Return", "");
        resolvedProperties.put("Maximum-value Columns", "");
        resolvedProperties.put("Additional WHERE clause", "");
        return resolvedProperties;
    }


    private Map<String, String> jsonToSqlResolver(Map<String, String> properties) {
        Map<String, String> resolvedProperties = new HashMap<>();
        resolvedProperties.put("JDBC Connection Pool", "");
        resolvedProperties.put("Statement Type", "");
        resolvedProperties.put("Table Name", properties.get("tableName"));
        resolvedProperties.put("Translate Field Names", properties.get("fieldNames"));
        resolvedProperties.put("Unmatched Field Behavior", "");
        resolvedProperties.put("Unmatched Column Behavior", "");
        return resolvedProperties;
    }

    private Map<String, String> putSqlResolver(Map<String, String> properties) {
        Map<String, String> resolvedProperties = new HashMap<>();
        resolvedProperties.put("JDBC Connection Pool", "");
        resolvedProperties.put("Support Fragmented Transactions", "");
        return resolvedProperties;
    }

}
