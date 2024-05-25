package org.unibl.etf.forum.forum_siem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.forum.forum_siem.entities.LogEntity;
@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {
}
