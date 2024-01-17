package team.challenge.MobileStore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.challenge.MobileStore.dto.LoginRequest;
import team.challenge.MobileStore.dto.SignUpRequest;
import team.challenge.MobileStore.dto.UserTokenInfo;
import team.challenge.MobileStore.mapper.RoleMapper;
import team.challenge.MobileStore.model.UserModel;
import team.challenge.MobileStore.security.TokenProvider;
import team.challenge.MobileStore.service.UserService;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final RoleMapper roleMapper;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest login){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.email(),
                        login.password()
                )
        );
        UserModel user = userService.getOneByEmailAndPassword(login);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new UserTokenInfo(token, user.getId(), user.getEmail(), roleMapper.mapToDtoSet(user.getRoles())));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> create(@Valid @RequestBody SignUpRequest signUpRequest){
        UserModel user = userService.create(signUpRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body("User register successfully");
    }

}
