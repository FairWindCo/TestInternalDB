package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ������ on 17.07.2015.
 */
@Entity
@Table(name = "SUBDIVISIONS")
public class Subdivision {
    @Id
    @GeneratedValue
    Long subdivisionId;
    @Column(nullable = false)
    String name;
    @ManyToMany(mappedBy = "subdivision",cascade = {CascadeType.PERSIST,CascadeType.REFRESH}/*,fetch = FetchType.EAGER*/)
    Set<Category> categories=new HashSet<>();
    @Version
    long versionid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(Long subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public long getVersionid() {
        return versionid;
    }

    public void setVersionid(long versionid) {
        this.versionid = versionid;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        if(category!=null) {
            this.categories.add(category);
            category.addSubdivisionInt(this);
        }
    }

    public void removeCategory(Category category) {
        if(category!=null) {
            if(this.categories.remove(category)) {
                category.removeSubdivisionInt(this);
            }
        }
    }

    void addCategoryInt(Category category) {
        if(category!=null) {
            this.categories.add(category);
        }
    }

     void removeCategoryInt(Category category) {
        if(category!=null) {
            this.categories.remove(category);
        }
    }

    public void removeAllCategories() {
        categories.forEach(cat->cat.removeSubdivision(this));
        this.categories.clear();
    }
}
