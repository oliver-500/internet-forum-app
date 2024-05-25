package org.unibl.etf.forum.forum_web_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum.forum_web_server.entities.AccessEntity;

import java.util.List;


@Repository
public interface AccessEntityRepository extends JpaRepository<AccessEntity, Integer> {
    @Query("SELECT a FROM AccessEntity a WHERE a.user.id = :userId")
    List<AccessEntity> getAllByUserId(@Param("userId") int userId);

    @Modifying
    @Query("DELETE FROM AccessEntity a WHERE a.user.id = :userId")
    void deleteAllByUserId(@Param("userId") int userId);

    @Query("SELECT a FROM AccessEntity a WHERE a.user.id = :userId AND a.room.id = :roomId")
    List<AccessEntity> getUserRoomAcceses(@Param("userId") int userId, @Param("roomId") int roomId);
}
