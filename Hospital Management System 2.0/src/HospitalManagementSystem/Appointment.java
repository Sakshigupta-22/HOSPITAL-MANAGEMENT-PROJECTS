package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointment {



    private Connection conn;
    private Scanner sc;
    patients patient=new patients(conn,sc);
    doctors doctor=new doctors(conn,sc);


    public Appointment(Connection conn, Scanner sc){
        this.conn=conn;
        this.sc=sc;
    }
    public void bookAppointment(patients patient, doctors doctor, Connection conn, Scanner sc){
        System.out.println("Enter the patient id: ");
        int patientid=sc.nextInt();
        System.out.println("Enter the doctor  id: ");
        int doctorid=sc.nextInt();
        System.out.println("Enter the appointment date: (yyyy-mm-dd)");
        String appointmentdate=sc.next();

        if(patient.check_patient(patientid) && doctor.check_doctor(doctorid)){
            if(checkDoctorAvailibility(doctorid,appointmentdate,conn)){
               String appointmentquery="INSERT INTO appointments (patient_id,doctor_id, appointment_date) VALUES (?,?,?) ";

                try{
                    PreparedStatement ps=conn.prepareStatement(appointmentquery);
                    ps.setInt(1,patientid);
                    ps.setInt(2,doctorid);
                    ps.setString(3,appointmentdate);
                    int rowsaffected=ps.executeUpdate();
                    if(rowsaffected>0){
                        System.out.println("Appointment Booked");
                    }
                    else{
                        System.out.println("booking failed");
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }

            }
            else{
                System.out.println("Doctor is not available on this date");
            }
        }
        else{
            System.out.println("Either patient or doctor doesn't exist");
        }




    }
    public static boolean checkDoctorAvailibility(int doctorid,String appointmentdate,Connection conn){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
        try{
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,doctorid);
            ps.setString(2,appointmentdate);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
               int count=rs.getInt(1);
               if(count==0){
                   return true;
               }
               else{
                   return false;
               }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
