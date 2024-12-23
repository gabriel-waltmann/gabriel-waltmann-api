package com.waltmann.waltmann_api.domain.tech;
import com.waltmann.waltmann_api.domain.file.File;
import com.waltmann.waltmann_api.domain.link.Link;
import com.waltmann.waltmann_api.domain.project.Project;
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
@Table(name = "techs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tech {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "link_id")
    private Link link;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

    @Column(name = "updated_at") @UpdateTimestamp
    private Date updated_at;

    @Column(name = "created_at") @CreationTimestamp
    private Date created_at;
}
