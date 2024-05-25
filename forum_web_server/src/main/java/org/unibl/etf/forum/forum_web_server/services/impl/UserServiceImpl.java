package org.unibl.etf.forum.forum_web_server.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.UserDTO;
import org.unibl.etf.forum.forum_web_server.dto.requests.UserActivationRequest;
import org.unibl.etf.forum.forum_web_server.dto.responses.PredicateResponse;
import org.unibl.etf.forum.forum_web_server.dto.responses.UserWithActivationInfo;
import org.unibl.etf.forum.forum_web_server.entities.AccessEntity;
import org.unibl.etf.forum.forum_web_server.entities.PermissionEntity;
import org.unibl.etf.forum.forum_web_server.entities.RoomEntity;
import org.unibl.etf.forum.forum_web_server.entities.UserEntity;
import org.unibl.etf.forum.forum_web_server.repositories.UserEntityRepository;
import org.unibl.etf.forum.forum_web_server.services.AccessService;
import org.unibl.etf.forum.forum_web_server.services.PermissionService;
import org.unibl.etf.forum.forum_web_server.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    private final PermissionService permissionService;

    private final AccessService accessService;

    @Value("${spring.mail.subject}")
    private String emailSubject;

    @Value("${spring.mail.username}")
    private String senderAddress;


    private final JavaMailSender emailSender;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserEntityRepository userEntityRepository, PermissionService permissionService, AccessService accessService, JavaMailSender emailSender, ModelMapper modelMapper) {
        this.userEntityRepository = userEntityRepository;
        this.permissionService = permissionService;
        this.accessService = accessService;
        this.emailSender = emailSender;
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
    public boolean saveUser(UserDTO user) {


        System.out.println(user.getUserGroup());
        System.out.println(modelMapper.map(user, UserEntity.class));
        UserEntity u = userEntityRepository.save(modelMapper.map(user, UserEntity.class));

        if(u != null) return true;
        else return false;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        UserEntity userEntity =  userEntityRepository.findUserByUsername(username);
        if(userEntity != null) return modelMapper.map(userEntity, UserDTO.class);
        else return null;
    }

    @Override
    public List<UserWithActivationInfo> findAllWithActivationInfo() {
        return userEntityRepository.findAll().stream().map(u -> {
            return modelMapper.map(u, UserWithActivationInfo.class);
        }
        ).collect(Collectors.toList());
    }

    @Override
    public PredicateResponse activateUser(UserActivationRequest req) {
        PredicateResponse response = new PredicateResponse();
        System.out.println("1");
        Optional<UserEntity> userEntity = userEntityRepository.findById(req.getUserId());
        if(!userEntity.isPresent()) {
            response.setSuccessful(false);
            return response;
        }
        userEntity.get().setRegistered(req.isActivated());
        userEntity.get().setUserGroup(req.getUserType());
        System.out.println("2");
        accessService.deleteAllForUser(userEntity.get().getId());
        System.out.println("3");
        userEntityRepository.save(userEntity.get());
        System.out.println("4");
        accessService.add( req.getUserAccess().getAccesses().stream().map(a -> {
           // return modelMapper.map(a, AccessEntity.class);
            AccessEntity ae = new AccessEntity();

            ae.setUser(userEntity.get());
            RoomEntity re = new RoomEntity();
            System.out.println("5");
            re.setId(a.getRoom().getId());
            ae.setRoom(re);


            ae.setPermission(modelMapper.map(permissionService.getOneByName(a.getPermission().getName()), PermissionEntity.class));
            return ae;

        }).collect(Collectors.toList()));
       if(req.isActivated()) sendNotificationOfAccountActivation(userEntity.get().getEmail(), userEntity.get().getUsername() + ", your acccount on our forum website is activated. You can login now.");
        System.out.println("6");
        response.setSuccessful(true);

        return response;

    }

    private boolean sendNotificationOfAccountActivation(String to, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(to);
        message.setSubject(emailSubject);
        message.setText(text);
        try{
            System.out.println("saljem mail");
            emailSender.send(message);
            return true;
        }
        catch(Throwable e){
            e.printStackTrace();
            return false;
        }

    }

}
