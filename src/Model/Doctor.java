package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User{
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Doctor() {
		super();
	}
	public Doctor(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}
	
	public boolean addWorkHour(int doctor_id,String doctor_name,String workDate) throws SQLException{
		int key = 0;
		int count = 0;
		String query = "INSERT INTO workhour" + "(doctor_id,doctor_name,workdate) VALUES (?,?,?)";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workhour WHERE status='a' AND doctor_id= "+doctor_id+" AND workdate ='"+workDate+"'");
			
			while(rs.next()) {
				count++;
				break;
			}
			
			if(count ==0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setString(3, workDate);
				preparedStatement.executeUpdate();
			}
			key=1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(key==1) {
			return true;
		}else {
			return false;
		}
	}
	
	public ArrayList<WorkHour> getWorkHourList(int doctor_id) throws SQLException{
		ArrayList<WorkHour> list = new ArrayList<>();
		WorkHour obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workhour WHERE status ='a' AND doctor_id = "+doctor_id);
			while(rs.next()) {
				obj = new WorkHour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setWorkDate(rs.getString("workdate"));
				obj.setStatus(rs.getString("status"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public boolean deleteWorkHour(int id) throws SQLException {
		String query = "DELETE FROM workhour WHERE id = ?";
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
