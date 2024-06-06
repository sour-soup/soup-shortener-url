package org.example.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "links")
public class LinkEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false, length = 300)
    private String url;
    @Column(name = "visit_count", nullable = false)
    private Long visitCount = 0L;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
