package com.litit.releasemanager.repository;


import com.litit.releasemanager.entity.SystemVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemVersionRepository extends JpaRepository<SystemVersionEntity, Integer> {

    public SystemVersionEntity findBySystemVersionNumber(int systemVersionNumber);
}
