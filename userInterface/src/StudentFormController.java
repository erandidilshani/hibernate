import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StudentFormController {
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtid;
    public TableView tbl;
    public TableColumn colid;
    public TableColumn colname;
    public TableColumn coladdress;
    public TableColumn colcontact;


    public void initialize(){
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadAllStudents();
    }

    private void loadAllStudents() {
        Configuration configuration= new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        List<Student> studentList=session.createQuery("FROM Student").list();//H Q L hibernate query language
        ObservableList<Student> list= FXCollections.observableArrayList(studentList);
        tbl.setItems(list);
    }


    public void saveOnAction(ActionEvent actionEvent) {

        Student student= new Student(Integer.parseInt(txtid.getText()),txtName.getText(),txtAddress.getText(),txtContact.getText());
        Configuration configuration= new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        int saveId=(Integer) session.save(student);
        if (saveId==Integer.parseInt(txtid.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved", ButtonType.OK).show();
        }
        transaction.commit();


    }

    Student tempStudent=null;

    public void DeleteOnAction(ActionEvent actionEvent) {
        Configuration configuration= new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(tempStudent);
        transaction.commit();

    }

    public void searchOnAction(ActionEvent actionEvent) {
        Configuration configuration= new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();


        tempStudent = session.get(Student.class,Integer.parseInt(txtid.getText()));
        if (tempStudent!=null){
            txtAddress.setText(tempStudent.getAddress());
            txtContact.setText(tempStudent.getContact());
            txtName.setText(tempStudent.getName());
        }else{
            new Alert(Alert.AlertType.WARNING,"Empty Result",ButtonType.CLOSE).show();
        }

    }
}
