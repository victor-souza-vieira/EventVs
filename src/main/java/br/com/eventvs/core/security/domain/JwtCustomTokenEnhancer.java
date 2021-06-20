package br.com.eventvs.core.security.domain;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;


public class JwtCustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
        if(oAuth2Authentication.getPrincipal() instanceof AuthPessoa) {

            var authPessoa = (AuthPessoa) oAuth2Authentication.getPrincipal();

            var info = new HashMap<String, Object>();
            info.put("nome", authPessoa.getNome());
            info.put("pessoa_id", authPessoa.getId());
            info.put("role", authPessoa.getRole());

            var oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            oAuth2AccessToken.setAdditionalInformation(info);
        }
        return accessToken;
    }
}
