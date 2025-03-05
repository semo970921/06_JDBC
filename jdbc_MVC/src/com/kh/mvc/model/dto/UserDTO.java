package com.kh.mvc.model.dto;

import java.util.Date;

public class UserDTO { // ResultSet에서 한 행을 담는 것
  private int userNo;

  private String userId;
  private String userName;
  private String userPw;
  private Date enrollDate;

  public UserDTO() {
    super();
  }

  public UserDTO(Date enrollDate, String password, String userId, String userName, int userNo) {
    this.enrollDate = enrollDate;
    this.userPw = password;
    this.userId = userId;
    this.userName = userName;
    this.userNo = userNo;
  }

  public Date getEnrollDate() {
    return enrollDate;
  }

  public void setEnrollDate(Date enrollDate) {
    this.enrollDate = enrollDate;
  }

  public String getUserPw() {
    return userPw;
  }

  public void setUserPw(String userPw) {
    this.userPw = userPw;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getUserNo() {
    return userNo;
  }

  public void setUserNo(int userNo) {
    this.userNo = userNo;
  }
}
