package org.unibl.etf.forum_authentication_controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum_authentication_controller.model.entities.CommentEntity;
@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
}
