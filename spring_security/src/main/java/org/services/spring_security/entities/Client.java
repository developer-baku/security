package org.services.spring_security.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Entity
@Builder
@Setter
@Getter
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "secret")
    private String secret;

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private List<Authentication_methods> authenticationMethods;

    @OneToMany(mappedBy ="client",fetch = FetchType.EAGER)
    private List<Grand_Type> grand_types;

    @OneToMany(mappedBy ="client",fetch = FetchType.EAGER)
    private List<RedirectUrl> redirectUrls;

    @OneToMany(mappedBy ="client", fetch = FetchType.EAGER)
    private List<Scope> scope;


    @OneToOne(mappedBy ="client")
    private TokenSettings tokenSettings;

    public static RegisteredClient fromClient(Client client) {
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getSecret())
                .clientAuthenticationMethods(clientAuthenticationMethods(client.getAuthenticationMethods()))
                .authorizationGrantTypes(authorizationGrantTypes(client.getGrand_types()))
                .scopes(scopes(client.getScope()))
                .redirectUris(redirectUris(client.getRedirectUrls()))
                .tokenSettings(org.springframework.security.oauth2.server.authorization.settings.TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(client.tokenSettings.getAccessToken()))
                        .accessTokenFormat( new OAuth2TokenFormat(client.tokenSettings.getType()))
                        .build())
                .build();
    }

    private static Consumer<Set<AuthorizationGrantType>> authorizationGrantTypes(List<Grand_Type> grantTypes) {
        return s -> {
            for (Grand_Type g: grantTypes) {
                s.add(new AuthorizationGrantType(g.getGrandType()));
            }
        };
    }

    private static Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethods(
            List<Authentication_methods> authenticationMethods) {
        return m -> {
            for (Authentication_methods a : authenticationMethods) {
                m.add(new ClientAuthenticationMethod(a.getAuthenticationMethod()));
            }
        };
    }

    private static Consumer<Set<String>> scopes(List<Scope> scopes) {
        return s -> {
            for (Scope x : scopes) {
                s.add(x.getScope());
            }
        };
    }

    private static Consumer<Set<String>> redirectUris(List<RedirectUrl> uris) {
        return r -> {
            for (RedirectUrl u : uris) {
                r.add(u.getRedirectUrl());
            }
        };
    }

}
