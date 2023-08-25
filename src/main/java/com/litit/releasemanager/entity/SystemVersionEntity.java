package com.litit.releasemanager.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class SystemVersionEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer systemVersionNumber;

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    @OneToMany(targetEntity = ServiceEntity.class, cascade = CascadeType.ALL)
    private List<ServiceEntity> services;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSystemVersionNumber() {
        return systemVersionNumber;
    }

    public void setSystemVersionNumber(Integer systemVersionNumber) {
        this.systemVersionNumber = systemVersionNumber;
    }
}
