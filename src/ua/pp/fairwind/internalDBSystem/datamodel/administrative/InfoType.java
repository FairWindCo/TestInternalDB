package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

/**
 * Created by ������ on 17.07.2015.
 */
@Entity
@Table(name = "INFOTYPES")
public class InfoType {
    @Id
    @GeneratedValue
    Long typeId;
    @Column(name = "INFOTYPENAME")
    String typeName;
    @Version
    long versionid;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="CATEGORY_ID", nullable=true)
    Category category;
    String key1c;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getVersionid() {
        return versionid;
    }

    public void setVersionid(long versionid) {
        this.versionid = versionid;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if(category!=null){
            if(this.category!=null) this.category.removeInfoTypes(this);
            category.addInfoTypes(this);
            this.category = category;
        }
    }

    void setCategoryInt(Category category) {
        if(category!=null){
            this.category = category;
        }
    }

    public String getKey1c() {
        return key1c;
    }

    public void setKey1c(String key1c) {
        this.key1c = key1c;
    }
}
