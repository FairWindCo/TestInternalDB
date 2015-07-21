package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 17.07.2015.
 */
@Entity
@Table(name = "SUBDIVISIONS")
public class Subdivision {
    @Id
    @GeneratedValue
    Long subdivisionId;
    String name;
    @ManyToMany(mappedBy = "subdivision")
    Set<Category> categories=new HashSet<>();
    long versionid;


}
