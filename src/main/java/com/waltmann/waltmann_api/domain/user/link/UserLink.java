package com.waltmann.waltmann_api.domain.user.link;

import com.waltmann.waltmann_api.domain.link.Link;
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
@Table(name = "user_links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLink {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private Link link;

    @Column(name = "updated_at") @UpdateTimestamp
    private Date updated_at ;

    @Column(name = "created_at") @CreationTimestamp
    private Date created_at;
}
