package ua.pp.fairwind.internalDBSystem.datamodel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 16.07.2015.
 */
public class Person {
    private Long personId;
    private String FIO;
    private String code;
    private Long dateberthdey;
    private String passportInfo;
    private PersonStatus personStatus;
    private PersonType personType;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Person() {
    }

    public Person(String FIO) {
        this.FIO = FIO;
        this.personId=(long)(Math.random()*1000000);
        this.dateberthdey=System.currentTimeMillis()-((int)Math.random()*2500);
        this.code=Integer.toString((int) (Math.random() * 1234546));
        this.personStatus=PersonStatus.ACTIVE;
        this.personType=PersonType.CLIENT;
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
