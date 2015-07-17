package ua.pp.fairwind.internalDBSystem.datamodel;

import ua.pp.fairwind.internalDBSystem.datamodel.directories.Segments;

import javax.persistence.*;
import java.util.HashSet;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "CLIENT_ADDITIONAL")
public class ClientAdditionalInfo {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private HashSet<RelationDegrees> relationDegrees=new HashSet<>();
    private String clientColorCODE;
    private String clientColorComments;
    private Segments clientSegment;
    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientColorCODE() {
        return clientColorCODE;
    }

    public void setClientColorCODE(String clientColorCODE) {
        this.clientColorCODE = clientColorCODE;
    }

    public String getClientColorComments() {
        return clientColorComments;
    }

    public void setClientColorComments(String clientColorComments) {
        this.clientColorComments = clientColorComments;
    }

    public Segments getClientSegment() {
        return clientSegment;
    }

    public void setClientSegment(Segments clientSegment) {
        this.clientSegment = clientSegment;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public HashSet<RelationDegrees> getRelationDegrees() {
        return relationDegrees;
    }

    public void addRelationDegrees(RelationDegrees relationDegrees) {
        this.relationDegrees.add(relationDegrees);
    }

    public void removeRelationDegrees(RelationDegrees relationDegrees) {
        this.relationDegrees.remove(relationDegrees);
    }

    public void removeAllRelationDegrees() {
        this.relationDegrees.clear();
    }

}
