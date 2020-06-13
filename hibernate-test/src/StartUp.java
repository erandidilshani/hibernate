import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class StartUp {
    public static void main(String[] args) {

        //-------------------------------------- Save Student-------------------------


        Student student= new Student(1002,"Kamal","Colombo","011-2020200");

        Configuration configuration= new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);
        SessionFactory factory=configuration.buildSessionFactory();
        Session session=factory.openSession();
        //------------------------
        Transaction transaction=session.beginTransaction();
        System.out.println(session.save(student));
        transaction.commit();
        //-----------------------

    }
}
