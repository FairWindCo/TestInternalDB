package ua.pp.fairwind.internalDBSystem.datamodel;

import org.springframework.data.annotation.CreatedDate;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.InfoType;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.DosserType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ������ on 17.07.2015.
 */
@Entity
@Table(name = "DOSSIERS")
public class Dosser {
    @Id
    @Column(name = "DOSSIER_ID")
    @GeneratedValue
    private Long dossierId;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Subdivision subdivision;
    @ManyToOne
    private Category category;
    @ManyToOne
    private InfoType infotype;
    private boolean confidential;
    private String textinfo;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Files fileinfo;
    @ManyToOne
    private User createUser;
    @ManyToOne
    private Dosser parentDossierId;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_status")
    private DosserType recordStatus;

    @CreatedDate
    private long creationTime;
    @Version
    private long version;

    public Long getDossierId() {
        return dossierId;
    }

    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public InfoType getInfotype() {
        return infotype;
    }

    public void setInfotype(InfoType infotype) {
        this.infotype = infotype;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public String getTextinfo() {
        return textinfo;
    }

    public void setTextinfo(String text) {
        this.textinfo = text;
    }

    public Files getFileinfo() {
        return fileinfo;
    }

    public void setFileinfo(Files file) {
        this.fileinfo = file;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Dosser getParentDossierId() {
        return parentDossierId;
    }

    public void setParentDossierId(Dosser parentDossierId) {
        this.parentDossierId = parentDossierId;
    }

    public DosserType getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(DosserType status) {
        this.recordStatus = status;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getFormatedCreationTime(){
        return new Date(creationTime);
    }


}
