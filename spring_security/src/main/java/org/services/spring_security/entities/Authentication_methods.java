package org.services.spring_security.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Entity
@Table(name = "authentication_methods")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authentication_methods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authentication_method")
    private String authenticationMethod;
    @ManyToOne
    private Client client;

    public static Authentication_methods from(ClientAuthenticationMethod authenticationMethod, Client client) {
        Authentication_methods a = new Authentication_methods();
        a.setAuthenticationMethod(authenticationMethod.getValue());
        a.setClient(client);
        return a;
    }

}
