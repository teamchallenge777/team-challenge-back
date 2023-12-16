package team.challenge.MobileStore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RoleModel  {
    @Id
    private String id;
    @Indexed(unique = true)
    private String roleName;

    public RoleModel(String roleName) {
        this.roleName = roleName;
    }
}
