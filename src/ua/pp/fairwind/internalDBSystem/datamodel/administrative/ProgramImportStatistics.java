package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Сергей on 16.09.2015.
 */
@Entity
@Table(name = "STATISTICS_JOURNAL")
public class ProgramImportStatistics {
    @Id
    @GeneratedValue
    @Column(name = "STATISTIC_ID")
    Long id;
    private Date importDateTime=new Date();
    private String importerUserName;
    private long personImportCounter;
    private long dosserImportCounter;
    private long subdivisionsImportCounter;
    private long categoryImportCounter;
    private long infotypeImportCounter;
    private long activitiesImportCounter;
    private long contacttypesImportCounter;
    private long hobbiImportCounter;
    private long relativesImportCounter;
    private long segmentsImportCounter;
    private long filetypesImportCounter;


    public Date getImportDateTime() {
        return importDateTime;
    }

    public void setImportDateTime(Date importDateTime) {
        this.importDateTime = importDateTime;
    }

    public String getImporterUserName() {
        return importerUserName;
    }

    public void setImporterUserName(String importerUserName) {
        this.importerUserName = importerUserName;
    }

    public long getPersonImportCounter() {
        return personImportCounter;
    }

    public void setPersonImportCounter(long personImportCounter) {
        this.personImportCounter = personImportCounter;
    }

    public long getDosserImportCounter() {
        return dosserImportCounter;
    }

    public void setDosserImportCounter(long dosserImportCounter) {
        this.dosserImportCounter = dosserImportCounter;
    }

    public long getSubdivisionsImportCounter() {
        return subdivisionsImportCounter;
    }

    public void setSubdivisionsImportCounter(long subdivisionsImportCounter) {
        this.subdivisionsImportCounter = subdivisionsImportCounter;
    }

    public long getCategoryImportCounter() {
        return categoryImportCounter;
    }

    public void setCategoryImportCounter(long categoryImportCounter) {
        this.categoryImportCounter = categoryImportCounter;
    }

    public long getInfotypeImportCounter() {
        return infotypeImportCounter;
    }

    public void setInfotypeImportCounter(long infotypeImportCounter) {
        this.infotypeImportCounter = infotypeImportCounter;
    }

    public long getActivitiesImportCounter() {
        return activitiesImportCounter;
    }

    public void setActivitiesImportCounter(long activitiesImportCounter) {
        this.activitiesImportCounter = activitiesImportCounter;
    }

    public long getContacttypesImportCounter() {
        return contacttypesImportCounter;
    }

    public void setContacttypesImportCounter(long contacttypesImportCounter) {
        this.contacttypesImportCounter = contacttypesImportCounter;
    }

    public long getHobbiImportCounter() {
        return hobbiImportCounter;
    }

    public void setHobbiImportCounter(long hobbiImportCounter) {
        this.hobbiImportCounter = hobbiImportCounter;
    }

    public long getRelativesImportCounter() {
        return relativesImportCounter;
    }

    public void setRelativesImportCounter(long relativesImportCounter) {
        this.relativesImportCounter = relativesImportCounter;
    }

    public long getSegmentsImportCounter() {
        return segmentsImportCounter;
    }

    public void setSegmentsImportCounter(long segmentsImportCounter) {
        this.segmentsImportCounter = segmentsImportCounter;
    }

    public long getFiletypesImportCounter() {
        return filetypesImportCounter;
    }

    public void setFiletypesImportCounter(long filetypesImportCounter) {
        this.filetypesImportCounter = filetypesImportCounter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
