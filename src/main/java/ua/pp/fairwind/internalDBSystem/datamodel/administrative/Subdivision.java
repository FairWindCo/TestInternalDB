package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "SUBDIVISIONS")
public class Subdivision {
    @Id
    @GeneratedValue
    Long subdivisionId;
    String name;
    @OneToMany
    Set<Category> categories=new HashSet<>();
    long versionid;

    public Long getSubdivisionId() {
        return subdivisionId;
    }

    public String getName() {
        return name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public long getVersionid() {
        return versionid;
    }

    public void setSubdivisionId(Long subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersionid(long versionid) {
        this.versionid = versionid;
    }
}
