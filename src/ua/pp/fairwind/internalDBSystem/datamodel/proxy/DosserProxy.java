package ua.pp.fairwind.internalDBSystem.datamodel.proxy;

import ua.pp.fairwind.internalDBSystem.datamodel.Dosser;
import ua.pp.fairwind.internalDBSystem.datamodel.Files;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.InfoType;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 15.09.2015.
 */
public class DosserProxy {
    static final SimpleDateFormat formater=new SimpleDateFormat("MM/dd/yyyy");
    private String fio;
    private String code;
    private String bethday;
    private String status;
    private String subdivision;
    private String category;
    private String infotype;
    private String info;
    private String fileType;
    private String fileComments;
    private String fileUrl;
    private String creationTime;
    private String mime;
    private String filename;
    private boolean confidentional;

    public DosserProxy(Dosser dosser){
        this(dosser.getPerson().getFio(),dosser.getPerson().getCode(),dosser.getPerson().getDateberthdey(),dosser.getPerson().getPersonStatus().name(),dosser.getSubdivision(),dosser.getCategory(),dosser.getInfotype(),dosser.getTextinfo(),dosser.getFileinfo(),dosser.getCreationTime(),dosser.isConfidential());
    }

    public DosserProxy(String fio, String code, Long bethday, String status, Subdivision subdivision, Category category, InfoType infotype, String info, Files file,Long creationTime,boolean confidentional) {
        this.fio = fio;
        this.confidentional=confidentional;
        this.code = code;
        this.bethday = bethday==null?"":formater.format(new Date(bethday));
        this.status = status;
        this.subdivision = subdivision==null?"---":subdivision.getName();
        this.category = category==null?"---":category.getName();
        this.infotype = infotype==null?"---":infotype.getTypeName();
        this.info = info;
        if(file!=null){
            this.fileType = file.getFilesType()==null?"---":file.getFilesType().getFilesTypeName();
            this.fileComments = file.getFileNameComments();
            this.fileUrl = "/file/view?fileID="+file.getFileId();
            mime=file.getFileMimeType();
            filename=file.getFileOriginalName();
        }else{
            this.fileType = "";
            this.fileComments = "";
            this.fileUrl = "";
        }
        this.creationTime=creationTime==null?"":formater.format(new Date(creationTime));
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBethday() {
        return bethday;
    }

    public void setBethday(String bethday) {
        this.bethday = bethday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileComments() {
        return fileComments;
    }

    public void setFileComments(String fileComments) {
        this.fileComments = fileComments;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isConfidentional() {
        return confidentional;
    }

    public void setConfidentional(boolean confidentional) {
        this.confidentional = confidentional;
    }
}
