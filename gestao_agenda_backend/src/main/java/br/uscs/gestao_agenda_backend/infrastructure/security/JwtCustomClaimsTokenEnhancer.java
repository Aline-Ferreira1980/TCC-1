package br.uscs.gestao_agenda_backend.infrastructure.security;

import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (authentication.getPrincipal() instanceof AuthUser authUser) {

            Map<String, Object> info = new HashMap<String, Object>();
            info.put("user_nome", authUser.getNome());
            info.put("user_id", authUser.getId());


            DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            oAuth2AccessToken.setAdditionalInformation(info);
        }

        return accessToken;
    }
}
