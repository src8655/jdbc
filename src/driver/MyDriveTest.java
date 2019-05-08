package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDriveTest {

	public static void main(String[] args) {
		Connection con = null;
		
		try {
			//1. JDBC Driver(MariaDB) 로딩
			Class.forName("driver.MyDrive");
			
			//2. 연결하기
			String url = "jdbc:mydb://192.168.0.10:3307/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");

			System.out.println("연결성공 : " + con);
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}catch (SQLException e) {
			System.out.println("error : " + e);
		}finally {
			try {
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
