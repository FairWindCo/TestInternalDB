package ua.pp.fairwind.internalDBSystem.datamodel;

import ua.pp.fairwind.internalDBSystem.datamodel.directories.ContactType;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "CONTACTS")
public class Contact {
    @Id
    @GeneratedValue
    @Column(name = "CONTACT_ID")
    private Long contactId;
    @Column(name = "CONTACT_TEXT")
    private String contactinfo;
    @ManyToOne
    private ContactType contactType;
    @Version
    private long version;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public void setContactinfo(String value) {
        this.contactinfo = value;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
