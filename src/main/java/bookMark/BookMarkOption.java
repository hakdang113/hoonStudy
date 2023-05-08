package bookMark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// DAO
public class BookMarkOption {

	private Connection conn;
	private ResultSet rs;

	
	// WifiDAO
	public BookMarkOption() {
		try {
			String dbUrl = "jdbc:mariadb://192.168.219.102:3306/testdb3";
			String dbUserId = "testuser3";
			String dbPassword = "zerobase";
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUserId, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// 추가 또는 수정 시 현재 시간을 가져오는 함수
	public String getDate() {
		String sql = "select now()";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // 데이터베이스 오류
	}

	
	// 새롭게 추가될 북마크 ID 번호를 조정하기 위한 함수
	public int getNext() {
		String sql = "select bookmarkID from bookmarkgroup order by bookmarkID desc";
		// 내림차순으로 가장 마지막에 등록된 북마크의 ID 번호를 가져옴

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // 첫번째 북마크인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}

	
	// 북마크 그룹 수정 함수
	public int editBookMarkGroup(BookMarkGroup updateGroup) {
		String sql = " update bookmarkgroup set bookmarkName = ?, registerNum = ?, EditDate = ? where bookMarkID = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateGroup.getBookmarkName());
			pstmt.setInt(2, updateGroup.getRegisterNum());
			pstmt.setString(3, getDate());
			pstmt.setInt(4, updateGroup.getID());

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	// 북마크 그룹 추가 함수
	public int addBookMarkGroup(String bookmarkName, int registerNum) {
		String sql = " insert into bookmarkgroup values (?, ?, ?, ? ,?) ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bookmarkName);
			pstmt.setInt(3, registerNum);
			pstmt.setString(4, getDate());
			pstmt.setString(5, getDate());

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	// 북마크 그룹 목록을 보여주는 함수
	public ArrayList<BookMarkGroup> getBmkList(int pageNumber) {
		String sql = "select * from bookmarkgroup where bookMarkId < ? order by bookmarkID desc limit 20";
		ArrayList<BookMarkGroup> arrlist = new ArrayList<BookMarkGroup>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookMarkGroup bmarkGroup = new BookMarkGroup();
				bmarkGroup.setID(rs.getInt(1));
				bmarkGroup.setBookmarkName(rs.getString(2));
				bmarkGroup.setRegisterNum(rs.getInt(3));
				bmarkGroup.setRegisterDate(rs.getString(4));
				bmarkGroup.setEditDate(rs.getString(5));
				arrlist.add(bmarkGroup);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrlist;
	}

	
	// 특정 번호(bookmarkID)의 북마크 데이터를 가져오는 함수
	public BookMarkGroup getBmkg(int bookmarkID) {
		String sql = " select * from bookmarkgroup where bookmarkID = ? ";
		BookMarkGroup bmkGroup = null;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookmarkID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				bmkGroup = new BookMarkGroup();
				bmkGroup.setID(rs.getInt(1));
				bmkGroup.setBookmarkName(rs.getString(2));
				bmkGroup.setRegisterNum(rs.getInt(3));
				bmkGroup.setRegisterDate(rs.getString(4));
				bmkGroup.setEditDate(rs.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bmkGroup;
	}

	
	// 특정 번호(bookmarkID)의 북마크를 지우는 함수
	public int delBookmarkGroup(int bookmarkID) {
		String sql = " delete from bookmarkgroup where bookmarkID = ? ";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookmarkID);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	public boolean isNextPage(int pageNumber) {
		String sql = "select * from bookmarkgroup where bookMarkId < ? And insertName_available = 1 ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 결과가 존재한다면
				return true; // true로 리턴 => 다음 페이지로 넘어갈 수 있음
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false; // 다음 페이지로 넘어갈 수 없음
	}
}
