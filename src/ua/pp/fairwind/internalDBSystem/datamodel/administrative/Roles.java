package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "SYSROLES")
public class Roles {
    @Id
    @Column(name = "ROLEID")
    @GeneratedValue
    Long roleId;
    @Column(name = "ROLENAME",nullable = false)
    String roleName;
    @Column(name = "ROLEDESCRIPTION")
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
