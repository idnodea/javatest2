package mvjsp.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;



import mvjsp.board.model.Board;
import mvjsp.board.model.Member;
import mvjsp.jdbc.JdbcUtil;

public class BoardDao {
    private static BoardDao instance = new BoardDao();
    
    public static BoardDao getInstance() {
        return instance;
    }

    private BoardDao() {
    	
    }
 // DB에 접속하여 Connection 객체를 반환
    public Connection getConnection() throws Exception {

        //Class.forName("org.mariadb.jdbc.Driver");
    	Class.forName("oracle.jdbc.driver.OracleDriver");
    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

        return conn;
    }
    
    // 게시글 갯수 얻기 
    public int selectCount(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select count(*) from board");
            rs.next();
            return rs.getInt(1);
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }
    }
    
    //리스트읽기
    public ArrayList<Board> selectList(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        
        ArrayList<Board> Blist = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select b.num, b.title, m.name, b.content, b.regtime, b.hits, b.memberno "
                    + "from board b, member m "
                    + "where b.memberno = m.memberno order by num desc");
            while(rs.next()) {
                Board board = new Board(rs.getInt("num"), rs.getString("title"), rs.getString("content"), rs.getString("regtime"), rs.getInt("hits"), rs.getInt("memberno"), rs.getString("name"));
                Blist.add(board);
            }
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }
        return Blist;
    }
    
    public int insert(Connection conn, Board board) throws SQLException {
        String sql = "INSERT INTO board (num, title, content, regtime, hits, memberno) VALUES (seq_board.nextval,?, ?, sysdate, 0, ?)";
        // hits는 새 게시글이므로 기본값 0으로 설정합니다. memberno는 세션 등에서 가져온 값을 사용해야 합니다.
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
//            pstmt.setString(3, this.getCurrentTime()); // 등록 시간 설정. 현재 시간을 자동으로 입력하려면, 이 부분을 자동으로 처리하는 로직이 필요합니다.
            // pstmt.setInt(4, board.getHits()); // hits는 SQL 쿼리 내에서 이미 0으로 설정됩니다.
            pstmt.setInt(3, board.getMemberno());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            // 오류 로깅 또는 다른 오류 처리
            e.printStackTrace();
            throw e; // 예외를 다시 던져 호출자가 처리할 수 있도록 합니다.
        }
    }
//    public int insert(Connection conn, Board board) throws SQLException {
//        // SQL 쿼리 문자열. 게시물을 삽입하기 위한 SQL 명령어를 작성합니다.
//        String sql = "INSERT INTO board (title, content, regtime, hits, memberno, name) VALUES (?, ?, ?, ?, 0, ?)";
//
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            // PreparedStatement 객체를 사용하여 SQL 쿼리에 필요한 파라미터를 설정합니다.
//            pstmt.setString(1, board.getTitle());       // 제목
//            pstmt.setString(2, board.getContent());     // 내용
//            pstmt.setString(3, board.getRegtime());     // 등록 시간
//            pstmt.setInt(4, board.getHits());           // 조회수
//            pstmt.setInt(5, board.getMemberno());       // 회원 번호  일단 0 또는 ?
//            pstmt.setString(6, board.getName());        // 작성자 이름
//
//            // SQL 쿼리 실행
//            return pstmt.executeUpdate();
//        }
//    }
    
    
    
    public int update(Connection conn, Board board) throws SQLException {
        String sql = "update board set title=?, content=?, regtime=sysdate where num=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setInt(3, board.getNum());
            return pstmt.executeUpdate();
        } 
    }
    
    public int delete(Connection conn, int num) throws SQLException {
        String sql = "delete from board where num=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, num);
            return pstmt.executeUpdate();
        } 
    }
    
    public Board selectOne(Connection conn, int num, boolean inc) throws SQLException {
        Board board = null;
        String sql = "select b.num, b.title, b.content, b.regtime, b.hits,m.memberno, m.id, m.name "
                + "from board b, member m "
                + "where b.memberno = m.memberno and b.num=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, num);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    board = new Board(
                            rs.getInt("num"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("regtime"),
                            rs.getInt("hits"),
                            rs.getInt("memberno"), // 새로 추가된 부분
                            rs.getString("id"),    // 새로 추가된 부분
                            rs.getString("name")
                    );
                    if (inc) {
                        try (PreparedStatement updateStmt = conn.prepareStatement("update board set hits = hits+1 where num=?")) {
                            updateStmt.setInt(1, num);
                            updateStmt.executeUpdate();
                        }
                    }
                }
            }
        }
        return board;
    }
    
 ////////////////////////////

    // 현재 시간을 문자열 형태로 반환
    public String getCurrentTime() {
        return LocalDate.now() + " " +
               LocalTime.now().toString().substring(0, 8);
    }
    
}