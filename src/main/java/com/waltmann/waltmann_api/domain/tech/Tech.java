package com.waltmann.waltmann_api.domain.tech;
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
public class Tech {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToOne
    @JoinColumn(name="file_id")
    private int file_id;

    @OneToOne
    @JoinColumn(name = "link_id")
    private int link_id;

    private Date updated_at;

    private Date created_at;
}
