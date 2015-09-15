package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by ������ on 17.07.2015.
 */
@Entity
@Table(name = "ACTIVITIES")
public class Activities {
    @Id
    @Column(name = "ACTIVITIES_TYPE_ID",nullable = false)
    @GeneratedValue
    Long activitiesTypeId;
    @Column(name = "ACTIVITIES_TYPE_NAME",nullable = false,length = 150)
    String activitiesTypeName;
    String key1c;
    @Version
    private long version;

    public Long getActivitiesTypeId() {
        return activitiesTypeId;
    }

    public void setActivitiesTypeId(Long activitiesTypeId) {
        this.activitiesTypeId = activitiesTypeId;
    }

    public String getActivitiesTypeName() {
        return activitiesTypeName;
    }

    public void setActivitiesTypeName(String activitiesTypeName) {
        this.activitiesTypeName = activitiesTypeName;
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
