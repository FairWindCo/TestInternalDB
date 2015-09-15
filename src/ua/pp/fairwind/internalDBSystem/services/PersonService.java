package ua.pp.fairwind.internalDBSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Сергей on 16.07.2015.
 */
@Service("personService")
@Transactional
public class PersonService {
    protected static Logger logger = Logger.getLogger("service");

    public List<Person> getAll() {
        logger.info("Retrieving all persons");
        /*
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM  Person");

        // Retrieve all
        return  query.list();/**/

        ArrayList<Person> list=new ArrayList<>();
        Person p1=new Person("Ivanov O.V.");
        Person p2=new Person("Петров О.О.");
        Person p3=new Person("Сидоров О.К.");
        Person p4=new Person("Никифоров М.М.");
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        return list;
    }
}
