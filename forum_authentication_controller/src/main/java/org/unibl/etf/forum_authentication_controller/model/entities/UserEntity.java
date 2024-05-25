package org.unibl.etf.forum_authentication_controller.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "user_group", nullable = true)
    private Integer userGroup;
    @Basic
    @Column(name = "username", nullable = true, length = 20)
    private String username;
    @Basic
    @Column(name = "email", nullable = true, length = 45)
    private String email;
    @Basic
    @Column(name = "password", nullable = true, length = 150)
    private String password;
    @Basic
    @Column(name = "registered", nullable = true)
    private Boolean registered;
    @Column(name = "activation_code", nullable = true)
    private String activationCode;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<AccessEntity> accesses;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments;


}
