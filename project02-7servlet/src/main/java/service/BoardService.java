//package service;
//
//import java.util.ArrayList;
//
//import mvjsp.board.dao.BoardDao;
//import mvjsp.board.model.Board;
//
//
//public class BoardService {
//
//    private static final int listSize = 3; // 한 페이지에 표시될 게시글 수
//    private static final int paginationSize = 3; // 페이네이션에 표시될 페이지 수
//
//    // 주어진 페이지 번호에 해당하는 게시글 목록을 가져오는 메서드
//    public ArrayList<Board> getMsgList(int pageNo) {
//       return new BoardDao().selectList((pageNo - 1) * listSize, listSize);
//    } // MySQL에서는 LIMIT를 사용하여 페이징 처리
//
//    // 페이네이션을 생성하는 메서드
//    public ArrayList<Pagination> getPagination(int pageNo) {
//
//        ArrayList<Pagination> pgnList = new ArrayList<Pagination>();
//
//        int numRecords = new BoardDao().getNumRecords(); // 전체 게시글 수 가져오기
//        int numPages = (int)Math.ceil((double)numRecords / listSize); // 전체 페이지 수 계산
//
//        int firstLink = ((pageNo - 1) / paginationSize)
//                        * paginationSize + 1; // 페이네이션의 첫 번째 링크 계산
//        int lastLink = firstLink + paginationSize - 1; // 페이네이션의 마지막 링크 계산
//        if (lastLink > numPages) { // 마지막 링크가 전체 페이지 수보다 크면 조정
//            lastLink = numPages;
//        }
//
//        if (firstLink > 1) { // 이전 링크 추가
//            pgnList.add(
//                   new Pagination("이전", pageNo - paginationSize, false));
//        }
//
//        for (int i = firstLink; i <= lastLink; i++) { // 중간 페이지 링크 추가
//            pgnList.add(new Pagination("" + i, i, i == pageNo));
//        }
//
//        if (lastLink < numPages) { // 다음 링크 추가
//            int tmpPageNo = pageNo + paginationSize;
//            if (tmpPageNo > numPages) { // 다음 링크의 페이지 번호가 전체 페이지 수를 넘지 않도록 조정
//                tmpPageNo = numPages;
//            }
//            pgnList.add(new Pagination("다음", tmpPageNo, false));
//        }
//
//        return pgnList;
//    }
//
//    // 주어진 게시글 번호에 해당하는 게시글을 가져오는 메서드
//    public BoardDto getMsg(int num) {
//        BoardDto dto = new BoardDao().selectOne(num, true); // 게시글 조회
//
//        // HTML 특수 문자를 치환하여 내용을 가공
//        dto.setTitle(dto.getTitle().replace (" ",  "&nbsp;"));
//        dto.setContent(dto.getContent().replace(" ",  "&nbsp;")
//                                       .replace("\n", "<br>"));
//
//        return dto;
//    }
//
//    // 게시글 작성을 위한 빈 게시글을 가져오는 메서드
//    public BoardDto getMsgForWrite(int num) {
//        return new BoardDao().selectOne(num, false); // 게시글 작성을 위한 빈 게시글 조회
//    }
//
//    // 게시글을 작성하는 메서드
//   // public void writeMsg(String writer, String title, String content)
//     public void writeMsg(String writer, String title, String content,int memberno)
//            throws Exception {
//
//        // 작성자, 제목, 내용이 비어있는지 확인
//        if (writer  == null || writer.length()  == 0 ||
//            title   == null || title.length()   == 0 ||
//            content == null || content.length() == 0) {
//
//           throw new Exception("모든 항목이 빈칸 없이 입력되어야 합니다.");
//        }
//
//        // 게시글 DTO 객체 생성
//        BoardDto dto = new BoardDto();
//        dto.setWriter (writer );
//        dto.setTitle  (title  );
//        dto.setContent(content);
//        dto.setMemberno(memberno); //추가
//
//        // 게시글 데이터베이스에 삽입
//        new BoardDao().insertOne(dto);
//    }
//
//    // 게시글을 수정하는 메서드
//    public void updateMsg(
//            String writer, String title, String content, int num)
//                    throws Exception {
//
//        // 작성자, 제목, 내용이 비어있는지 확인
//        if (writer  == null || writer.length()  == 0 ||
//            title   == null || title.length()   == 0 ||
//            content == null || content.length() == 0) {
//
//           throw new Exception("모든 항목이 빈칸 없이 입력되어야 합니다.");
//        }
//
//        // 게시글 DTO 객체 생성
//        BoardDto dto = new BoardDto();
//        dto.setNum    (num    );
//        dto.setWriter (writer );
//        dto.setTitle  (title  );
//        dto.setContent(content);
//
//        // 게시글 데이터베이스 업데이트
//        new BoardDao().updateOne(dto);
//    }
//
//    // 게시글을 삭제하는 메서드
//    public void deleteMsg(int num) {
//        new BoardDao().deleteOne(num); // 게시글 데이터베이스에서 삭제
//    }
//}