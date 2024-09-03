package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class doctors {
    private Connection conn;
    private Scanner sc;

    public doctors(Connection conn, Scanner sc){
        this.conn=conn;
        this.sc=sc;
    }
    public void addDoctor(){
        System.out.print("Enter the doctor name: ");
        String name=sc.next();
        sc.nextLine();


        System.out.print("Specialization:  ");
        String specialization=sc.nextLine();

        try{
            String query="INSERT INTO doctors(name,specialization) VALUES (?,?);";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1,name);

            ps.setString(2,specialization);
            int affectedrows=ps.executeUpdate();
            if(affectedrows>0){
                System.out.println("record inserted successfully:)");
            }
            else{
                System.out.println("failed to add doctor:(");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void viewdoctors(){

        try{
            String query="Select * from doctors;";
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs =ps.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+-------------------------------+");
            System.out.println("|Doctor_id  | Doctors_name        |Specialization                 |");
            System.out.println("+------------+--------------------+-------------------------------+");
            while(rs.next()){
                int id=rs.getInt("Did");
                String name=rs.getString("name");

                String specialization =rs.getString("Specialization");


                System.out.printf("|%-11s|%-18s|%-30s|\n",id,name,specialization);
                System.out.println("+------------+--------------------+-------------------------------+");

            }



        }
        catch(SQLException e){
            e.printStackTrace();
        }


    }
    public boolean check_doctor(int id){
        try{
            String query ="Select * from doctors WHERE Did=? ";
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
    public void Deleterecord(Connection conn,Scanner sc)throws SQLException{

        try {
            System.out.println("Enter the customer id to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (!DoctorExist(conn, id)) {
                System.out.println("customer not found");
                return;
            }





            String query = "DELETE FROM doctors WHERE Did="+id;
            try(Statement stmt=conn.createStatement()){
                int rowsaffected=stmt.executeUpdate(query);
                if(rowsaffected>0){
                    System.out.println("details deleted successfully");
                }
                else{
                    System.out.println("details not deleted");
                }

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static boolean DoctorExist(Connection conn,int id){
        try{
            String query="SELECT Did FROM doctors WHERE Did="+id;
            try(Statement stmt=conn.createStatement()){
                ResultSet res=stmt.executeQuery(query);
                return res.next();

            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
