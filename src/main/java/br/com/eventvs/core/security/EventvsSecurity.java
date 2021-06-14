package br.com.eventvs.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class EventvsSecurity {

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getPessoaEmail(){
        //Jwt jwt = (Jwt) getAuthentication().getDetails();
        //Object a = getAuthentication();
       // System.out.println(getAuthentication().getPrincipal());

        return (String) getAuthentication().getPrincipal();
        //return jwt.getClaim("pessoa_id");
    }

}
