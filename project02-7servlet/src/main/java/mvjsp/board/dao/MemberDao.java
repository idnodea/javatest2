package mvjsp.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mvjsp.board.model.Member;
import mvjsp.jdbc.JdbcUtil;
import mvjsp.jdbc.connection.ConnectionProvider;

public class MemberDao {
	private static MemberDao instance = new MemberDao();
	public static MemberDao getInstance() {
		return instance;
	}

	private MemberDao() { //이게 없으면 객체생성을 막게 된다. 뉴를 못하고 겟인스턴스만 부를수있다.
	}
	//세션검증용 추가
	public Member selectMember(String id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Member member = null;
	    
	    try {
	    	conn = ConnectionProvider.getConnection(); // getConnection 메서드는 데이터베이스 연결을 설정하는 메서드로 가정합니다.
	        String sql = "SELECT * FROM member WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            member = new Member(
	                rs.getInt("memberno"),
	                rs.getString("id"),
	                rs.getString("email"),
	                rs.getString("name")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	    	JdbcUtil.close(conn, pstmt, rs); // close 메서드는 데이터베이스 연결을 닫는 메서드로 가정합니다.
	    }
	    
	    return member;
	}
	
	 // 로그인 처리 메소드 추가
    public boolean login(String id, String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean loginResult = false;

        try {
            conn = ConnectionProvider.getConnection();
            String sql = "SELECT password FROM member WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("email");
                // 비밀번호 비교 (실제 구현에서는 해시 함수 등을 사용하여 비교하는 것이 보안에 좋음)
                if (storedPassword.equals(email)) {
                    loginResult = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
            JdbcUtil.close(conn);
        }

        return loginResult;
    }

	
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from member");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	public ArrayList<Member> selectAll(Connection conn) {
		ArrayList<Member> MList = new ArrayList<>();
		String sql = "select * from member";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member(rs.getInt("memberno"),
						rs.getString("id"),
						rs.getString("email"),
						rs.getString("name"));
				MList.add(member);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return MList;
	}
	
	public int insert(Connection conn, Member member) {
		String sql = "insert into member(memberno, id, email, name) values (seq_member.nextval,?,?,?)";
	    try ( 
	        PreparedStatement pstmt = conn.prepareStatement(sql);            
	    ) {
	        
	        // 쿼리 실행
	    	pstmt.setString(1, member.getId());
	    	pstmt.setString(2, member.getEmail());
	    	pstmt.setString(3, member.getName());
	        return pstmt.executeUpdate();
	    
	    } catch(Exception e) {
	        e.printStackTrace();
	    } 
		return 0;
	}
	
	public int update(Connection conn, Member member) {
		String sql = "update member set email = ?, name = ? where memberno = ?";
	    try ( 
	        PreparedStatement pstmt = conn.prepareStatement(sql);            
	    ) {
	        
	        // 쿼리 실행
	    	pstmt.setInt(3, member.getMemberno());
	    	pstmt.setString(1, member.getEmail());
	    	pstmt.setString(2, member.getName());
	        return pstmt.executeUpdate();
	    
	    } catch(Exception e) {
	        e.printStackTrace();
	    } 
		return 0;
	}
	
	public int delete(Connection conn, int memberno) {
		String sql = "delete from member where memberno = ?";
	    try ( 
	        PreparedStatement pstmt = conn.prepareStatement(sql);            
	    ) {
	        
	        // 쿼리 실행
	    	pstmt.setInt(1, memberno);
	        return pstmt.executeUpdate();
	    
	    } catch(Exception e) {
	        e.printStackTrace();
	    } 
		return 0;
	}
	
	public Member select(Connection conn, int memberno) {
		Member member = null;
		ResultSet rs = null;
		String sql = "select * from member where memberno = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {	
			pstmt.setInt(1, memberno);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member(rs.getInt("memberno"),
						            rs.getString("id"),
						            rs.getString("email"),
						            rs.getString("name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
		}
		return member;
		
	}
}





