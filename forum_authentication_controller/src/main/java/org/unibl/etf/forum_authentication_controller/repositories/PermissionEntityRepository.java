package org.unibl.etf.forum_authentication_controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum_authentication_controller.model.entities.PermissionEntity;

@Repository
public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, Integer> {

    @Query("SELECT p from PermissionEntity p WHERE p.name = :name")
    PermissionEntity getByName(@Param("name") String name);
}
