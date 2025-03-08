package com.kh.mvc.controller;

import com.kh.mvc.model.dao.UserDAO;
import com.kh.mvc.model.dto.UserDTO;
import com.kh.mvc.model.service.MemberService;

import java.util.List;
import java.util.Map;

/**
 * View에서 온 요청을 처리해주는 클래스입니다.
 * 메서드로 전달된 데이터값을 가공처리한 후 DAO로 전달합니다.
 * DAO로부터 반환받은 결과를 사용자가 보게될 View(응답화면)에 반환합니다.
 */
public class UserController {

  private UserDAO userDAO = new UserDAO();

  // 나중에 service하면서 적은거
  private MemberService userService = new MemberService();

  public List<UserDTO> findAll(){
    //List<UserDTO> list = userDAO.findAll();
    // 들고온 이유? view에 보내주기 위함

    // 컨트롤럴와 DAO 사이에 중간다리를 만들어 주겠다
    return userService.findAll();
  }

  public int insertUser(String userId, String userPw, String userName) {
    // requestParameter 매개변수
    // userView에 있는 insertUser에 담은 주소값을 담는 변수

    // DAO에 값을 덤김
    // userDAO.insertUSer(userId, userPw, userName); => 근데 값을 한 군데에 담아서 보내고파
    UserDTO user = new UserDTO();
    user.setUserId(userId);
    user.setUserPw(userPw);
    user.setUserName(userName);

    int result = userService.insertUser(user);
    user = null; // GC가 해주겠지만 훌륭한 개발자라면...
    return result;
  }

  public int updatePw(String id, String currentPw, String newPw){
    UserDTO user = new UserDTO();
    user.setUserId(id);
    user.setUserPw(currentPw);
    user.setNewPw(newPw);

    int result = userService.updatePw(user);
    return result;

  }

  public int deleteUser(String userId, String userPw){
    UserDTO user = new UserDTO();
    user.setUserId(userId);
    user.setUserPw(userPw);

    int result = userDAO.deleteUser(user);

    return result;
  }

/*  public UserDTO findUser(String id){
    UserDTO user = new UserDTO();
    user.setUserId(id);
    return user;
  }*/




}
