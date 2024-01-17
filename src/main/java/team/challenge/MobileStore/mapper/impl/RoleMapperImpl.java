package team.challenge.MobileStore.mapper.impl;

import org.springframework.stereotype.Component;
import team.challenge.MobileStore.dto.RoleDto;
import team.challenge.MobileStore.mapper.RoleMapper;
import team.challenge.MobileStore.model.RoleModel;

import java.util.Set;
import java.util.stream.Collectors;
@Component
public class RoleMapperImpl implements RoleMapper {
    @Override
    public RoleDto mapToDto(RoleModel roleModel) {
        return new RoleDto(roleModel.getAuthority());
    }

    @Override
    public Set<RoleDto> mapToDtoSet(Set<RoleModel> roleModelSet) {
        return roleModelSet.stream().map(this::mapToDto).collect(Collectors.toSet());
    }
}
