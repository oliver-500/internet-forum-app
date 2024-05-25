package org.unibl.etf.forum.forum_web_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum.forum_web_server.entities.PermissionEntity;


@Repository
public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, Integer> {
    @Query("SELECT p FROM PermissionEntity p WHERE p.name = :name")
    PermissionEntity getOne(@Param("name") String name);
}
