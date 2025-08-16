package com.waltmann.waltmann_api.domain.user.tech;

import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.domain.user.User;
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
@Table(name = "user_techs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTech {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tech_id")
    private Tech project;

    @Column(name = "updated_at") @UpdateTimestamp
    private Date updated_at ;

    @Column(name = "created_at") @CreationTimestamp
    private Date created_at;
}
