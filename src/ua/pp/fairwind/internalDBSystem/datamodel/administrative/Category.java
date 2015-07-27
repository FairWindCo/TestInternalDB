package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    Long categoryId;
    @Column(name = "CATEGORY_NAME")
    String name;
    @ManyToMany
    Set<Subdivision> subdivision =new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    Set<InfoType> infoTypes=new HashSet<>();
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

    public Set<Subdivision> getSubdivision() {
        return subdivision;
    }

    public void addSubdivision(Subdivision subdivision) {
        if(subdivision!=null) {
            this.subdivision.add(subdivision);
            subdivision.addCategoryInt(this);
        }
    }

    public void removeSubdivision(Subdivision subdivision) {
        if(subdivision!=null){
            if(this.subdivision.remove(subdivision)) {
                subdivision.removeCategoryInt(this);
            }
        }
    }

    void addSubdivisionInt(Subdivision subdivision) {
        if(subdivision!=null) {
            this.subdivision.add(subdivision);
        }
    }

    void removeSubdivisionInt(Subdivision subdivision) {
        if(subdivision!=null){
            this.subdivision.remove(subdivision);
        }
    }

    public Set<InfoType> getInfoTypes() {
        return infoTypes;
    }

    public void addInfoTypes(InfoType infoTypes) {
        if(infoTypes!=null) {
            this.infoTypes.add(infoTypes);
            infoTypes.setCategoryInt(this);
        }
    }

    public void removeInfoTypes(InfoType infoTypes) {
        if(infoTypes!=null) {
            if(this.infoTypes.remove(infoTypes)) infoTypes.setCategoryInt(null);
        }
    }

    void addInfoTypesInt(InfoType infoTypes) {
        if(infoTypes!=null) this.infoTypes.add(infoTypes);
    }

    void removeInfoTypesInt(InfoType infoTypes) {

        if(infoTypes!=null) this.infoTypes.remove(infoTypes);
    }

    public long getVersionid() {
        return versionid;
    }

    public void setVersionid(long versionid) {
        this.versionid = versionid;
    }
}
