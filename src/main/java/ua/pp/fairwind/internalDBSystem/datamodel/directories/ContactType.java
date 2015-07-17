package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "CONTACTTYPES")
public class ContactType {
        @Id
        @Column(name = "CONTACT_TYPE_ID",nullable = false)
        @GeneratedValue
        Long contactTypeId;
        @Column(name = "CONTACT_TYPE_NAME",nullable = false,length = 50)
        String cobtactTypeName;
        @Version
        private long version;


        public Long getContactTypeId() {
                return contactTypeId;
        }

        public void setContactTypeId(Long contactTypeId) {
                this.contactTypeId = contactTypeId;
        }

        public String getCobtactTypeName() {
                return cobtactTypeName;
        }

        public void setCobtactTypeName(String cobtactTypeName) {
                this.cobtactTypeName = cobtactTypeName;
        }

        public long getVersion() {
                return version;
        }

        public void setVersion(long version) {
                this.version = version;
        }
}
