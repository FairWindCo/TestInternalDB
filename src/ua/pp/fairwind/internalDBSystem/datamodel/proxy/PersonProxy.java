package ua.pp.fairwind.internalDBSystem.datamodel.proxy;

import java.util.Date;

/**
 * Created by Сергей on 14.09.2015.
 */
public class PersonProxy {
    private final long personId;
    private final String fio;
    private final String code;
    private final Date dateberthdey;

    public PersonProxy(Long personId,String fio, String code, Long dateberthdey) {
        this.personId=personId;
        this.fio = fio;
        this.code = code;
        if(dateberthdey!=null) {
            this.dateberthdey = new Date(dateberthdey);
        } else {
            this.dateberthdey=null;
        }
    }


    public long getPersonId() {
        return personId;
    }

    public String getFio() {
        return fio;
    }

    public String getCode() {
        return code;
    }

    public Date getDateberthdey() {
        return dateberthdey;
    }
}
