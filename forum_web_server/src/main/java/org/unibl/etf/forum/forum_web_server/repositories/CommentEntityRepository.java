package org.unibl.etf.forum.forum_web_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum.forum_web_server.entities.CommentEntity;
import org.unibl.etf.forum.forum_web_server.entities.UserEntity;

import java.util.List;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
//    @Query("SELECT COUNT(c) FROM CommentEntity c WHERE c.room.id = :roomId AND c.approved = false")
//    List<CommentEntity> getRoomNewCommentsCount(@Param("roomId") Integer roomId);

    @Query("SELECT COUNT(c) FROM CommentEntity c where c.room.id = :roomId AND c.approved = false")
    int getNumberOfNewComments(@Param("roomId") Integer roomId);
}
