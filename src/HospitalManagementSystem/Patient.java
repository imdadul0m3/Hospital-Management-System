package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void addPatient() {
		System.out.println("Enter Patient Name: ");
		String name = scanner.next();
		System.out.println("Enter Patient Age: ");
		int age = scanner.nextInt();
		System.out.println("Enter Patient Gender: ");
		String gender = scanner.next();

		try {
			String query = "INSERT INTO patients(name, age, gender)VALUES(?,?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Patient Added Successfully!!");
			} else {
				System.out.println("Failed to add Patient!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewPatient() {
		String query = "select * from patients";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+------------+-----------------+--------+----------+");
			System.out.println("| Patient Id | Name            | Age    | Gender   |");
			System.out.println("+------------+-----------------+--------+----------+");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				System.out.printf("| %-11s| %-16s| %-7s| %-9s|", id, name, age, gender);
				System.out.println();
				System.out.println("+------------+-----------------+--------+----------+");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean getPatientById(int id) {
		String query = "SELECT * FROM patients WHERE id = ?";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
}







