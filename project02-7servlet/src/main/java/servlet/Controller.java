package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvjsp.board.dao.BoardDao;
import mvjsp.board.dao.MemberDao;
import mvjsp.board.model.Board;
import mvjsp.board.model.Member;
import mvjsp.jdbc.connection.ConnectionProvider;

/**
 * Servlet implementation class Controller
 */
@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			process(request, response);
		} catch (SQLException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			process(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println(uri);
		String path = uri.substring(uri.lastIndexOf("/"));
		System.out.println(path);
		if (path.equals("/MList.do")) {
			Connection conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();
			ArrayList<Member> MList = dao.selectAll(conn);
			request.setAttribute("MList", MList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("MList_view.jsp");
			dispatcher.forward(request, response);
		} else if (path.equals("/memberForm.do")) {
			response.sendRedirect("memberForm.jsp");
		} else if (path.equals("/memberInput.do")) {
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			String name = request.getParameter("name");

			Connection conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();
			Member member = new Member(id, email, name);
			dao.insert(conn, member);
			response.sendRedirect("index.jsp");
		} else if (path.equals("/updateForm.do")) {

			String mno = request.getParameter("memberno");
			System.out.println(mno);
			Connection conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();

			Member member = dao.select(conn, Integer.parseInt(mno));
			request.setAttribute("member", member);

			RequestDispatcher dispatcher = request.getRequestDispatcher("updateForm_view.jsp");
			dispatcher.forward(request, response);
		} else if (path.equals("/memberUpdate.do")) {
			String mno = request.getParameter("memberno");
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			String name = request.getParameter("name");

			Connection conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();
			Member member = new Member(Integer.parseInt(mno), id, email, name);
			dao.update(conn, member);
			response.sendRedirect("MList.jsp");

		} else if (path.equals("/memberDelete.do")) {
			String memberno = request.getParameter("memberno");
			Connection conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();
			dao.delete(conn, Integer.parseInt(memberno));
			response.sendRedirect("index.jsp");

		} else if (path.equals("/adminLogin.do")) {
			try (Connection conn = ConnectionProvider.getConnection()) {
				MemberDao dao = MemberDao.getInstance();
				ArrayList<Member> MList = dao.selectAll(conn);
				request.setAttribute("MList", MList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("adminMList_view.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				// 오류 처리
			}
		} else if (path.equals("/adminLogout.do")) {
			// HttpSession 객체를 가져옵니다.
			HttpSession session = request.getSession();

			// 세션을 무효화합니다.
			session.invalidate();

			// 로그인 페이지로 리다이렉트합니다.
			response.sendRedirect("adminLoginForm.jsp");

		}

		else if (path.equals("/adminMList.do")) {
			Connection conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();
			ArrayList<Member> adminMList = dao.selectAll(conn);
			request.setAttribute("adminMList", adminMList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("adminMList_view.jsp");
			dispatcher.forward(request, response);
		} else if (path.equals("/adminBlist.do")) {
//		    Connection conn = ConnectionProvider.getConnection();
//		    BoardDao dao = BoardDao.getInstance();
//		    ArrayList<Board> Blist = dao.selectList(conn); // 전체 게시물 리스트 조회
//		    request.setAttribute("Blist", Blist);
//		    RequestDispatcher dispatcher = request.getRequestDispatcher("Blist.jsp"); // Blist.jsp로 포워딩
//		    dispatcher.forward(request, response);
			try (Connection conn = ConnectionProvider.getConnection()) {
				BoardDao dao = BoardDao.getInstance();
				ArrayList<Board> adminBlist = dao.selectList(conn);
				request.setAttribute("adminBlist", adminBlist);
				RequestDispatcher dispatcher = request.getRequestDispatcher("adminBlist.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				// 오류 처리
			}

		} else if (path.equals("/loginForm.do")) {

			response.sendRedirect("loginForm.jsp");

		} else if (path.equals("/login.do")) { // 로그인관련
			// 요청에서 아이디와 비밀번호 파라미터 값을 받음
			String id = request.getParameter("id");
			String email = request.getParameter("email"); // HTML 폼에서 사용자가 입력한 비밀번호 필드의 이름과 일치해야 함

			Connection conn = ConnectionProvider.getConnection();
			MemberDao dao = MemberDao.getInstance();

			boolean loginResult = dao.login(id, email); // 로그인 처리 메소드 호출

			if (loginResult) {
				// 로그인 성공 시, 세션에 사용자 정보 저장 등의 추가 작업을 수행할 수 있습니다.
				HttpSession session = request.getSession();
				session.setAttribute("MEMBERID", id); // 예시로, 로그인한 사용자의 아이디를 세션에 저장

				response.sendRedirect("MList.do"); // 로그인 성공 페이지로 리다이렉션 loginSuccess.jsp
			} else {
				// 로그인 실패 시, 로그인 실패 메시지를 포함하여 로그인 페이지로 다시 리다이렉션
				response.sendRedirect("loginForm.jsp?error=invalidLogin"); // 로그인 폼으로 다시 리다이렉션하며, 오류 메시지를 쿼리 파라미터로 전달
			}
		} else if (path.equals("/logout.do")) {
			// HttpSession 객체를 가져옵니다.
			HttpSession session = request.getSession();

			// 세션을 무효화합니다.
			session.invalidate();

			// 로그인 페이지로 리다이렉트합니다.
			response.sendRedirect("loginForm.jsp");

		} else if (path.equals("/Bview.do")) {
			int num = Integer.parseInt(request.getParameter("num")); // 게시물 번호를 파라미터로 받음
			Connection conn = ConnectionProvider.getConnection();
			BoardDao dao = BoardDao.getInstance();
			Board board = dao.selectOne(conn, num, true); // 게시물 조회
			request.setAttribute("board", board);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Bview.jsp"); // Bview.jsp로 포워딩
			dispatcher.forward(request, response);

		} else if (path.equals("/Bdelete.do")) {
			int num = Integer.parseInt(request.getParameter("num")); // 삭제할 게시물 번호를 파라미터로 받음
			Connection conn = ConnectionProvider.getConnection();
			BoardDao dao = BoardDao.getInstance();
			dao.delete(conn, num); // 게시물 삭제
			response.sendRedirect("Blist.do"); // 리스트 페이지로 리다이렉트

		} else if (path.equals("/Blist.do")) {
//		    Connection conn = ConnectionProvider.getConnection();
//		    BoardDao dao = BoardDao.getInstance();
//		    ArrayList<Board> Blist = dao.selectList(conn); // 전체 게시물 리스트 조회
//		    request.setAttribute("Blist", Blist);
//		    RequestDispatcher dispatcher = request.getRequestDispatcher("Blist.jsp"); // Blist.jsp로 포워딩
//		    dispatcher.forward(request, response);
			try (Connection conn = ConnectionProvider.getConnection()) {
				BoardDao dao = BoardDao.getInstance();
				ArrayList<Board> Blist = dao.selectList(conn);
				request.setAttribute("Blist", Blist);
				RequestDispatcher dispatcher = request.getRequestDispatcher("Blist.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				// 오류 처리
			}

		} else if (path.equals("/Bupdate.do")) {
			int num = Integer.parseInt(request.getParameter("num")); // 수정할 게시물 번호를 파라미터로 받음
			Connection conn = ConnectionProvider.getConnection();
			BoardDao dao = BoardDao.getInstance();
			Board board = dao.selectOne(conn, num, false); // 게시물 조회(조회수 증가 X)
			request.setAttribute("board", board);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Bupdate.jsp"); // Bupdate.jsp로 포워딩
			dispatcher.forward(request, response);

		} else if (path.equals("/Bwrite.do")) { // 이제 글쓰기페이지로 이동하는 역할만
			// Bwrite.jsp 페이지로 포워딩
			RequestDispatcher dispatcher = request.getRequestDispatcher("Bwrite.jsp");
			dispatcher.forward(request, response);
		} else if (path.equals("/Binsert.do")) {
		    String title = request.getParameter("title");
		    String content = request.getParameter("content");
		    HttpSession session = request.getSession();
		    Member member = (Member)session.getAttribute("MEMBER"); // 세션에서 Member 객체 가져오기
		    
		    if (member != null) {
		        Board board = new Board();
		        board.setTitle(title);
		        board.setContent(content);
		        board.setMemberno(member.getMemberno()); // Member 객체에서 사용자 번호 사용
		        
		        BoardDao dao = BoardDao.getInstance();
		        try (Connection conn = ConnectionProvider.getConnection()) {
		            dao.insert(conn, board);
		            response.sendRedirect("Blist.do"); // 성공 시 게시글 목록으로 리다이렉션
		        }
		    } else {
		        // 사용자 정보가 세션에 없는 경우 (로그인하지 않은 상태)
		        response.sendRedirect("loginForm.jsp");
		    }
		
		
//		    String title = request.getParameter("title");
//		    String content = request.getParameter("content");
//		    String regtime = ""; // 등록일시는 현재 시간으로 설정해야 함
//		    int hits = 0; // 조회수는 0으로 초기화
//		    String memberId = (String) request.getSession().getAttribute("MEMBERID"); // 세션에서 회원 아이디 가져오기
//
//		    // 세션에서 회원번호와 이름 가져오기
//		    int memberno = (int) request.getSession().getAttribute("MEMBERNO");
//		    String name = (String) request.getSession().getAttribute("NAME");
//
//		    Connection conn = ConnectionProvider.getConnection();
//		    BoardDao dao = BoardDao.getInstance();
//		    Board board = new Board(title, content, regtime, hits, memberno, memberId, name);
//
//		    dao.insert(conn, board); // 게시물 등록
//		    response.sendRedirect("Blist.do"); // 리스트 페이지로 리다이렉트

//		else if (path.equals("/Binsert.do")) {  세션사용시
//		    String title = request.getParameter("title");
//		    String content = request.getParameter("content");
//		    
//		    // 실제로는 세션에서 사용자 정보를 가져와야 함
//		    // 예시 코드에서는 사용자 번호와 이름을 하드코딩한 것이므로 실제 환경에서는 세션을 이용하여 사용자 정보를 가져와야 함
//		    int memberno = 1; // 세션에서 사용자 번호를 가져오도록 수정해야 함
//		    String name = "test"; // 세션에서 사용자 이름을 가져오도록 수정해야 함
//
//		    try (Connection conn = ConnectionProvider.getConnection()) {
//		        BoardDao dao = BoardDao.getInstance();
//		        Board board = new Board();
//		        board.setTitle(title);
//		        board.setContent(content);
//		        board.setRegtime(dao.getCurrentTime()); // 현재 시간 설정
//		        board.setHits(0); // 새 게시글 조회수는 0
//		        board.setMemberno(memberno); // 세션에서 가져온 사용자 ID
//		        board.setName(name); // 세션에서 가져온 사용자 이름
//
//		        dao.insert(conn, board);
//		        response.sendRedirect("Blist.do"); // 게시글 목록 페이지로 리다이렉트
//		    } catch (Exception e) {
//		        e.printStackTrace();
//		        response.sendRedirect("errorPage.jsp"); // 오류 페이지로 리다이렉트
//		    }
//		

//		else if (path.equals("/Binsert.do")) {
//			String title = request.getParameter("title");
//			String content = request.getParameter("content");
//			// 세션에서 사용자 정보를 가져오는 부분은 구현 상황에 따라 달라질 수 있습니다.
//			// 예시 코드에서는 memberno와 name을 어떻게 처리할지에 대한 구체적인 구현이 필요합니다.
//			String name = request.getParameter("name"); // 예시, 실제 구현에서는 세션에서 사용자 이름을 가져와야 할 수 있습니다.
//			int memberno = 1; // 예시, 실제 구현에서는 세션에서 사용자 ID를 가져와야 합니다.
//
//			try (Connection conn = ConnectionProvider.getConnection()) {
//				BoardDao dao = BoardDao.getInstance();
//				Board board = new Board();
//				board.setTitle(title);
//				board.setContent(content);
//				board.setRegtime(dao.getCurrentTime()); // 현재 시간 설정
//				board.setHits(0); // 새 게시글 조회수는 0
//				board.setMemberno(memberno); // 세션에서 가져온 사용자 ID
//				board.setName(name); // 세션에서 가져온 사용자 이름
//
//				System.out.println(board);
//				dao.insert(conn, board);
//				response.sendRedirect("Blist.do"); // 게시글 목록 페이지로 리다이렉트
//			} catch (Exception e) {
//				e.printStackTrace();
//				response.sendRedirect("errorPage.jsp"); // 오류 페이지로 리다이렉트
//			}

//			 String title = request.getParameter("title");
//			    String content = request.getParameter("content");
//			    // 세션에서 사용자 정보 가져오기 (예시, 실제 코드에서는 세션 관리 필요)
//			    // String memberId = (String) request.getSession().getAttribute("MEMBERID");
//			    int memberno = ...; // 세션에서 사용자 번호를 가져온다고 가정
//			    String name = ...; // 세션에서 사용자 이름을 가져온다고 가정
//
//			    try (Connection conn = ConnectionProvider.getConnection()) {
//			        BoardDao dao = BoardDao.getInstance();
//			        // 현재 시간을 YYYY-MM-DD HH:MM:SS 형식으로 설정
//			        String regtime = dao.getCurrentTime();
//			        
//			        Board board = new Board(title, content, regtime, 0, memberno, name);
//			        dao.insert(conn, board);
//			        response.sendRedirect("Blist.do");
//			    } catch (Exception e) {
//			        e.printStackTrace();
//			        // 오류 처리
//			    }

		} else if (path.equals("/adminBview.do")) {
			int num = Integer.parseInt(request.getParameter("num")); // 게시물 번호를 파라미터로 받음
			Connection conn = ConnectionProvider.getConnection();
			BoardDao dao = BoardDao.getInstance();
			Board board = dao.selectOne(conn, num, true); // 게시물 조회
			request.setAttribute("board", board);
			RequestDispatcher dispatcher = request.getRequestDispatcher("adminBview.jsp"); // Bview.jsp로 포워딩
			dispatcher.forward(request, response);

		}else if (path.equals("/adminBdelete.do")) {
			int num = Integer.parseInt(request.getParameter("num")); // 삭제할 게시물 번호를 파라미터로 받음
			Connection conn = ConnectionProvider.getConnection();
			BoardDao dao = BoardDao.getInstance();
			dao.delete(conn, num); // 게시물 삭제
			response.sendRedirect("adminBlist.do"); // 리스트 페이지로 리다이렉트

		} else if (path.equals("/adminBlist.do")) {

			try (Connection conn = ConnectionProvider.getConnection()) {
				BoardDao dao = BoardDao.getInstance();
				ArrayList<Board> adminBlist = dao.selectList(conn);
				request.setAttribute("adminBlist", adminBlist);
				RequestDispatcher dispatcher = request.getRequestDispatcher("adminBlist.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				// 오류 처리
			}

		} else if (path.equals("/adminBupdate.do")) {
			int num = Integer.parseInt(request.getParameter("num")); // 수정할 게시물 번호를 파라미터로 받음
			Connection conn = ConnectionProvider.getConnection();
			BoardDao dao = BoardDao.getInstance();
			Board board = dao.selectOne(conn, num, false); // 게시물 조회(조회수 증가 X)
			request.setAttribute("board", board);
			RequestDispatcher dispatcher = request.getRequestDispatcher("adminBupdate.jsp"); // Bupdate.jsp로 포워딩
			dispatcher.forward(request, response);

		} else if (path.equals("/adminBwrite.do")) { // 이제 글쓰기페이지로 이동하는 역할만
			// Bwrite.jsp 페이지로 포워딩
			RequestDispatcher dispatcher = request.getRequestDispatcher("adminBwrite.jsp");
			dispatcher.forward(request, response);
		} else if (path.equals("/adminBinsert.do")) {
		    String title = request.getParameter("title");
		    String content = request.getParameter("content");
		    HttpSession session = request.getSession();
		    Member member = (Member)session.getAttribute("MEMBER"); // 세션에서 Member 객체 가져오기
		    
		    if (member != null) {
		        Board board = new Board();
		        board.setTitle(title);
		        board.setContent(content);
		        board.setMemberno(member.getMemberno()); // Member 객체에서 사용자 번호 사용
		        
		        BoardDao dao = BoardDao.getInstance();
		        try (Connection conn = ConnectionProvider.getConnection()) {
		            dao.insert(conn, board);
		            response.sendRedirect("adminBlist.do"); // 성공 시 게시글 목록으로 리다이렉션
		        }
		    } else {
		        // 사용자 정보가 세션에 없는 경우 (로그인하지 않은 상태)
		        response.sendRedirect("adminloginForm.jsp");
		    }
		}
			else {
			System.out.println("test");
		}

	}

}

//package servlet;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import mvjsp.board.dao.BoardDao;
//import mvjsp.board.dao.MemberDao;
//import mvjsp.board.model.Board;
//import mvjsp.board.model.Member;
//import mvjsp.jdbc.connection.ConnectionProvider;
//
///**
// * Servlet implementation class Controller
// */
//@WebServlet("*.do")
//public class Controller extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public Controller() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//			try {
//				process(request, response);
//			} catch (SQLException | ServletException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		try {
//			process(request, response);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	private void process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
//		String uri = request.getRequestURI();
//		System.out.println(uri);
//		String path = uri.substring(uri.lastIndexOf("/"));
//		System.out.println(path);
//		if (path.equals("/list.do")) { 
//			Connection conn = ConnectionProvider.getConnection();
//			MemberDao dao = MemberDao.getInstance();
//			ArrayList<Member> Mlist = dao.selectAll(conn);
//			request.setAttribute("Mlist", Mlist);
//			RequestDispatcher dispatcher 
//			         = request.getRequestDispatcher("list_view.jsp");
//			dispatcher.forward(request, response);
//		}else if(path.equals("/memberForm.do")) {
//			response.sendRedirect("memberForm.jsp");
//		}else if(path.equals("/memberInput.do")) {
//			String id = request.getParameter("id");
//			String email = request.getParameter("email");
//			String name = request.getParameter("name");
//
//			Connection conn = ConnectionProvider.getConnection();
//			MemberDao dao = MemberDao.getInstance();
//			Member member = new Member(id,email,name);
//			dao.insert(conn,member);
//			response.sendRedirect("list.do");
//		}else if(path.equals("updateForm.do")) {
//			
//			String mno = request.getParameter("memberno"); 
//			System.out.println(mno);
//			Connection conn = ConnectionProvider.getConnection();
//			MemberDao dao = MemberDao.getInstance();
//
//			Member member = dao.select(conn, Integer.parseInt(mno));
//			request.setAttribute("member", member);
//			
//			RequestDispatcher dispatcher 
//	         = request.getRequestDispatcher("updateForm_view.jsp");
//				dispatcher.forward(request, response);
//		}else if (path.equals("/memberUpdate.do")) {
//			String mno = request.getParameter("memberno");
//			String id = request.getParameter("id");
//			String email = request.getParameter("email");
//			String name = request.getParameter("name");
//			
//			Connection conn = ConnectionProvider.getConnection();
//			MemberDao dao = MemberDao.getInstance();
//			Member member = new Member(Integer.parseInt(mno), id, email, name);
//			dao.update(conn, member);
//			response.sendRedirect("list.jsp");
//			
//		} else if (path.equals("/memberDelete.do")) {
//			String memberno = request.getParameter("memberno");
//			Connection conn = ConnectionProvider.getConnection();
//			MemberDao dao = MemberDao.getInstance();
//			dao.delete(conn, Integer.parseInt(memberno));
//			response.sendRedirect("list.jsp");
//			
//		} else if (path.equals("/Bview.do")) {
//		    int num = Integer.parseInt(request.getParameter("num")); // 게시물 번호를 파라미터로 받음
//		    Connection conn = ConnectionProvider.getConnection();
//		    BoardDao dao = BoardDao.getInstance();
//		    Board board = dao.selectOne(conn, num, true); // 게시물 조회
//		    request.setAttribute("board", board);
//		    RequestDispatcher dispatcher = request.getRequestDispatcher("Bview.jsp"); // Bview.jsp로 포워딩
//		    dispatcher.forward(request, response);
//		    
//		} else if (path.equals("/Bdelete.do")) {
//		    int num = Integer.parseInt(request.getParameter("num")); // 삭제할 게시물 번호를 파라미터로 받음
//		    Connection conn = ConnectionProvider.getConnection();
//		    BoardDao dao = BoardDao.getInstance();
//		    dao.delete(conn, num); // 게시물 삭제
//		    response.sendRedirect("Blist.do"); // 리스트 페이지로 리다이렉트
//		    
//		} else if (path.equals("/Blist.do")) {
////		    Connection conn = ConnectionProvider.getConnection();
////		    BoardDao dao = BoardDao.getInstance();
////		    ArrayList<Board> Blist = dao.selectList(conn); // 전체 게시물 리스트 조회
////		    request.setAttribute("Blist", Blist);
////		    RequestDispatcher dispatcher = request.getRequestDispatcher("Blist.jsp"); // Blist.jsp로 포워딩
////		    dispatcher.forward(request, response);
//			 try (Connection conn = ConnectionProvider.getConnection()) {
//			        BoardDao dao = BoardDao.getInstance();
//			        ArrayList<Board> Blist = dao.selectList(conn);
//			        request.setAttribute("Blist", Blist);
//			        RequestDispatcher dispatcher = request.getRequestDispatcher("Blist.jsp");
//			        dispatcher.forward(request, response);
//			    } catch (Exception e) {
//			        e.printStackTrace();
//			        // 오류 처리
//			    }
//			 
//		} else if (path.equals("/Bupdate.do")) {
//		    int num = Integer.parseInt(request.getParameter("num")); // 수정할 게시물 번호를 파라미터로 받음
//		    Connection conn = ConnectionProvider.getConnection();
//		    BoardDao dao = BoardDao.getInstance();
//		    Board board = dao.selectOne(conn, num, false); // 게시물 조회(조회수 증가 X)
//		    request.setAttribute("board", board);
//		    RequestDispatcher dispatcher = request.getRequestDispatcher("Bupdate.jsp"); // Bupdate.jsp로 포워딩
//		    dispatcher.forward(request, response);
//		    
//		} else if (path.equals("/Bwrite.do")) {   //이제 글쓰기페이지로 이동하는 역할만
//		    // Bwrite.jsp 페이지로 포워딩
//		    RequestDispatcher dispatcher = request.getRequestDispatcher("Bwrite.jsp");
//		    dispatcher.forward(request, response);
//		}
////		    String title = request.getParameter("title");
////		    String content = request.getParameter("content");
////		    String regtime = ""; // 등록일시는 현재 시간으로 설정해야 함
////		    int hits = 0; // 조회수는 0으로 초기화
////		    String memberId = (String) request.getSession().getAttribute("MEMBERID"); // 세션에서 회원 아이디 가져오기
////
////		    // 세션에서 회원번호와 이름 가져오기
////		    int memberno = (int) request.getSession().getAttribute("MEMBERNO");
////		    String name = (String) request.getSession().getAttribute("NAME");
////
////		    Connection conn = ConnectionProvider.getConnection();
////		    BoardDao dao = BoardDao.getInstance();
////		    Board board = new Board(title, content, regtime, hits, memberno, memberId, name);
////
////		    dao.insert(conn, board); // 게시물 등록
////		    response.sendRedirect("Blist.do"); // 리스트 페이지로 리다이렉트
//		
////		else if (path.equals("/Binsert.do")) {  세션사용시
////		    String title = request.getParameter("title");
////		    String content = request.getParameter("content");
////		    
////		    // 실제로는 세션에서 사용자 정보를 가져와야 함
////		    // 예시 코드에서는 사용자 번호와 이름을 하드코딩한 것이므로 실제 환경에서는 세션을 이용하여 사용자 정보를 가져와야 함
////		    int memberno = 1; // 세션에서 사용자 번호를 가져오도록 수정해야 함
////		    String name = "test"; // 세션에서 사용자 이름을 가져오도록 수정해야 함
////
////		    try (Connection conn = ConnectionProvider.getConnection()) {
////		        BoardDao dao = BoardDao.getInstance();
////		        Board board = new Board();
////		        board.setTitle(title);
////		        board.setContent(content);
////		        board.setRegtime(dao.getCurrentTime()); // 현재 시간 설정
////		        board.setHits(0); // 새 게시글 조회수는 0
////		        board.setMemberno(memberno); // 세션에서 가져온 사용자 ID
////		        board.setName(name); // 세션에서 가져온 사용자 이름
////
////		        dao.insert(conn, board);
////		        response.sendRedirect("Blist.do"); // 게시글 목록 페이지로 리다이렉트
////		    } catch (Exception e) {
////		        e.printStackTrace();
////		        response.sendRedirect("errorPage.jsp"); // 오류 페이지로 리다이렉트
////		    }
////		
//		
//		
//		 else if(path.equals("Binsert.do")){
//				    String title = request.getParameter("title");
//				    String content = request.getParameter("content");
//				    // 세션에서 사용자 정보를 가져오는 부분은 구현 상황에 따라 달라질 수 있습니다.
//				    // 예시 코드에서는 memberno와 name을 어떻게 처리할지에 대한 구체적인 구현이 필요합니다.
//				    String name = request.getParameter("name"); // 예시, 실제 구현에서는 세션에서 사용자 이름을 가져와야 할 수 있습니다.
//				    int memberno = 1; // 예시, 실제 구현에서는 세션에서 사용자 ID를 가져와야 합니다.
//
//				    try (Connection conn = ConnectionProvider.getConnection()) {
//				        BoardDao dao = BoardDao.getInstance();
//				        Board board = new Board();
//				        board.setTitle(title);
//				        board.setContent(content);
//				        board.setRegtime(dao.getCurrentTime()); // 현재 시간 설정
//				        board.setHits(0); // 새 게시글 조회수는 0
//				        board.setMemberno(memberno); // 세션에서 가져온 사용자 ID
//				        board.setName(name); // 세션에서 가져온 사용자 이름
//
//				        dao.insert(conn, board);
//				        response.sendRedirect("Blist.do"); // 게시글 목록 페이지로 리다이렉트
//				    } catch (Exception e) {
//				        e.printStackTrace();
//				        response.sendRedirect("errorPage.jsp"); // 오류 페이지로 리다이렉트
//				    }
//				
//			 
//			 
//			 
////			 String title = request.getParameter("title");
////			    String content = request.getParameter("content");
////			    // 세션에서 사용자 정보 가져오기 (예시, 실제 코드에서는 세션 관리 필요)
////			    // String memberId = (String) request.getSession().getAttribute("MEMBERID");
////			    int memberno = ...; // 세션에서 사용자 번호를 가져온다고 가정
////			    String name = ...; // 세션에서 사용자 이름을 가져온다고 가정
////
////			    try (Connection conn = ConnectionProvider.getConnection()) {
////			        BoardDao dao = BoardDao.getInstance();
////			        // 현재 시간을 YYYY-MM-DD HH:MM:SS 형식으로 설정
////			        String regtime = dao.getCurrentTime();
////			        
////			        Board board = new Board(title, content, regtime, 0, memberno, name);
////			        dao.insert(conn, board);
////			        response.sendRedirect("Blist.do");
////			    } catch (Exception e) {
////			        e.printStackTrace();
////			        // 오류 처리
////			    }
//			
//			
//		}else {
//			System.out.println("test");
//		}
//		
//	}
//
//}