package com.litit.releasemanager.service;

import com.litit.releasemanager.entity.ServiceEntity;
import com.litit.releasemanager.repository.ServiceRepository;
import com.litit.releasemanager.repository.SystemVersionRepository;
import com.litit.releasemanager.resource.ServiceResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ServiceVersionServiceTest {

    public static final int VERSION_NUMBER = 3;

    @BeforeEach
    public void setUp() {
        ServiceRepository serviceRepository = Mockito.mock(ServiceRepository.class);
        SystemVersionRepository systemVersionRepository = Mockito.mock(SystemVersionRepository.class);

        ServiceEntity entity = new ServiceEntity();
        entity.setVersionNumber(VERSION_NUMBER);
        Mockito.when(serviceRepository.save(Mockito.any())).thenReturn(entity);

        sut = new SystemVersionService(serviceRepository, systemVersionRepository);
    }

    private SystemVersionService sut;


    @Test
    void shouldDeployServiceAndReturnNewSystemVersionNumber() {


        //  given
        ServiceResource serviceToDeploy = new ServiceResource("TestServiceName", VERSION_NUMBER);

        //  when
        int version = sut.deployService(serviceToDeploy);

        //  then
        Assertions.assertEquals(version, VERSION_NUMBER);
    }
}