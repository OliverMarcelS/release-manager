package com.litit.releasemanager.service;


import com.litit.releasemanager.entity.ServiceEntity;
import com.litit.releasemanager.entity.SystemVersionEntity;
import com.litit.releasemanager.repository.ServiceRepository;
import com.litit.releasemanager.repository.SystemVersionRepository;
import com.litit.releasemanager.resource.ServiceResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemVersionService {

    private final ServiceRepository serviceRepository;
    private final SystemVersionRepository systemVersionRepository;

    public SystemVersionService(ServiceRepository serviceRepository, SystemVersionRepository systemVersionRepository) {
        this.serviceRepository = serviceRepository;
        this.systemVersionRepository = systemVersionRepository;
    }

    public int deployService(ServiceResource serviceResource) {

        List<ServiceEntity> all = serviceRepository.findAll();

        if (alreadyDeployed(serviceResource)) {
            return latestSystemVersion();
        }

        ServiceEntity serviceEntity = createServiceEntity(serviceResource);
        SystemVersionEntity systemVersionEntity = createSystemVersionEntityFromDeployedService(serviceEntity);

        return systemVersionEntity.getSystemVersionNumber();
    }

    public List<ServiceResource> getServicesBySystemVersion(int systemVersion) {

        SystemVersionEntity systemVersionEntity = systemVersionRepository.findBySystemVersionNumber(systemVersion);
        if (Objects.isNull(systemVersionEntity)) {
            return Arrays.asList();
        }

        return systemVersionEntity.getServices().stream().map(
                serviceEntity -> mapServiceEntityToResource(serviceEntity)).collect(Collectors.toList());
    }

    private boolean alreadyDeployed(ServiceResource resource) {

        return serviceRepository.findAll().stream().filter(
                serviceEntity -> serviceEntity.getVersionNumber() == resource.version()).findFirst().isPresent();
    }

    private int latestSystemVersion() {
        //return systemVersionRepository.findAll().stream().mapToInt(systemVersionEntity -> systemVersionEntity.getSystemVersionNumber()).max().orElse(-1);
        Optional<SystemVersionEntity> systemVersionEntity = latestSystemVersionEntity();
        return systemVersionEntity.isPresent() ? systemVersionEntity.get().getSystemVersionNumber() : 0;
    }

    private Optional<SystemVersionEntity> latestSystemVersionEntity() {
        return systemVersionRepository.findAll().stream().max(Comparator.comparing(SystemVersionEntity::getSystemVersionNumber));
    }

    private ServiceEntity createServiceEntity(ServiceResource serviceResource) {

        //  move to mapper/projector, use mapper helper
        ServiceEntity entity = new ServiceEntity();
        entity.setName(serviceResource.name());
        entity.setVersionNumber(serviceResource.version());

        ServiceEntity savedEntity = serviceRepository.save(entity);
        return savedEntity;
    }

    private SystemVersionEntity createSystemVersionEntityFromDeployedService(ServiceEntity deployedService) {

        SystemVersionEntity systemVersionEntity = new SystemVersionEntity();
        systemVersionEntity.setSystemVersionNumber(latestSystemVersion() + 1);

        //  no other services deployed yet
        if (!latestSystemVersionEntity().isPresent()) {
            systemVersionEntity.setServices(Arrays.asList(deployedService));
            return systemVersionRepository.save(systemVersionEntity);
        }

        //  other services deployed, find it and replace the new one if exists
        List<ServiceEntity> services = latestSystemVersionEntity().get().getServices();
        Optional<ServiceEntity> existingService = services.stream().filter(s -> s.getName().equals(deployedService.getName())).findFirst();

        if (existingService.isPresent()) {
            services.remove(existingService.get());
        }

        services.add(deployedService);
        systemVersionEntity.setServices(services);
        return systemVersionRepository.save(systemVersionEntity);
    }

    //  move to mapper
    private ServiceResource mapServiceEntityToResource(ServiceEntity serviceEntity) {
        return new ServiceResource(serviceEntity.getName(), serviceEntity.getVersionNumber());
    }
}
