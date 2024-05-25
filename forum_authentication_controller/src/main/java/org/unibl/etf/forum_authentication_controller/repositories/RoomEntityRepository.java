package org.unibl.etf.forum_authentication_controller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum_authentication_controller.model.entities.RoomEntity;

@Repository
public interface RoomEntityRepository extends JpaRepository<RoomEntity, Integer> {
}
