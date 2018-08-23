package com.nif.rest.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nif.rest.test.dto.ControllerServiceDTO;
import com.nif.rest.test.dto.mapper.ControllerServiceMapper;
import com.nif.rest.test.entity.ControllerService;
import com.nif.rest.test.repository.ControllerServiceRepository;
import com.nif.rest.test.service.ControllerServiceService;
import com.nif.rest.test.service.builder.ControllerServiceBuilder;
import org.apache.nifi.web.api.entity.ControllerServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    private ControllerServiceService controllerServiceService;

    private ControllerServiceRepository controllerServiceRepository;

    private ControllerServiceMapper controllerServiceMapper;

    @Autowired
    public void setControllerServiceService(ControllerServiceService controllerServiceService) {
        this.controllerServiceService = controllerServiceService;
    }

    @Autowired
    public void setControllerServiceRepository(ControllerServiceRepository controllerServiceRepository) {
        this.controllerServiceRepository = controllerServiceRepository;
    }

    @PostMapping("/controller-services")
    public ResponseEntity addControllerService(@RequestBody ControllerServiceDTO controllerServiceDTO) {
        ControllerServiceBuilder builder = new ControllerServiceBuilder()
                .name(controllerServiceDTO.getName())
                .type(controllerServiceDTO.getType())
                .state("DISABLED");
        Map<String, String> resolveProperties = controllerServiceService.resolveProperties(controllerServiceDTO.getType(), controllerServiceDTO.getProperties());
        resolveProperties.forEach(builder::addProperty);
        ControllerServiceEntity controllerServiceEntity = controllerServiceService.addControllerService(builder.build());

        ControllerService controllerService=controllerServiceMapper.controllerServiceDTOToControllerService(controllerServiceDTO);
        controllerService.setNifiId(controllerServiceEntity.getId());
        controllerServiceRepository.save(controllerService);

        return ResponseEntity.ok(controllerServiceEntity);
    }

}
