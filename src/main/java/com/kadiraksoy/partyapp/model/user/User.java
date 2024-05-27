package com.kadiraksoy.partyapp.model.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kadiraksoy.partyapp.model.party.Party;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthdayDate;
    private boolean active;
    private String otp;
    private LocalDateTime otpGeneratedTime;

    @OneToMany(mappedBy = "admin")
    private List<Party> parties;
    @OneToMany(mappedBy = "admin")
    private List<Party> adminParties;

    @ManyToMany(mappedBy = "participants")
    private List<Party> participantParties;

//    @ManyToMany(mappedBy = "participants")
//    private List<Party> joinedParties;
//
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments;


    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // our "username" for security is the email field
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
