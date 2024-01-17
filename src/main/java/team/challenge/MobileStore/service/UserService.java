package team.challenge.MobileStore.service;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import team.challenge.MobileStore.dto.LoginRequest;
import team.challenge.MobileStore.dto.SignUpRequest;
import team.challenge.MobileStore.dto.UserInfoRequest;
import team.challenge.MobileStore.model.UserModel;

import java.util.List;

public interface UserService  extends UserDetailsService {
    List<?> getAll();
    UserModel getOneById(@NonNull final String id);
    UserModel getOneByEmail(@NonNull final String email);
    UserModel getOneByPhoneNumber(@NonNull final String phoneNumber);
    UserModel create(@NonNull final SignUpRequest userRequest);
    UserModel update(@NonNull final UserInfoRequest userRequest);
    void delete(@NonNull final String id);
    UserModel getOneByEmailAndPassword(@NonNull final LoginRequest loginRequest);

}
