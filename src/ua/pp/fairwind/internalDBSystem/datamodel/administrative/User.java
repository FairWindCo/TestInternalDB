package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "USERNAME",nullable = false)
    private String userName;
    @Column(name = "FIO")
    private String fio;
    @Column(name = "PASSWORD")
    private String passwordHash;
    @Column(name = "ENABLED")
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Roles> userRoles=new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Subdivision> grantedSubdivisions=new HashSet<>();
    @ManyToOne
    @JoinColumn(name="SUBDIV_ID", nullable=true)
    private Subdivision mainsubdivisions;
    @Version
    private long versionId;
    @Transient
    private Long mainsubdivisions_id;

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

    public String getFio() {
        return fio;
    }

    public void setFio(String FIO) {
        this.fio = FIO;
    }

    public Set<Roles> getUserRoles() {
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

    public Subdivision getMainsubdivisions() {
        return mainsubdivisions;
    }

    public void setMainsubdivisions(Subdivision mainsubdivisions) {
        this.mainsubdivisions = mainsubdivisions;
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public Set<Subdivision> getGrantedSubdivisions() {
        return grantedSubdivisions;
    }

    public void addGrantedSubdivisions(Subdivision grantedSubdivisions) {
        this.grantedSubdivisions.add(grantedSubdivisions);
    }

    public void removeGrantedSubdivisions(Subdivision grantedSubdivisions) {
        this.grantedSubdivisions.remove(grantedSubdivisions);
    }

    public Long getMainsubdivisions_id() {
        if(mainsubdivisions_id!=null) return mainsubdivisions_id;
        else return mainsubdivisions==null?null:mainsubdivisions.subdivisionId;
    }

    public void setMainsubdivisions_id(Long mainsubdivisions_id) {
        this.mainsubdivisions_id = mainsubdivisions_id;
    }
}
