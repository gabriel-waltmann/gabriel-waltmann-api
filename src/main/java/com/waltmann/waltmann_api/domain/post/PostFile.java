package com.waltmann.waltmann_api.domain.post;


import com.waltmann.waltmann_api.domain.file.File;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "techs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostFile {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Column(name = "updated_at") @UpdateTimestamp
    private Date updated_at;

    @Column(name = "created_at") @CreationTimestamp
    private Date created_at;
}