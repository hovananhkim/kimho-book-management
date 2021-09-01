package com.kimho.book.security.oauth2.user;

import com.kimho.book.model.entity.User;
import com.kimho.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2User);
        processOAuthPostLogin(customOAuth2User.getEmail());
        return customOAuth2User;
    }

    private void processOAuthPostLogin(String email) {
        if (!userRepository.existsByEmail(email)) {
            User newUser = new User();
            newUser.setEmail(email);
//            newUser.setProvider(AuthProvider.google);
            newUser.setEnabled(true);
            userRepository.save(newUser);
        }

    }
}
