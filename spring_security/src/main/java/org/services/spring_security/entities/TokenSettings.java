package org.services.spring_security.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token_settings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "accesstime")
    private Long accessToken;

    @OneToOne
    private Client client;
}
