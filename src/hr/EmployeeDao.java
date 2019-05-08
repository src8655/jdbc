package hr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

	public List<EmployeeVo> getList(String keyword) {
		List<EmployeeVo> result = new ArrayList<EmployeeVo>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//1. JDBC Driver(MariaDB) 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mariadb://192.168.0.10:3307/employees";
			con = DriverManager.getConnection(url, "hr", "hr");

			//3. statement 객체 생성
			//4. sql문 실행
			String sql = "select emp_no, birth_date, first_name, last_name, gender, hire_date" + 
					" from employees" +
					" where first_name like '%'||?||'%'" + 
					" or last_name like '%'||?||'%'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			rs = pstmt.executeQuery();
			
			//5. 결과 가져오기
			while(rs.next()) {
				Long no = rs.getLong(1);
				String birthDate = rs.getString(2);
				String firstName = rs.getString(3);
				String lastName = rs.getString(4);
				String gender = rs.getString(5);
				String hireDate = rs.getString(6);
				
				EmployeeVo vo = new EmployeeVo();
				vo.setNo(no);
				vo.setBirthDate(birthDate);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setGender(gender);
				vo.setHireDate(hireDate);
				
				result.add(vo);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		} catch(SQLException e) {
			System.out.println("error" + e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
