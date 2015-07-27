package ua.pp.fairwind.internalDBSystem.datamodel;

import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "FILES")
public class Files {
    @Id
    @Column(name = "FILE_ID")
    @GeneratedValue
    Long fileId;
    @Column(name = "FILE_COMMENTS",length = 255,nullable = true)
    String fileNameComments;
    @ManyToOne
    FilesType filesType;
    @Lob
    @Column(name = "BIN_FILE_DATA",nullable = false)
    byte[] fileData;
    @Version
    private long versionId;

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

    public FilesType getFilesType() {
        return filesType;
    }

    public void setFilesType(FilesType filesType) {
        this.filesType = filesType;
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }
}
