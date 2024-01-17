package team.challenge.MobileStore.mapper;

import team.challenge.MobileStore.dto.RoleDto;
import team.challenge.MobileStore.model.RoleModel;

import java.util.Set;

public interface RoleMapper {
    RoleDto mapToDto(RoleModel roleModel);
    Set<RoleDto> mapToDtoSet(Set<RoleModel> roleModelSet);
}
