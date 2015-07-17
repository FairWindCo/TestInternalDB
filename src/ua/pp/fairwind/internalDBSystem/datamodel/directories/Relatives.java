package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "RELATIVES")
public class Relatives {
    @Id
    @GeneratedValue
    private Long relativiesId;
    private String name;
    @Version
    private long version;

    public Long getRelativiesId() {
        return relativiesId;
    }

    public void setRelativiesId(Long relativiesId) {
        this.relativiesId = relativiesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
