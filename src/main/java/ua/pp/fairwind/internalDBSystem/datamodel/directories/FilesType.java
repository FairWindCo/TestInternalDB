package ua.pp.fairwind.internalDBSystem.datamodel.directories;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "FILESTYPES")
public class FilesType {
        @Id
        @Column(name = "FILES_TYPE_ID",nullable = false)
        @GeneratedValue
        Long filesTypeId;
        @Column(name = "FILES_TYPE_NAME",nullable = false,length = 70)
        String filesTypeName;
        @Version
        private long version;

        public Long getFilesTypeId() {
                return filesTypeId;
        }

        public void setFilesTypeId(Long filesTypeId) {
                this.filesTypeId = filesTypeId;
        }

        public String getFilesTypeName() {
                return filesTypeName;
        }

        public void setFilesTypeName(String filesTypeName) {
                this.filesTypeName = filesTypeName;
        }

        public long getVersion() {
                return version;
        }

        public void setVersion(long version) {
                this.version = version;
        }
}
