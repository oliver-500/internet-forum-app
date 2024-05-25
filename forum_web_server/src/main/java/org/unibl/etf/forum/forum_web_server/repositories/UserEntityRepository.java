package org.unibl.etf.forum.forum_web_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum.forum_web_server.entities.UserEntity;


@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    UserEntity findUserByUsername(@Param("username") String username);
}
