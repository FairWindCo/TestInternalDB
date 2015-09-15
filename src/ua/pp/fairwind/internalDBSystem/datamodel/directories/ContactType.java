package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by ������ on 17.07.2015.
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
        String key1c;
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

        public String getKey1c() {
                return key1c;
        }

        public void setKey1c(String key1c) {
                this.key1c = key1c;
        }
}
