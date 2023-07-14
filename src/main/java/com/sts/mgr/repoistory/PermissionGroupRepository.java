package com.sts.mgr.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.mgr.entities.PermissionGroupEntity;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroupEntity, Integer> {

}
