package org.services.spring_security.service;

import org.services.spring_security.entities.*;
import org.services.spring_security.repository.RepositoryC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RCRService implements RegisteredClientRepository {

    @Autowired
    private RepositoryC repositoryC;
    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        System.out.println("save is called");
        Client c = new Client();
        c.setAuthenticationMethods(registeredClient.getClientAuthenticationMethods()
                .stream().map(f->

                        Authentication_methods.builder()
                                .client(c)
                                .authenticationMethod(f.getValue())
                                .build())
                .collect(Collectors.toList()));

        c.setScope(registeredClient.getScopes().stream().map(f-> Scope.builder()
                .client(c)
                .scope(f)
                .build()).toList());

        c.setClientId(registeredClient.getClientId());

        c.setGrand_types(registeredClient.getAuthorizationGrantTypes().stream().map(f-> Grand_Type.builder()
                        .client(c)
                        .grandType(f.getValue())
                        .build())
                .toList());

        c.setRedirectUrls(registeredClient.getRedirectUris().stream().map(f-> RedirectUrl.builder()
                .redirectUrl(f)
                .client(c)
                .build()).toList());

        String oAuth2TokenFormat;
        if (registeredClient.getTokenSettings().getAccessTokenFormat().equals(OAuth2TokenFormat.REFERENCE)) oAuth2TokenFormat = "REFERENCE";
        else  oAuth2TokenFormat = "SELF_CONTAINED";
        Duration d = registeredClient.getTokenSettings().getAccessTokenTimeToLive();

        Long time=d.toHours();


        org.services.spring_security.entities.TokenSettings tokenSettings =
                org.services.spring_security.entities.TokenSettings.builder()
                        .accessToken(time)
                        .type(oAuth2TokenFormat)
                        .client(c)
                        .build();

        c.setTokenSettings(tokenSettings);

        c.setSecret(registeredClient.getClientSecret());

        repositoryC.save(c);
    }

    @Override
    public RegisteredClient findById(String id) {
        System.out.println("findbyid is called");

        Optional<Client> optionalClient = repositoryC.findById(Long.valueOf(id));
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Set<AuthorizationGrantType> cl = client.getGrand_types().stream().map(f-> {
                String s = f.getGrandType();
                return switch (s) {
                    case "authorization_code" -> AuthorizationGrantType.AUTHORIZATION_CODE;
                    case "refresh_token" -> AuthorizationGrantType.REFRESH_TOKEN;
                    case "client_credentials" -> AuthorizationGrantType.CLIENT_CREDENTIALS;
                    default -> throw new IllegalArgumentException("Unknown authorization");
                };
            }).collect(Collectors.toSet());
            Set<String> strings
                    = client.getRedirectUrls().stream()
                    .map(RedirectUrl::getRedirectUrl)
                    .collect(Collectors.toSet());
            Set<String> scopes
                    = client.getScope().stream()
                    .map(Scope::getScope)
                    .collect(Collectors.toSet());

            Set<ClientAuthenticationMethod> methods
                    = client.getAuthenticationMethods().stream()
                    .map(f->{
                            String a = f.getAuthenticationMethod();
                        return switch (a) {
                            case "client_secret_basic" -> ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
                            case "client_secret_jwt" -> ClientAuthenticationMethod.CLIENT_SECRET_JWT;
                            case "client_secret_post" -> ClientAuthenticationMethod.CLIENT_SECRET_POST;
                            case "private_key_jwt" -> ClientAuthenticationMethod.PRIVATE_KEY_JWT;
                            case "none" -> ClientAuthenticationMethod.NONE;
                            default -> throw new IllegalArgumentException("Unknown authorization_method");
                        };
            })
                    .collect(Collectors.toSet());
            OAuth2TokenFormat oAuth2TokenFormat ;
            if (client.getTokenSettings().getType().equals("REFERENCE")) oAuth2TokenFormat = OAuth2TokenFormat.REFERENCE;
            else oAuth2TokenFormat = OAuth2TokenFormat.SELF_CONTAINED;

            TokenSettings tokenSettings = TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofHours(client.getTokenSettings().getAccessToken()))
                    .accessTokenFormat(
                            oAuth2TokenFormat
                    )
                    .build();
            return RegisteredClient.withId(id)
                    .clientSecret(client.getSecret())
                    .id(id)
                    .tokenSettings(tokenSettings)
                    .clientId(client.getClientId())
                    .authorizationGrantTypes(c->
                             c.addAll(cl)
                    )
                    .redirectUris(redirectUris->redirectUris.addAll(strings))
                    .scopes(c->c.addAll(scopes))
                    .clientAuthenticationMethods(c->c.addAll(methods))
                    .build();
        }
        else throw  new RuntimeException();


    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        System.out.println("clientid is called");

        var clientOptional = repositoryC.findByClientId(clientId);
        if (clientOptional.isPresent()){
            var client = clientOptional.get();
            Set<AuthorizationGrantType> cl = client.getGrand_types().stream().map(f-> {
                String s = f.getGrandType();
                return switch (s) {
                    case "authorization_code" -> AuthorizationGrantType.AUTHORIZATION_CODE;
                    case "refresh_token" -> AuthorizationGrantType.REFRESH_TOKEN;
                    case "client_credentials" -> AuthorizationGrantType.CLIENT_CREDENTIALS;
                    default -> throw new IllegalArgumentException("Unknown authorization");
                };
            }).collect(Collectors.toSet());
            Set<String> strings
                    = client.getRedirectUrls().stream()
                    .map(RedirectUrl::getRedirectUrl)
                    .collect(Collectors.toSet());
            Set<String> scopes
                    = client.getScope().stream()
                    .map(Scope::getScope)
                    .collect(Collectors.toSet());

            Set<ClientAuthenticationMethod> methods
                    = client.getAuthenticationMethods().stream()
                    .map(f->{
                        String a = f.getAuthenticationMethod();
                        return switch (a) {
                            case "client_secret_basic" -> ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
                            case "client_secret_jwt" -> ClientAuthenticationMethod.CLIENT_SECRET_JWT;
                            case "client_secret_post" -> ClientAuthenticationMethod.CLIENT_SECRET_POST;
                            case "private_key_jwt" -> ClientAuthenticationMethod.PRIVATE_KEY_JWT;
                            case "none" -> ClientAuthenticationMethod.NONE;
                            default -> throw new IllegalArgumentException("Unknown authorization_method");
                        };
                    })
                    .collect(Collectors.toSet());
            OAuth2TokenFormat oAuth2TokenFormat ;
            if (client.getTokenSettings().getType().equals("REFERENCE")) oAuth2TokenFormat = OAuth2TokenFormat.REFERENCE;
            else oAuth2TokenFormat = OAuth2TokenFormat.SELF_CONTAINED;
            System.out.println(oAuth2TokenFormat.getValue());
            TokenSettings tokenSettings = TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofHours(client.getTokenSettings().getAccessToken()))
                    .accessTokenFormat(
                            oAuth2TokenFormat
                    )
                    .build();

            return RegisteredClient.withId(String.valueOf(client.getId()))
                    .tokenSettings(tokenSettings)
                    .clientSecret(client.getSecret())
                    .id(String.valueOf(client.getId()))
                    .tokenSettings(tokenSettings)
                    .clientId(client.getClientId())
                    .authorizationGrantTypes(c->
                            c.addAll(cl)
                    )
                    .redirectUris(redirectUris->redirectUris.addAll(strings))
                    .scopes(c->c.addAll(scopes))
                    .clientAuthenticationMethods(c->c.addAll(methods))
                    .build();

        }
        else throw new RuntimeException();

    }
}
