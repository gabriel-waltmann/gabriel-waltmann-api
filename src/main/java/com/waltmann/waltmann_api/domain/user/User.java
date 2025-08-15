package com.waltmann.waltmann_api.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waltmann.waltmann_api.domain.file.File;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "title")
    private String title;

    @Column(name = "about")
    private String about;

    @Column(name = "profile_file_id")
    private UUID profile_file_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_file_id")
    private File profileFile;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "updated_at") @UpdateTimestamp
    private Date updated_at;

    @Column(name = "created_at") @CreationTimestamp
    private Date created_at;

}
