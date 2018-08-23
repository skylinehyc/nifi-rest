package com.nif.rest.test.controller;

import com.nif.rest.test.builder.ControllerServiceBuilder;
import com.nif.rest.test.entity.ControllerService;
import com.nif.rest.test.service.ControllerServiceService;
import org.apache.nifi.web.api.entity.ControllerServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    private ControllerServiceService controllerServiceService;

    @Autowired
    public void setControllerServiceService(ControllerServiceService controllerServiceService) {
        this.controllerServiceService = controllerServiceService;
    }

    @PostMapping("/controller-services")
    public ResponseEntity addControllerService(@RequestBody ControllerService controllerService) {
        ControllerServiceBuilder builder = new ControllerServiceBuilder()
                .name(controllerService.getName())
                .type(controllerService.getType())
                .state(controllerService.getState());
        Map<String, String> properties = controllerServiceService.resolveProperties(controllerService.getType(), controllerService.getProperties());
        properties.forEach(builder::addProperty);

        ControllerServiceEntity controllerServiceEntity = controllerServiceService.addControllerService(builder.build());
        return ResponseEntity.ok(controllerServiceEntity);
    }
}
