package test.pg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTest {

	public static void main(String[] args) {
		boolean result = update(29, "디자인팀~~~");
		if(result) {
			System.out.println("업데이트 성공!");
		}
	}
	public static boolean update(int no, String name) {
		boolean result = false;
		
		Connection con = null;
		Statement stmt = null;
		
		try {
			//1. JDBC Driver(MariaDB) 로딩
			Class.forName("org.postgresql.Driver");
			
			//2. 연결하기
			String url = "jdbc:postgresql://192.168.0.10:5432/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");

			//3. statement 객체 생성
			stmt = con.createStatement();
			
			//4. sql문 실행
			String sql = "update author set name='" + name + "' where no='" + no + "'";
			int count = stmt.executeUpdate(sql);
			
			if(count == 1) result = true;
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		} catch(SQLException e) {
			System.out.println("error" + e);
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
