package org.services.spring_security.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scope")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String scope;
    @ManyToOne
    private Client client;
    public static Scope from(String scope, Client client) {
        Scope s = new Scope();

        s.setScope(scope);
        s.setClient(client);

        return s;
    }

}
