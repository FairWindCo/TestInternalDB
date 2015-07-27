package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "HOBBIES")
public class Hobbies {
        @Id
        @Column(name = "HOBBIE_ID",nullable = false)
        @GeneratedValue
        Long hobbieId;
        @Column(name = "HOBBIE_NAME",nullable = false,length = 100)
        String hobbieName;
        @Version
        private long version;

        public Long getHobbieId() {
                return hobbieId;
        }

        public void setHobbieId(Long hobbieId) {
                this.hobbieId = hobbieId;
        }

        public String getHobbieName() {
                return hobbieName;
        }

        public void setHobbieName(String hobbieName) {
                this.hobbieName = hobbieName;
        }

        public long getVersion() {
                return version;
        }

        public void setVersion(long version) {
                this.version = version;
        }
}
