package com.litit.releasemanager.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiceEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer versionNumber;

    @ManyToOne(targetEntity = SystemVersionEntity.class)
    private SystemVersionEntity systemVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public SystemVersionEntity getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(SystemVersionEntity systemVersion) {
        this.systemVersion = systemVersion;
    }
}
