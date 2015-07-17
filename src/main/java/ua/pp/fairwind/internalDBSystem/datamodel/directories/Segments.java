package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "SEGMENTS")
public class Segments {
    @Id
    @GeneratedValue
    private Long sergmentsId;
    private String name;
    @Version
    private long version;

    public Long getSergmentsId() {
        return sergmentsId;
    }

    public void setSergmentsId(Long sergmentsId) {
        this.sergmentsId = sergmentsId;
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
