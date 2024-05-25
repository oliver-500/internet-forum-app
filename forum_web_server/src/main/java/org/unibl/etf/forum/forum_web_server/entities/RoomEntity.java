package org.unibl.etf.forum.forum_web_server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "room")
public class RoomEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 45)
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<AccessEntity> accesses;
    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private List<CommentEntity> comments;


}
