package org.services.spring_security.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    @ManyToOne
    private User user;
}
