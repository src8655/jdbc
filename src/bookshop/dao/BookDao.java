package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.AuthorVo;
import bookshop.vo.BookVo;

public class BookDao {
	public BookVo get(Long no) {
		return null;
	}
	public boolean update(BookVo vo) {
		boolean result = false;
		
		//책정보 변경
		
		return result;
	}
	public boolean update(Long no, String status) {
		boolean result = false;
		
		//상태 변경
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			
			String sql = "update book set status=? where no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setLong(2, no);
			int count = pstmt.executeUpdate();
			
			if(count == 1) result = true;
			
		} catch(SQLException e) {
			System.out.println("error" + e);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	public boolean insert(BookVo vo) {
		boolean result = false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			
			String sql = "insert into book values(null, ?, '대여가능', ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setLong(2, vo.getAuthorNo());
			int count = pstmt.executeUpdate();
			
			if(count == 1) result = true;
			
		} catch(SQLException e) {
			System.out.println("error" + e);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<BookVo> getList() {
		List<BookVo> result = new ArrayList<BookVo>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			String sql = "select a.title, b.name, a.status" + 
					" from book a, author b" + 
					" where a.author_no = b.no" + 
					" order by a.no asc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString(1);
				String authorName = rs.getString(2);
				String status = rs.getString(3);
				
				BookVo vo = new BookVo();
				vo.setTitle(title);
				vo.setAuthorName(authorName);
				vo.setStatus(status);
				
				result.add(vo);
			}
			
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
	
	private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.0.10:3307/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}
		
		
		return con;
	}
}
