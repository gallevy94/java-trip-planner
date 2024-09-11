package com.handson.trip_planner.security.oauth2;

import com.handson.trip_planner.jwt.DBUserService;
import com.handson.trip_planner.jwt.DBUser;
import com.handson.trip_planner.security.UserPrincipal;
import com.handson.trip_planner.security.oauth2.user.OAuth2UserInfo;
import com.handson.trip_planner.security.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private DBUserService userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws Exception {
        System.out.println("***************** processOAuth2User *****************\n" + oAuth2UserRequest + "\n" + oAuth2User);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new Exception("Email not found from OAuth2 provider");
        }

        Optional<DBUser> userOptional = userRepository.findUserName(oAuth2UserInfo.getEmail());
        DBUser user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }


        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private DBUser registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        DBUser user = new DBUser();

        user.setName(oAuth2UserInfo.getEmail());
        user.setGoogleId(oAuth2UserInfo.getId());
        user.setPassword("1111");
        return userRepository.save(user);
    }

    private DBUser updateExistingUser(DBUser existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getEmail());
        existingUser.setGoogleId(oAuth2UserInfo.getId());
        return userRepository.save(existingUser);
    }

}