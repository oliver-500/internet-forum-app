package org.unibl.etf.forum.forum_siem.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_siem.DTO.UserRequestDTO;
import org.unibl.etf.forum.forum_siem.entities.LogEntity;
import org.unibl.etf.forum.forum_siem.repositories.LogRepository;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void addLog(UserRequestDTO userRequest){
        LogEntity le = new LogEntity();
        le.setMessage(userRequest.toString());
        le.setId(-1);
        logRepository.save(le);
    }
}
