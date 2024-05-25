package org.unibl.etf.forum.forum_web_server.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comment")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "approved", nullable = true)
    private Boolean approved;
    @Basic
    @Column(name = "posted_datetime", nullable = true)
    private Timestamp postedDatetime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private RoomEntity room;
    @Column(name = "content", nullable = true, length = 500)
    private String content;

}
