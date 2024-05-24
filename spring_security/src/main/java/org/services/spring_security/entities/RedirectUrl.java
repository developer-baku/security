package org.services.spring_security.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "redirect_urls")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedirectUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String redirectUrl;

    @ManyToOne
    private Client client;
    public static RedirectUrl from(String url, Client c) {
        RedirectUrl redirectUrl = new RedirectUrl();

        redirectUrl.setRedirectUrl(url);
        redirectUrl.setClient(c);

        return redirectUrl;
    }

}
