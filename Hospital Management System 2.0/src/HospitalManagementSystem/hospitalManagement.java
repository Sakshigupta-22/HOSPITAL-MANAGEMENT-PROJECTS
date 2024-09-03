package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class hospitalManagement {
    //url
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    //root
    private static final String username="root";
    //password
    private static final String password="SG22@sakshi";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        try{
            Connection conn= DriverManager.getConnection(url,username,password);
            System.out.println("do you want to create a new database(type YES) +" +
                    "if want to work on existing db set up by you (type NO)");
            String DBdecision=sc.next();
            if(DBdecision.equalsIgnoreCase("yes")){
                CreateDatabase(conn,sc);
            }
            System.out.println("Select database:");
            selectdb(conn,sc);
            System.out.println("want to create table(type YES) and (type No) if already created");
            String tabledecision=sc.next();
            if(tabledecision.equalsIgnoreCase("yes")){
                CreateTable(conn,sc);
                return;
            }
            patients patient=new patients( conn,sc);
            doctors doctor =new doctors(conn,sc);
            Appointment appointment=new Appointment(conn,sc);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM   ");
                System.out.println("1. Add patients");
                System.out.println("2. View patients records ");
                System.out.println("3. Add doctors");
                System.out.println("4. View doctors records ");
                System.out.println("5. delete record");
                System.out.println("6. BookAppointment ");
                System.out.println("0. Exit ");
                System.out.println("Enter your choice: ");
                int choice=sc.nextInt();

                switch(choice){
                    case 1://add patients
                        patient.addPatient();
                        System.out.println();
                        break;

                    case 2://patients
                        patient.prescription();
                        System.out.println();
                        break;

                    case 3://add doctors
                        doctor.addDoctor();
                        System.out.println();
                        break;

                    case 4://view doctors
                        doctor.viewdoctors();
                        System.out.println();
                        break;
                    case 5://delete doctors
                        doctor.Deleterecord(conn,sc);
                        System.out.println();
                        break;

                    case 6://appointment
                        appointment.bookAppointment(patient,doctor,conn,sc);
                        System.out.println();
                        break;

                    case 7://
                        Exit();
                        sc.close();
                        return;

                    default:
                        System.out.println("Enter valid choice ");

                }

            }


        }
        catch(SQLException | InterruptedException e){
            e.printStackTrace();
        }

    }
    private static void selectdb(Connection conn,Scanner sc)throws SQLException{
        System.out.println("enter the name of database you want to select:");
        String db_name=sc.next();
        String query="use  "+db_name+";";
        try{
            Statement stmt=conn.createStatement();
            stmt.execute(query);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private static void CreateDatabase(Connection conn,Scanner sc)throws SQLException{
        System.out.println("enter the name of database you want to create:");
        String db_name=sc.next();
        String query="CREATE database "+db_name+";";
        try(Statement stmt=conn.createStatement()){
            int rowsaffected =stmt.executeUpdate(query);
            if(rowsaffected > 0){
                System.out.println("DataBase created successfully:)");
            }
            else{
                System.out.println("DataBase not created successfully or exist");
            }
        }
    }
    private static void CreateTable(Connection conn,Scanner sc)throws SQLException {
        System.out.println("enter the name of table you want to create:");
        String table_name = sc.next();
        //patients
        String query = "CREATE TABLE " + table_name + " Pid INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(256) NOT NULL ," +
                "age VARCHAR(256) NOT NULL," +
                "gender DOUBLE NOT NULL," +
                "associated_doc VARCHAR (300) NOT NULL," +
                "prescribed_drug VARCHAR(500) " ;
        //doctors
        // String query = "CREATE TABLE " + table_name + " Did INT AUTO_INCREMENT PRIMARY KEY," +
//                "name VARCHAR(256) NOT NULL ," +
//                "specialization VARCHAR (255) NOT NULL)";

        //apppointments
//        String query = "CREATE TABLE " + table_name + "( id INT AUTO_INCREMENT PRIMARY KEY," +
//                "patient_id INT NOT NULL ," +
//                "doctor_id INT NOT NULL," +
//                "appointment_date DATE NOT NULL, "+
//                "FOREIGN KEY (patient_id) REFERENCES patients(Pid),"+
//                "FOREIGN KEY (doctor_id) REFERENCES doctors(Did) )";

        try (Statement stmt = conn.createStatement()) {
            boolean created = stmt.execute(query);
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet res = metaData.getTables(null, null, table_name, new String[]{"TABLE"});
            if (res.next()) {
                System.out.println("Table created successfully:)");
            } else {
                System.out.println("table not created successfully or already exist");
            }
        }
    }
    public static void Exit()throws InterruptedException{
        System.out.println("Exiting");
        int i=5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println("Thankyou for using Hospitaln management System!! :)");
    }
}
