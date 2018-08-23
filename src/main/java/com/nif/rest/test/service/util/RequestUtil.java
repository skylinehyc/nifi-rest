package com.nif.rest.test.service.util;

import org.apache.nifi.web.api.entity.ComponentEntity;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestUtil {

    public static Map<String, Object> getRevision(ComponentEntity componentEntity) {
        Map<String, Object> revision = new HashMap<>();
        revision.put("clientId", "156");
        revision.put("version", componentEntity.getRevision().getVersion());
        return revision;
    }

    public static HttpEntity<Map<String, Object>> addHeader(Map<String, Object> body) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }
}
