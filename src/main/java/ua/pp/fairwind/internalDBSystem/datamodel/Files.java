package ua.pp.fairwind.internalDBSystem.datamodel;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "FILES")
public class Files {
    @Id
    @Column(name = "FILE_ID",columnDefinition = "file record id")
    @GeneratedValue
    Long fileId;
    @Column(name = "FILE_COMMENTS",length = 255,nullable = true,columnDefinition = "comments to the file or file name")
    String fileNameComments;
    @Lob
    @Column(name = "BIN_FILE_DATA",columnDefinition = "the binary file date",nullable = false)
    byte[] fileData;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileNameComments() {
        return fileNameComments;
    }

    public void setFileNameComments(String fileNameComments) {
        this.fileNameComments = fileNameComments;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
