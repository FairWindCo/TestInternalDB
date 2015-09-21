package ua.pp.fairwind.internalDBSystem;

import com.hexiong.jdbf.DBFReader;
import com.hexiong.jdbf.JDBFException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonStatus;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.services.repository.PersonRepository;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by Сергей on 21.09.2015.
 */
public class ImportUtility {
    public static void main(String[] args) {
        ApplicationContext context =new ClassPathXmlApplicationContext("spring-config.xml");
        PersonRepository personrepository=context.getBean(PersonRepository.class);
        int impoeted=importPersons(personrepository);
        System.out.println("IMPORTED RECORDS:"+impoeted);


    }

    static private int importPersons(PersonRepository repository){
        if(repository==null) return 0;
        int imported=0;
        try {
            DBFReader dbfreader = new DBFReader("D:/JAVADEV/CARSKI/Cmr.dbf");
            //DBFReader dbfreader = new DBFReader("E:\\hexiongshare\\test.dbf");
            int i;
            for (i = 0; i < dbfreader.getFieldCount(); i++) {
                System.out.print(dbfreader.getField(i).getName() + "  ");
            }
            System.out.print("\n");
            for (i = 0; dbfreader.hasNextRecord(); i++) {
                Object[] aobj = dbfreader.nextRecord(Charset.forName("Cp1251"));
                for (int j = 0; j < aobj.length; j++)
                    System.out.print(aobj[j] + "  |  ");
                System.out.print("\n");
                String key1c=(aobj[0]!=null)?(String)aobj[0]:null;
                String name=(aobj[1]!=null)?(String)aobj[1]:null;
                Date betday=(aobj[3]!=null)?(Date)aobj[3]:null;
                Person person=repository.findByKey1C(key1c);
                if(key1c==null)continue;
                if(name==null)continue;
                if(person==null){
                    person=repository.findByFio(name);
                }
                if(person==null){
                    person=new Person();
                    person.setFio(name);
                    person.setKey1c(key1c);
                    if(betday!=null){
                        person.setDateberthdey(betday.getTime());
                    } else {
                        person.setDateberthdey(0L);
                    }
                    person.setPersonStatus(PersonStatus.ACTIVE);
                    person.setPersonType(PersonType.CLIENT);
                    repository.save(person);
                    imported++;
                }
            }

            System.out.println("Total Count: " + i);
        }catch (JDBFException exdbf){
            System.out.println(exdbf.toString());
        }
        return imported;
    }
}
