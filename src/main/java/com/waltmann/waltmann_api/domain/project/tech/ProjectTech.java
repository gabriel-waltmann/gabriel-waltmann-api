package com.waltmann.waltmann_api.domain.project.tech;

import com.waltmann.waltmann_api.domain.project.Project;
import com.waltmann.waltmann_api.domain.tech.Tech;
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
@Table(name = "project_techs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTech {
  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "tech_id")
  private Tech tech;

  @Column(name = "updated_at") @UpdateTimestamp
  private Date updated_at;
  @Column(name = "created_at") @CreationTimestamp
  private Date created_at;
}
