package ua.pp.fairwind.internalDBSystem.datamodel.administrative;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;

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
    HashSet<Category> categories=new HashSet<>();
    long versionid;
}
