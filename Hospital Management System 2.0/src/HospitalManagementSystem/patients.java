package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patients {
    private Connection conn;
    private Scanner sc;

    public patients(Connection conn, Scanner sc){
        this.conn=conn;
        this.sc=sc;
    }
      public void addPatient(){
        System.out.println("Enter the patient name: ");
        String name=sc.nextLine();
        sc.nextLine();
        System.out.println("enter patient age: ");
        int age=sc.nextInt();
        sc.nextLine();
        System.out.println("enter the patient gender: ");
        String gender=sc.nextLine();

        System.out.println("associated doctor: ");
        String ass_doc=sc.nextLine();

        System.out.println("prescribed medicine");
        String presc_med=sc.nextLine();


        try{
           String query="INSERT INTO HospitalManagementSystem.patients(name,age,gender,associated_doc,prescribed_drug) VALUES (?,?,?,?,?);";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,gender);
            ps.setString(4,ass_doc);
            ps.setString(5,presc_med);
            int affectedrows=ps.executeUpdate();
            if(affectedrows>0){
                System.out.println("record inserted successfully:)");
            }
            else{
                System.out.println("failed to add patient:(");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
      }
      public void prescription(){

        try{
            String query="Select * from patients;";
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs =ps.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+-----------------+--------+---------------+------------------------+----------------------+");
            System.out.println("|Patient_id  | Patients_name   | Age    | Gender        |Associated_doc          |Prescribed_drugs      |");
            System.out.println("+------------+-----------------+--------+---------------+------------------------+----------------------+");
            while(rs.next()){
                int id=rs.getInt("Pid");
                String name=rs.getString("name");
                int age=rs.getInt("age");
                String gender=rs.getString("gender");
                String ass_doc=rs.getString("associated_doc");
                String pres_drug=rs.getString("prescribed_drug");
                System.out.printf("|%-13s|%-17s|%-8s|%-15s|%-24s|%-22s|\n",id,name,age,gender,ass_doc,pres_drug);
                System.out.println("+------------+-----------------+--------+---------------+------------------------+----------------------+");

            }



        }
        catch(SQLException e){
            e.printStackTrace();
        }


      }
    public boolean check_patient(int id){
        try{
            String query ="Select * from patients WHERE Pid=? ";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return true;

            }
            else{
                return false;

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
