package org.services.spring_security.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Entity
@Table(name = "grand_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Grand_Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grand_type")
    private String grandType;

    @ManyToOne
    private Client client;

    public static Grand_Type from(AuthorizationGrantType authorizationGrantType, Client c) {
        Grand_Type g = new Grand_Type();

        g.setGrandType(authorizationGrantType.getValue());
        g.setClient(c);

        return g;
    }

}
