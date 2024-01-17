package team.challenge.MobileStore.model;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel implements UserDetails, OAuth2User {
    @Id
    private String id;
    @Indexed(unique = true)
    @Email
    private String email;
    private String phoneNumber;
    private String password;
    @DocumentReference
    private Set<RoleModel> roles;
    private AuthProvider provider;
    private String customer;
    private Map<String, Object> attributes;
    private boolean isEmailConfirmed;
    private boolean isPhoneNumberConfirmed;
    private LocalDateTime creatingDate;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEmailConfirmed || isPhoneNumberConfirmed;
    }

    @Override
    public String getName() {
        return email;
    }
}
