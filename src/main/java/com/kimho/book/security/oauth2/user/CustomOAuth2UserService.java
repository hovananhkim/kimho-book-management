package com.kimho.book.security.oauth2.user;

import com.kimho.book.model.entity.AuthProvider;
import com.kimho.book.model.entity.User;
import com.kimho.book.repository.RoleRepository;
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

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2User);
        return processOAuthPostLogin(customOAuth2User);
    }

    private OAuth2User processOAuthPostLogin(CustomOAuth2User customOAuth2User) {
        User user = new User();
        if (userRepository.existsByEmail(customOAuth2User.getEmail())) {
            user = userRepository.findByEmail(customOAuth2User.getEmail());
            user.setFirstName(customOAuth2User.getName());
            user.setAvatar(customOAuth2User.getImageUrl());
        } else {
            user.setAvatar(customOAuth2User.getImageUrl());
            user.setProviderId(customOAuth2User.getId());
            user.setEmail(customOAuth2User.getEmail());
            user.setFirstName(customOAuth2User.getName());
            user.setAvatar(customOAuth2User.getImageUrl());
            user.setProvider(AuthProvider.google);
            user.setRole(roleRepository.findByName("ROLE_USER"));
            user.setEnabled(true);
        }
        return UserPrincipal.create(userRepository.save(user), customOAuth2User.getAttributes());
    }
}
