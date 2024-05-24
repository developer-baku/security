package org.services.spring_security.service;

import org.services.spring_security.entities.*;
import org.services.spring_security.repository.RepositoryC;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.stereotype.Service;

import org.services.spring_security.*;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional(readOnly = true)
//@Service
public class ClientService implements RegisteredClientRepository{
    private final RepositoryC clientRepository;

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        Client c = new Client();

        c.setClientId(registeredClient.getClientId());
        c.setSecret(registeredClient.getClientSecret());
        c.setAuthenticationMethods(
                registeredClient.getClientAuthenticationMethods()
                        .stream().map(a -> Authentication_methods.from(a, c))
                        .collect(Collectors.toList())
        );
        c.setGrand_types(
                registeredClient.getAuthorizationGrantTypes()
                        .stream().map(g -> Grand_Type.from(g, c))
                        .collect(Collectors.toList())
        );
        c.setRedirectUrls(
                registeredClient.getRedirectUris()
                        .stream().map(u -> RedirectUrl.from(u, c))
                        .collect(Collectors.toList())
        );
        c.setScope(
                registeredClient.getScopes()
                        .stream().map(s -> Scope.from(s, c))
                        .collect(Collectors.toList())
        );

        clientRepository.save(c);
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<Client> client = clientRepository.findById(Long.valueOf(id));
        if (client.isPresent()){
            return   Client.fromClient(client.get());
        }
        else throw new IllegalStateException();


    }




    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<Client> client = clientRepository.findByClientId(clientId);
        if (client.isPresent()){
            return   Client.fromClient(client.get());
        }
        else throw new IllegalStateException();


    }
}
