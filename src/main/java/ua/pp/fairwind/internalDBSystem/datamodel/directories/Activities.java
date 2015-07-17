package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
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
}
