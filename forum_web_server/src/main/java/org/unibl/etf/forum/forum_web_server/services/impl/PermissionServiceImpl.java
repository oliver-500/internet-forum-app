package org.unibl.etf.forum.forum_web_server.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.PermissionDTO;
import org.unibl.etf.forum.forum_web_server.repositories.PermissionEntityRepository;
import org.unibl.etf.forum.forum_web_server.services.PermissionService;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionEntityRepository repo;
    private final ModelMapper modelMapper;

    public PermissionServiceImpl(PermissionEntityRepository repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PermissionDTO> getAll() {
        return repo.findAll().stream().map(p -> {
           return modelMapper.map(p, PermissionDTO.class);
        }).collect(Collectors.toList());
    }

    @Override
    public PermissionDTO getOneById(int id) {
       return modelMapper.map(repo.getById(id), PermissionDTO.class);
    }

    @Override
    public PermissionDTO getOneByName(String name) {
        return modelMapper.map(repo.getOne(name), PermissionDTO.class);
    }
}
