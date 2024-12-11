package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem{
	
    private static final String url = "jdbc:mysql://localhost:3307/hospital";
    private static final String username = "root";
    private static final String password = "imdadul";
    public static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
    	 
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				Connection connection = DriverManager.getConnection(url,username,password);
				Patient patient = new Patient(connection,scanner);
				Doctor doctor = new Doctor(connection);
				
				while(true) {
					System.out.println("HOSPTITAL MANAGEMENT SYSTEM ");
					System.out.println("1. Add Patient");
					System.out.println("2. View Patients");
					System.out.println("3. View Doctors");
					System.out.println("4. Book AppointMent");
					System.out.println("5. Exit");
					System.out.println("Enter your choice: ");
					int choice = scanner.nextInt();
					
					switch(choice) {
					case 1:
						patient.addPatient();
						System.out.println();
						break;
					case 2:
						patient.viewPatient();
						System.out.println();
						break;
					case 3: 
						doctor.viewDoctors();
						System.out.println();
						break;
					case 4:
						// Book Appointment
						bookAppointment(patient,doctor,connection,scanner);
						System.out.println();
						break;
					case 5:
						return;
					default:
						System.out.println("Enter valid choice!!!");
						break;
					}
					
				}
				
			}catch(SQLException e) { 
				e.printStackTrace();
			}
		
	}
    
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) {
    	
    	System.out.println("Enter Patient Id: ");
		int patiantId = scanner.nextInt();
    	System.out.println("Enter Doctor Id: ");
    	int doctorId = scanner.nextInt();
    	System.out.println("Enter appointment date (YYYY-MM-DD");
    	String appointmentDate = scanner.next();
    	
    	if(patient.getPatientById(patiantId) && doctor.getDoctorById(doctorId)){
    		if(checkDoctorAvailability(doctorId, appointmentDate,connection)) {
    			String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
    			
    			try {
    				PreparedStatement ps = connection.prepareStatement(appointmentQuery);
    				ps.setInt(1, patiantId);
    				ps.setInt(2, doctorId);
    				ps.setString(3,appointmentDate);
    				int rowsAffected = ps.executeUpdate();
    				if(rowsAffected > 0) {
    					System.out.println("Appointment Booked!!!");
    				}else {
    					System.out.println("Failed to Book Appointment!!!");
    				}
    			}catch(SQLException e) {
    				e.printStackTrace();
    			}
    			
    		}else {
    			System.out.println("Doctor not available on this date!!!");
    		}
    	}else {
    		System.out.println("Either doctor or pataint doesn't exist!!!");
    	}
    }

	public static  boolean checkDoctorAvailability(int doctorId, String appointmentDate,Connection connection) {
		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, doctorId);
			ps.setString(2, appointmentDate);
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				if(count == 0) {
					return true;
				}else {
					return false;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}









