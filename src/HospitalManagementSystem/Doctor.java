package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
	private Connection connection;

	public Doctor(Connection connection) {
		this.connection = connection;

	}

	public void viewDoctors() {
		String query = "select * from doctor";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+------------+-----------------+------------------+");
			System.out.println("|  Doctor Id | Name            | Specialization   |");
			System.out.println("+------------+-----------------+------------------+");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String specialization = resultSet.getString("specialization");
				System.out.printf("| %-11s| %-16s| %-17s|\n", id, name, specialization);
				System.out.println("+------------+-----------------+------------------+");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM Doctor WHERE id = ?";

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
