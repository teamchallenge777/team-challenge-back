package team.challenge.MobileStore.db.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import team.challenge.MobileStore.model.RoleModel;
import team.challenge.MobileStore.repositories.RoleRepository;

import java.util.List;

@ChangeLog(order = "002")
public class RoleMigration {
    @ChangeSet(order = "001", id = "create_roles", author = "Romych")
    public void createRoles(RoleRepository roleRepository){
        List<RoleModel> roles = List.of(
                new RoleModel("USER"),
                new RoleModel("ADMIN")
        );
        roleRepository.saveAll(roles);
    }
}
