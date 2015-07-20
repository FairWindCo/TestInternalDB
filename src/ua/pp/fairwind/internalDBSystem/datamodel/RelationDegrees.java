package ua.pp.fairwind.internalDBSystem.datamodel;

import ua.pp.fairwind.internalDBSystem.datamodel.directories.Relatives;

import javax.persistence.*;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "RELATION_DEGREES")
public class RelationDegrees {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Relatives relatives;
    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Relatives getRelatives() {
        return relatives;
    }

    public void setRelatives(Relatives relatives) {
        this.relatives = relatives;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
