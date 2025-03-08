package com.kh.mvc.model.dao;

import com.kh.mvc.model.dto.UserDTO;
import com.kh.mvc.util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO(Data Access Object)
 *
 * 데이터베이스 관련  된 작업(CRUD)을 전문적으로 담당하는 객체
 * DAO안에 모든 메소드들은 데이터베이스와 관련된 기능으로 만들 것
 *
 * Controller를 통해 호출된 기능을 수행
 * DB에 직접 접근한 후 SQL문을 수행하고 결과 받기(JDBC 필요)
 * 따라서 JDBC 관련 작업은 DAO에서만 함
 */
public class UserDAO {
  /*
   *  JDBC 용 객체
   * - Connection : DB와의 연결정보를 담고있는 객체(IP주소, Port, 사용자명, 비번)
   *     -> 디비거에서 connect to a database 하는 것
   * - Statement : Connection이 가지고 있는 연결정보 DB에
   *               SQL문을 전달하고 실행하고 결과도 받아오는 객체
   *     -> 디비거에서 script 여는 것
   * - ResultSet : 실행한 SQL문이 SELECT문일 경우 조회된 결과가 처음 담기는 객체
   *               select는 ResultSet이 결과로 나오고
   *               나머지는 행의 개수(=정수)로 나와
   *
   * - PreparedStatement : SQL문을 미리 준비하는 개념
   *                       ?(위치홀더)로 확보해놓은 공간을 사용자가 입력한 값들과 바인딩해서 SQL문을 수행
   *
   * ** 처리 절차
   * 1) JDBC Driver 등록 : 해당 DBMS에서 제공하는 클래스를 동적으로 등록, 프로그램 실행 시 딱 한번만 수행
   * 2) Connection 객체 생성 : 접속하고자 하는 DB의 정보를 입력해서 생성
   *                          (URL, 사용자이름, 비밀번호)
   * 3-1) PreparedStatement 객체 생성 : Connection 객체를 이용해서 생성
   *                                 Statement와 가장 큰 차이점은 미완성된 SQL문을 미리 전달
   * 3-2) 미완성된 SQL문을 완성 형태로 만들어주어야 함
   * => 미완성된 경우에만 해당 / 완성된 경우에는 생략
   * 4) SQL문을 실행 : executeXXX() => SQL을 인자로 전달하지 않음!!
   *                  > SELECT(DQL) : executeQuery()  <= SELECT 한다고 값이 바뀌지는 않음
   *                  > DML         : executeUpdate() <= 값이 바뀜
   *   SQL문을 실행하면 DB서버로 요청
   *   DB서버가 있는 컴퓨터의 실제 저장장치에 저장됨
   * 5) 결과 받기 :
   *               > SELECT : ResultSet타입 객체(조회데이터 담김)
   *               > DML    : int(처리된 행의 개수)
   * 조회된 결과를 들고 View까지 가야해 (행 * 열 개를 데이터를 갖고 가야해.. => return으로!)
   *
   * 6-1) ResultSet에 담겨있는 데이터들을 하나하나씩 뽑아서 DTO 객체 필드에
   *     옮겨담은 후 조회 결과가 여러 행일 경우 List로 관리
   * 6-2) 트랜잭션 처리
   *   EX) 계좌이체
   * 7)(생략될 수 있음) 자원 반납(close) => 생성의 역순으로!
   * 8) 결과 반환 -> Controller
   *             SELECT > 6_1에서 만든거
   *             DML    > 처리된 행의 개수
   * */

  private final String URL = "jdbc:oracle:thin:@112.221.156.34:12345:XE";
  private final String USERNAME = "KH22_JSW";
  private final String PASSWORD = "KH1234";

  // 공통으로 사용하기에 프로그램 실행 시
  // 딱 한번만 메모리에 할당되어
  // 모든 객체가 공유하기 위함
  static {

    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("ojdbc...잘 넣었나요?" + "\n 오타 없나요~?");
    }

  }

  public List<UserDTO> findAll(Connection conn) { // Connection 인터페이스

    // return 1,2 => return 값은 여러 개 담지 않어
    // DAO : Data Transform Object

    // DB가야지~~
    /*
     * vo / DTO / Entity
     *
     * 1명의 회원의 정보는 1개의 UserDTO객체의 필드에 값을 담아야겠다.
     *
     * 테이블의 한 행의 데이터를 담기위해 사용한다
     *
     * 문제점 : userDTO가 몇개가 나올지 알 수 없음
     * (여러 개의 정보를 담는것은 배열, 리스트,맵,set)
     * 배열은 할당 해야하는데 몇칸인지 몰라
     * 맵은 key를 알아야하는데 key를 몰라
     * 따라서 list의 ArrayList에 담자
     *
     * */

    List<UserDTO> list = new ArrayList();
    String sql = "SELECT"
            + " USER_NO"
            + ", USER_ID"
            + ", USER_PW"
            + ", USER_NAME"
            + ", ENROLL_DATE"
            + " FROM TB_USER "
            + "ORDER BY "
            + "ENROLL_DATE DESC";

    // 흐~~~~~ 이제야 이해가 가넹~~~~~~~~~~~~~~~
    // 헤헤헤헿 신난댜
    // 나는야 말하는 감쟈감쟈 감자감쟈감자
    // 흐흫어ㅓ엏


    // Connection conn = null;
    // 주석처리한 이유 => Study에서 설명

    PreparedStatement pstmt = null;
    ResultSet rset = null;

    // Class.forName("oracle.jdbc.driver.OracleDriver");
    // 어디에 위치하면 좋을까?
    // 위에 static 블록!!

    try {
      // 커넥션 객체를 여기다가 하고싶지 않어
      // conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

      pstmt = conn.prepareStatement(sql);
      // 5. 미완성 sql이 아니기에 바로 실행
      rset = pstmt.executeQuery();

      // 데이터가 있는지 확인
/*      if(rset.next()){
        // 조회 결과 컬럼 값을 DTO필드에 담는 작업 및 리스트에 요소로 추가
      }*/
/*      if(rset.next()){}
      if(rset.next()){}
      if(rset.next()){}
      => 중복 코드 => 몇번 돌지 몰라
      */
      while (rset.next()) {
        UserDTO user = new UserDTO();

        //user.setUserNo(rset.getInt(0)); // 컬럼 순번
        user.setUserNo(rset.getInt("USER_NO"));
        // 컬럼명으로 받는걸로 해야함
        //1. 다른 사람이 컬럼 순번을 보면 뭔지 모름
        //2. 컬럼 수정시 순번 바뀜

        user.setUserId(rset.getString("USER_ID"));
        user.setUserPw(rset.getString("USER_PW"));
        user.setUserName(rset.getString("USER_NAME"));
        user.setEnrollDate(rset.getDate("ENROLL_DATE"));
        // user는 지역변수로 블록단위 생명주기인데
        // 그러면!!! 블럭이 끝나면 사라지는거 아니냐?

        list.add(user);
        // 이렇게하면 안날아감
        // 왜? 힙에있는 객체가 계속 살아남으려면 누군가가 얘를 참조하고 있어야 함
        // ArrayList는 10칸 짜리 배열인데 블록이 끝나기전에 누군가가 가리키고있기에
        // 객체가 생성되어 있기에 안날라간당


      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("오타가 나지 않았나요?? 확인 하셨나요?? 두 번 봤나요??");
    } /*finally {*/
    // 사실...finally가 의미 있으려면 return구문..
    // 따라서 꼭 필요하지 않어
    // 기계식 개발자가 되지 말 것!!!!!!!!!!!!!🔥🔥🔥🔥🔥


    // 생성한것 역순으로 만들어유~
/*    try {
      if (rset != null) {
        // null일때 수행하면 nullpointerException이 일어남
        rset.close();
      }
    } catch (SQLException e) {
      System.out.println("몰라 DB서버 이상해");
    }
//     try{
//        if(pstmt != null){
//          pstmt.close();
//        }
//      } catch (SQLException e) {
//        System.out.println("PreparedStment 이상해요~");
//      }

    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }*/

    /*}*/finally{
      JdbcUtil.close(rset);
      JdbcUtil.close(pstmt);
      JdbcUtil.close(conn);
    }


    return list;

  }

  /**
   * @param user 사용자가 입력한 아이디/비밀번호/이름이 각각 필드에 대입되어있음
   * @return 아직 뭐 돌려줄지 안정함
   */
  public int insertUser(Connection conn, UserDTO user) {

    PreparedStatement pstmt = null;

    String sql = "INSERT "
            + "INTO TB_USER "
            + "VALUES "
            + "("
            + "SEQ_USER_NO.NEXTVAL"
            + ", ?, ?, ?"
            + ", sysdate"
            + ")";

    int result = 0;

    try {

      pstmt = conn.prepareStatement(sql);

      // 미완성 SQL -> 바인딩
      pstmt.setString(1, user.getUserId()); // 컬럼의 위치랑 인덱스랑 맞춰야 함
      pstmt.setString(2, user.getUserPw());
      pstmt.setString(3, user.getUserName());

      // SQL 실행
      result = pstmt.executeUpdate();


    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.close(pstmt);
      JdbcUtil.close(conn);
    }
    return result;
  }

  /**
   * 비밀번호 수정하기(사용자가 입력한 아이디/현재 비밀번호가 각각 필드에 대입되어있음
   *
   * @param user
   * @return result : 정수로 반환
   */
  public int updatePw(Connection conn, UserDTO user) {

    // Connection conn = null;
    PreparedStatement pstmt = null;


    String sql = """
				 UPDATE TB_USER SET
						USER_PW = ?
					   
         WHERE
					  USER_ID = ?
				 AND
						USER_PW = ?
					""";

    int result = 0;

    try{

      pstmt = conn.prepareStatement(sql);

      // 선생님 tip
      // 잘 안나오면 찍어봐라
      System.out.println(user.getUserId());
      System.out.println(user.getUserPw());
      System.out.println(user.getNewPw());

      // 미완성 SQL -> 바인딩
      pstmt.setString(1,user.getNewPw());
      pstmt.setString(2,user.getUserId());
      pstmt.setString(3,user.getUserPw());

      // SQL 실행
      result = pstmt.executeUpdate();

      return result;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      JdbcUtil.close(pstmt);
      JdbcUtil.close(conn);
    }

  }

  public int deleteUser(Connection conn, UserDTO user) {

    PreparedStatement pstmt = null;

    String sql
            =
            """
                DELETE FROM TB_USER
                WHERE USER_ID = ?
                AND USER_PW = ?

            """;

    int result = 0;

    try {

      pstmt = conn.prepareStatement(sql);

      System.out.println(user.getUserId());
      System.out.println(user.getUserPw());

      pstmt.setString(1, user.getUserId());
      pstmt.setString(2, user.getUserPw());

      result = pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.close(pstmt);
      JdbcUtil.close(conn);
    }
    return result;
  }

  public UserDTO findUser(Connection conn, UserDTO user) {
    List<UserDTO> list = new ArrayList();

    PreparedStatement pstmt = null;
    ResultSet rset = null;

    String sql
            =
            """
            SELECT
              ?, ?, ?, ?, ?
            FROM TB_USER
            WHERE USER_ID = ?
            """;

    try{
      pstmt = conn.prepareStatement(sql);

      while(rset.next()) {
        pstmt.setInt(1, user.getUserNo());
        pstmt.setString(2, user.getUserId());
        pstmt.setString(3, user.getUserPw());
        pstmt.setString(4, user.getUserName());
        pstmt.setDate(5, new java.sql.Date(user.getEnrollDate().getTime()));
        pstmt.setInt(6, user.getUserNo());

        list.add(user);

      }

      rset = pstmt.executeQuery();

    } catch (SQLException e) {
      e.printStackTrace();
    }finally{
      JdbcUtil.close(rset);
      JdbcUtil.close(pstmt);
    }
    return list.get(0);
  }








}
  
  
  
  
  

