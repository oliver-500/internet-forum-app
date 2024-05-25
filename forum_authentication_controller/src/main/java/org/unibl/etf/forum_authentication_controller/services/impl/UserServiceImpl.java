package org.unibl.etf.forum_authentication_controller.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum_authentication_controller.model.dto.UserDTO;
import org.unibl.etf.forum_authentication_controller.model.entities.UserEntity;
import org.unibl.etf.forum_authentication_controller.repositories.UserEntityRepository;
import org.unibl.etf.forum_authentication_controller.services.UserService;
@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean existsByUsername(String username) {
        if(userEntityRepository.findAll().stream().filter(u -> u.getUsername().equals(username)).count() > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        if(userEntityRepository.findAll().stream().filter(u -> u.getEmail().equals(email)).count() > 0){
            return true;
        }
        return false;
    }

    @Override
    public UserDTO saveUser(UserDTO user) {


        System.out.println(user.getUserGroup() + "_----------" +  user.getActivationCode());
        System.out.println(modelMapper.map(user, UserEntity.class));
        UserEntity u = userEntityRepository.save(modelMapper.map(user, UserEntity.class));

        return modelMapper.map(u, UserDTO.class);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        UserEntity userEntity =  userEntityRepository.findUserByUsername(username);
        if(userEntity != null) return modelMapper.map(userEntity, UserDTO.class);
        else return null;
    }
}
