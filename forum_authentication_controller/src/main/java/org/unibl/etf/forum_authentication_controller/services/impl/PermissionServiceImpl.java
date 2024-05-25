package org.unibl.etf.forum_authentication_controller.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum_authentication_controller.model.dto.PermissionDTO;
import org.unibl.etf.forum_authentication_controller.repositories.PermissionEntityRepository;
import org.unibl.etf.forum_authentication_controller.services.PermissionService;
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionEntityRepository repo;
    private final ModelMapper mapper;

    public PermissionServiceImpl(PermissionEntityRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }


    @Override
    public PermissionDTO getOneByName(String name) {
        return mapper.map(repo.getByName(name), PermissionDTO.class);
    }
}
