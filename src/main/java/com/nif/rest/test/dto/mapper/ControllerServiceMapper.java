package com.nif.rest.test.dto.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nif.rest.test.dto.ControllerServiceDTO;
import com.nif.rest.test.entity.ControllerService;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ControllerServiceMapper {
    public ControllerServiceDTO controllerServiceToControllerServiceDTO(ControllerService controllerService) {
        ControllerServiceDTO controllerServiceDTO = new ControllerServiceDTO();
        controllerServiceDTO.setName(controllerService.getName());
        controllerServiceDTO.setType(controllerService.getType());
        try {
            controllerServiceDTO.setProperties(new ObjectMapper().readValue(controllerService.getProperties(), HashMap.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controllerServiceDTO;
    }

    public ControllerService controllerServiceDTOToControllerService(ControllerServiceDTO controllerServiceDTO) {
        ControllerService controllerService = new ControllerService();
        controllerService.setName(controllerServiceDTO.getName());
        controllerService.setType(controllerServiceDTO.getType());
        controllerService.setProperties(new JSONObject(controllerServiceDTO.getProperties()).toString());
        return controllerService;
    }
}
