package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.Helper;

public class Hasta extends User {

	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Hasta() {
	}

	public Hasta(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);

	}

	public boolean register(String name, String tcNo, String password) throws SQLException {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO user" + "(name,tcNo,password,type) VALUES (?,?,?,?)";

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcno ='" + tcNo + "'");

			while (rs.next()) {
				duplicate = true;
				Helper.showMsg("Bu TC Numarasýna Ait Daha Önce Bir Kayýt Oluþturulmuþ !");
				break;
			}

			if (!duplicate) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, tcNo);
				preparedStatement.setString(3, password);
				preparedStatement.setString(4, "hasta");
				preparedStatement.executeUpdate();
				key = 1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean addAppointment(int doctor_id, int hasta_id, String doctor_name, String hasta_name,
			String appointmentDate) throws SQLException {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES (?,?,?,?,?)";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doctor_id);
			preparedStatement.setString(2, doctor_name);
			preparedStatement.setInt(3, hasta_id);
			preparedStatement.setString(4, hasta_name);
			preparedStatement.setString(5, appointmentDate);
			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateWorkHourStatus(int doctor_id,String workDate) throws SQLException {
		int key = 0;
		String query = "UPDATE workhour SET status = ? WHERE doctor_id = ? AND workdate = ?";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doctor_id);
			preparedStatement.setString(3, workDate);
			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateWorkHourStatusA(String doctorName,String workDate) throws SQLException {
		int key = 0;
		String query = "UPDATE workhour SET status = ? WHERE doctor_name = ? AND workdate = ?";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "a");
			preparedStatement.setString(2, doctorName);
			preparedStatement.setString(3, workDate);
			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteAppointment(int id) throws SQLException {
		String query = "DELETE FROM appointment WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key) {
			return true;
		}else {
			return false;
		}
	}

}
