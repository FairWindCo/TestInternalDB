package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.HashSet;

/**
 * Created by ������ on 17.07.2015.
 */
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue
    private Long userID;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "FIO")
    private String FIO;
    @Column(name = "PASSWORD")
    private String passwordHash;
    @Column(name = "ENABLED")
    private boolean enabled;
    @OneToMany(fetch = FetchType.EAGER)
    private HashSet<Roles> userRoles;
    @OneToMany(fetch = FetchType.EAGER)
    private HashSet<Subdivision> grantedSubdivisions;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public HashSet<Roles> getUserRoles() {
        return userRoles;
    }

    public void addUserRoles(Roles userRoles) {
        this.userRoles.add(userRoles);
    }

    public void removeUserRoles(Roles userRoles) {
        this.userRoles.remove(userRoles);
    }

    public void removeAllUserRoles() {
        this.userRoles.clear();
    }
}
