package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Сергей on 16.09.2015.
 */
@Entity
@Table(name = "SYSTEM_JOURNAL")
public class ProgramOperationJornal {
    public static enum Operation{
        CREATE,
        UPDATE,
        DELETE,
        SELECT,
        SEARCH,
        PRINT,
        ADD_COMPLAINT
    }
    @Id
    @GeneratedValue
    @Column(name = "STATISTIC_ID")
    Long id;
    private Date journalDate =new Date();
    private Operation operation;
    private String user;
    private String object;
    private String info;

    public Date getJournalDate() {
        return journalDate;
    }

    public void setJournalDate(Date importDateTime) {
        this.journalDate = importDateTime;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
