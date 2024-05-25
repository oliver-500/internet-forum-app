package org.unibl.etf.forum.forum_web_server.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "permission")
public class PermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @OneToMany(mappedBy = "permission")
    private List<AccessEntity> accesses;


}
