package com.kadiraksoy.partyapp.model.party;


import com.kadiraksoy.partyapp.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date eventDate;
    private int participantLimit;
    @CreationTimestamp
    private Date createdTime;
    @UpdateTimestamp
    private Date updatedTime;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;
    @ManyToMany
    @JoinTable(
            name = "party_participants",
            joinColumns = @JoinColumn(name = "party_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;
    private Long imageId;
//    private boolean active;
//    @OneToMany(mappedBy = "party")
//    private List<Comment> comments;

}
