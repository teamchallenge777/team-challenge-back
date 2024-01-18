package team.challenge.MobileStore.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.exception.AuthException;
import team.challenge.MobileStore.model.AuthProvider;
import team.challenge.MobileStore.model.RoleModel;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.repositories.RoleRepository;
import team.challenge.MobileStore.repositories.UserRepository;
import team.challenge.MobileStore.security.oauth.user.OAuth2UserInfo;
import team.challenge.MobileStore.security.oauth.user.OAuth2UserInfoFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processUser(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User){
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes());
        if (oAuth2UserInfo.getEmail().isEmpty()) throw new OAuth2AuthenticationException("Email not found OAuth2 provider!");
        Optional<UserModel> optionalUser = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        UserModel user;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))){
                throw new AuthException(String.format("Looks like you're singed up with %s account. Please use your %s account to login!", user.getProvider(), user.getProvider()));
            }
            return updateExistUser(user, oAuth2UserInfo);
        } else {
            return registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
    }


    private UserModel registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo){
        RoleModel roleUser = roleRepository.findByRoleName("USER").orElse(new RoleModel("USER"));
        roleUser = roleRepository.save(roleUser);
        UserModel user = UserModel.builder()
                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .email(oAuth2UserInfo.getEmail())
                .attributes(oAuth2UserInfo.getAttributes())
                .roles(Collections.singleton(roleUser))
                .isPhoneNumberConfirmed(false)
                .isPhoneNumberConfirmed(false)
                .creatingDate(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    private UserModel updateExistUser(UserModel user, OAuth2UserInfo oAuth2UserInfo){
        return userRepository.save(user);
    }
}
