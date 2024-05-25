package org.unibl.etf.forum_authentication_controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum_authentication_controller.model.entities.AccessEntity;

@Repository
public interface AccessEntityRepository extends JpaRepository<AccessEntity, Integer> {
    @Query("SELECT a from AccessEntity a WHERE a.user.id = :userId AND a.permission.id = :permissionId AND a.room.id = :roomId")
    AccessEntity getByUserAndPermissionAndRoom(@Param("userId") int userId, @Param("permissionId")int permissionId, @Param("roomId")int roomId);
}
