package com.waltmann.waltmann_api.domain.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "techs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTech {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tech_id")
    private int tech_id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private int project_id;

    private Date updated_at;

    private Date created_at;
}
