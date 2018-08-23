package com.nif.rest.test.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
public class ControllerService implements Serializable {

    @Id
    @GenericGenerator(name = "components", strategy = "uuid")
    @GeneratedValue(generator = "components")
    @Column(name = "id")
    private String id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "properties")
    private String properties;

    @NotNull
    @Column(name = "nifiId")
    private String nifiId;
}
