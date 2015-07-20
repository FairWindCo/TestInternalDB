package ua.pp.fairwind.internalDBSystem.datamodel;

import ua.pp.fairwind.internalDBSystem.datamodel.directories.Activities;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Hobbies;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonStatus;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 16.07.2015.
 */
@Entity
@Table(name = "PERSONS")
public class Person {
    @Id
    @Column(name = "PERSON_ID",nullable = false)
    @GeneratedValue
    private Long personId;
    @Column(name = "FIO",nullable = false)
    private String FIO;
    @Column(name = "PERSON_CODE")
    private String code;
    @Column(name = "BIRTHDAY",nullable = false)
    private Long dateberthdey;
    @Column(name = "PASSPORT_INFO")
    private String passportInfo;
    @Column(name = "PERSON_STATUS",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PersonStatus personStatus;
    @Column(name = "PERSON_TYPE",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PersonType personType;
    @OneToOne(targetEntity = Files.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Files photo;
    @OneToMany(targetEntity = Files.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Files> files=new HashSet<>();
    @OneToMany(targetEntity = Contact.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Contact> contacts=new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private Hobbies hobbie;
    @Column(name = "hobbies")
    private String hobbiesComments;
    @ManyToOne(fetch = FetchType.EAGER)
    private Activities activities;
    @Column(name = "activities")
    private String activitiesComments;

    private long createdTime;
    private long lastModifyTime;

    @Version
    private long version;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Person() {
        createdTime=System.currentTimeMillis();
    }

    public Person(String FIO) {
        this.FIO = FIO;
        this.personId=(long)(Math.random()*1000000);
        this.dateberthdey=System.currentTimeMillis()-((int)Math.random()*2500);
        this.code=Integer.toString((int) (Math.random() * 1234546));
        this.personStatus=PersonStatus.ACTIVE;
        this.personType=PersonType.CLIENT;
        createdTime=System.currentTimeMillis();
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDateberthdey() {
        return dateberthdey;
    }

    public String getBethday() {
        if(dateberthdey!=null) {
            return SimpleDateFormat.getDateInstance().format(new Date(dateberthdey));
        } else {
            return "----";
        }
    }


    public void setDateberthdey(Long dateberthdey) {
        this.dateberthdey = dateberthdey;
    }

    public String getPassportInfo() {
        return passportInfo;
    }

    public void setPassportInfo(String passportInfo) {
        this.passportInfo = passportInfo;
    }

    public PersonStatus getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(PersonStatus personStatus) {
        this.personStatus = personStatus;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }



}
