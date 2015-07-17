package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "ROLES")
public class Roles {
    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue
    Long roleId;
    @Column(name = "ROLE_NAME")
    String roleName;
    @Column(name = "ROLE_DESCRIPTION")
    String roleDescription;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
