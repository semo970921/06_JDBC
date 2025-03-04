package com.kh.mvc.model.dao;

import com.kh.mvc.model.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO(Data Access Object)
 *
 * 데이터베이스 관련된 작업(CRUD)을 전문적으로 담당하는 객체
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

  public List<UserDTO> findAll(){

    // return 1,2 => 여러개 담지 않어
    // DAO : Data Transform Object

    // DB가야지~~
    /*
    * vo / DTO / Entity
    *
    * 1명의 회원의 정보는 1개의 UserDTO객체의 필드에 값을 담아야겠다.
    *
    * 문제점 : userDTO가 몇개가 나올지 알 수 없음
    * (여러 개의 정보를 담는것은 배열, 리스트,맵,set)
    * 배열은 할당 해야하는데 몇칸인지 몰라
    * 맵은 key를 알아야하는데 key를 몰라
    * 따라서 list의 ArrayList에 담자
    *
    * */

    List<UserDTO> list = new ArrayList<UserDTO>();
    String sql = "SELECT "
                    + "USER_NO"
                    +", USER_ID"
                    +", USER_PW"
                    +", USER_NAME"
                    +", ENROLL_DATE"
                +"FROM TB_USER "
                +"ORDER BY "
                      +"ENROLL_DATE DESC";


    return list;

  }

}
