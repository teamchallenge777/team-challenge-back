package team.challenge.MobileStore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.challenge.MobileStore.dto.LoginRequest;
import team.challenge.MobileStore.dto.SignUpRequest;
import team.challenge.MobileStore.dto.UserInfoRequest;
import team.challenge.MobileStore.exception.AuthException;
import team.challenge.MobileStore.exception.ModelAlreadyExistException;
import team.challenge.MobileStore.exception.ModelNotFoundException;
import team.challenge.MobileStore.model.AuthProvider;
import team.challenge.MobileStore.model.RoleModel;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.repositories.RoleRepository;
import team.challenge.MobileStore.repositories.UserRepository;
import team.challenge.MobileStore.service.UserService;
import team.challenge.MobileStore.util.StringUtil;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel getOneById(@NonNull String id) {
        return userRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(String.format("User with ID: %s not found!", id)));
    }

    @Override
    public UserModel getOneByEmail(@NonNull String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ModelNotFoundException(String.format("User with email: %s not found!", email)));
    }

    @Override
    public UserModel getOneByPhoneNumber(@NonNull String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ModelNotFoundException(String.format("User with phone number: %s not found!", phoneNumber)));
    }

    @Override
    public UserModel create(@NonNull SignUpRequest userRequest) {
        /*
        1. check if email is already exist
        2. find customer with present email or create new customer
        3. add customer to user model
        4. create new user and save in db
         */
        try{
            getOneByEmail(userRequest.email());
            throw new ModelAlreadyExistException("User with present email is already exist");
        } catch (ModelNotFoundException e){
            RoleModel roleUser = roleRepository.findByRoleName("USER").orElse(new RoleModel("USER"));
            roleUser = roleRepository.save(roleUser);
            String hash = passwordEncoder.encode(userRequest.password());
            UserModel newUser = UserModel.builder()
                    .phoneNumber(userRequest.phoneNumber())
                    .email(userRequest.email())
                    .password(hash)
                    .provider(AuthProvider.local)
                    .roles(Collections.singleton(roleUser))
                    .build();
            return userRepository.save(newUser);
        }
    }

    @Override
    public UserModel update(@NonNull UserInfoRequest userRequest) {
        return null;
    }

    @Override
    public void delete(@NonNull String id) {
        userRepository.delete(getOneById(id));
    }

    @Override
    public UserDetails getOneByEmailAndPassword(@NonNull LoginRequest loginRequest) {
        UserModel currentUser;
        if (StringUtil.isValidEmail(loginRequest.username())){
            currentUser = getOneByEmail(loginRequest.username());
        } else if (StringUtil.isValidPhoneNumber(loginRequest.username())){
            currentUser = getOneByPhoneNumber(loginRequest.username());
        } else {
            throw new AuthException("Invalid username!");
        }
        if (passwordEncoder.matches(loginRequest.password(), currentUser.getPassword())){
//        if (true){
            return loadUserByUsername(currentUser.getUsername());
        } else{
            throw new AuthException("Invalid password!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getOneByEmail(username);
    }
}
