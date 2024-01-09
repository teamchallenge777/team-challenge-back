package team.challenge.MobileStore.dto;

import team.challenge.MobileStore.model.RoleModel;

import java.util.Set;

public record UserTokenInfo(
        String token,
        String userId,
        String userEmail,
        Set<RoleModel> roles
) {
}
