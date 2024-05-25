package org.unibl.etf.forum.forum_web_server.services.impl;


import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.AccessDTO;
import org.unibl.etf.forum.forum_web_server.dto.UserAccessDTO;
import org.unibl.etf.forum.forum_web_server.dto.UserRoomAccessDTO;
import org.unibl.etf.forum.forum_web_server.entities.AccessEntity;
import org.unibl.etf.forum.forum_web_server.repositories.AccessEntityRepository;
import org.unibl.etf.forum.forum_web_server.services.AccessService;
import org.unibl.etf.forum.forum_web_server.services.PermissionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessServiceImpl implements AccessService {

    private final AccessEntityRepository accessEntityRepository;

    private final ModelMapper mapper;

    private final PermissionService permissionService;

    public AccessServiceImpl(AccessEntityRepository accessEntityRepository, ModelMapper mapper, PermissionService permissionService) {
        this.accessEntityRepository = accessEntityRepository;
        this.mapper = mapper;
        this.permissionService = permissionService;
    }

    @Override
    public UserAccessDTO getAllForUser(int id) {

        UserAccessDTO userAccessDTO = new UserAccessDTO();
        userAccessDTO.setUserId(id);
        userAccessDTO.setAccesses(accessEntityRepository.getAllByUserId(id).stream().map(ua ->{
            AccessDTO accessDTO = mapper.map(ua, AccessDTO.class);

            return accessDTO;
        }).collect(Collectors.toList()));
        return userAccessDTO;
    }
    @Override
    public void deleteAllForUser(int id){
        accessEntityRepository.deleteAllByUserId(id);
    }

    @Override
    public void add(List<AccessEntity> accesses) {
        accesses.forEach(a -> accessEntityRepository.save(a));
    }

    @Override
    public List<AccessDTO> getMyAccesses(UserRoomAccessDTO req) {
        return accessEntityRepository.getUserRoomAcceses(req.getUserId(), req.getRoomId()).stream().map(a ->{
            return mapper.map(a, AccessDTO.class);
        }).collect(Collectors.toList());
    }
}
