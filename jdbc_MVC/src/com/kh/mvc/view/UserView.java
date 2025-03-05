package com.kh.mvc.view;

import com.kh.mvc.controller.UserController;
import com.kh.mvc.model.dto.UserDTO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * MemberView 클래스는 JDBC실습을 위해 생성하였으며,
 * 사용자에게 입력 및 출력을 수행하는 메소드를 제공합니다.
 *
 * @author : 종로 C강의장
 * @version : 0.1
 * @date : 2025-03-04
 */
public class UserView {

  private Scanner sc = new Scanner(System.in);
  private UserController userController = new UserController(); // 이거 쓰기위해 필드로 놓음

  /**
   * 프로그램 시작 시 사용자에게 보여줄 메인화면을 출력해주는 메소드입니다.
   */
  public void mainMenu(){

    while(true){
      System.out.println("---USER 테이블 관리 프로그램---");
      System.out.println("1. 회원 전체 조회");
      System.out.println("2. 회원 추가"); // 값이 5개 필요함 => 값 입력할 수 있도록 유도 필요
      System.out.println("9. 프로그램 종료");
      System.out.print("이용할 메뉴 선택해주세요 > ");

      int menuNo = 0;
      try{ // 예외처리
        menuNo = sc.nextInt();
      } catch(InputMismatchException e){
        sc.nextLine();
        continue;
      }

      // menuNo = sc.nextInt();
      sc.nextLine(); // 입력 버퍼에 남은거 제거?

      switch(menuNo){
        case 1:
          findAll();
          break;
        case 2:
          insertUser();
          break;
        case 9:
          System.out.println("프로그램 종료~👻");
          return;
        default :
          System.out.println("잘못된 메뉴 선택입니다.");
      }

    }

  }

  // 회원 전체 정보를 조회해주는 기능
  private void findAll(){

    System.out.println("\n--- 회원 전체 목록입니당 ---");
    // 이제 DB서버 가야해 => userController(중간다리 역할)
    // => 일단 위에 필드 선언

    // Controller야~ 쩌어기 DB가서 회원 전체 목록 좀 가져와줘
    //userController.findAll();
    List<UserDTO> list = userController.findAll();

    System.out.println("\n조회된 총 회원의 수는 " + list.size() + " 명 입니다.");

    /*조건식 사용법*/
    if(!(list.isEmpty())){ // == list.size()

      System.out.println("================================");
      for(UserDTO user : list){
        System.out.print(user.getUserName() + "님의 정보 !");
        System.out.print("\n아이디 : " + user.getUserId());
        System.out.print("\t가입일 : " + user.getEnrollDate());
        System.out.println();
      }
      System.out.println("================================");

    } else{
      System.out.println("회원이 존재하지 않습니다.");
    }

  }

  /**
   * TB_USER에 INSERT할 값을 사용자에게 입력받도록 유도하는 화면
   */
  private void insertUser(){

    System.out.println("--- 회원 추가 페이지 입니다 ---");
    System.out.print("아이디를 입력하세요 > ");
    String userId = sc.nextLine();
/*    while(true){
      String userId = sc.nextLine();
      // unique 했다고 치고 입력받은 아이디 가지고
      // DB가서 WHERE조건절에다가 사용자가 입력한 아이디 넣어서
      // 조회 결과있으면 혼쭐내주기

      *//*if(조회결과 중복 없음){
        SELECT USER_ID FROM TB_USESR WHERE USER_ID = 사용자가 입력한 아이디 값
                break;
      }
      System.out.println("중복된 아이디가 존재합니다. 다른 아이디름 입력해주세요!");*//*

      if(userId.length() > 30){
        System.out.println("아이디는 30자 이내로 입력해주세요.");
      }
    }*/
    System.out.print("비밀번호를 입력하세요 > ");
    String userPw = sc.nextLine(); // userPw
    System.out.print("이름을 입력하세요 > ");
    String userName = sc.nextLine();

    int result = userController.insertUser(userId, userPw, userName); // String이므로 String pool에 있는 주소값을 넘김

    if(result > 0){
      System.out.println(userName + "님 가입에 성공하셨습니다!");
    } else{
      System.out.println("회원 추가에 실패했습니다. 다시 시도해주세요~");
    }



  }





}
