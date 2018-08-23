package com.nif.rest.test.repository;

import com.nif.rest.test.entity.ControllerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControllerServiceRepository extends JpaRepository<ControllerService,String> {
}
