package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by ������ on 17.07.2015.
 */
@Entity
@Table(name = "SEGMENTS")
public class Segments {
    @Id
    @GeneratedValue
    private Long sergmentsId;
    private String name;
    String key1c;
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
    public String getKey1c() {
        return key1c;
    }

    public void setKey1c(String key1c) {
        this.key1c = key1c;
    }
}
