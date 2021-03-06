package ua.pp.fairwind.internalDBSystem.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;

import javax.persistence.*;

/**
 * Created by ������ on 17.07.2015.
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
    String fileMimeType;
    String fileOriginalName;
    @Lob
    @JsonIgnore
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

    public String getFileMimeType() {
        return fileMimeType;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    public String getFileOriginalName() {
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }
}
