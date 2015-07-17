package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.HashSet;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID",columnDefinition = "category id")
    Long categoryId;
    @Column(name = "NAME",columnDefinition = "file record id")
    String name;
    @ManyToMany
    HashSet<Subdivision> subdivision =new HashSet<>();
    @OneToMany
    HashSet<InfoType> infoTypes=new HashSet<>();
    @Version
    long versionid;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Subdivision> getSubdivision() {
        return subdivision;
    }

    public void addSubdivision(Subdivision subdivision) {
        this.subdivision.add(subdivision);
    }

    public void removeSubdivision(Subdivision subdivision) {
        this.subdivision.remove(subdivision);
    }

    public HashSet<InfoType> getInfoTypes() {
        return infoTypes;
    }

    public void addInfoTypes(InfoType infoTypes) {
        this.infoTypes.add(infoTypes);
    }

    public void removeInfoTypes(InfoType infoTypes) {
        this.infoTypes.remove(infoTypes);
    }

    public long getVersionid() {
        return versionid;
    }

    public void setVersionid(long versionid) {
        this.versionid = versionid;
    }
}
