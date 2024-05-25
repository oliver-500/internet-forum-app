package org.unibl.etf.forum_authentication_controller.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "access")
public class AccessEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false)
    private PermissionEntity permission;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private RoomEntity room;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;


}
