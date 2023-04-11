package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.controller.dto.UserListDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

import static kr.codesqaud.cafe.util.DateUtil.getCurrentDate;

public class User {

    private String nickName;
    private String email;
    private String password;
    private String id;
    private String date;

    public User(String nickName, String email, String password,String id) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.id = id;
        this.date = getCurrentDate();
    }

    public User(ResultSet rs) throws SQLException {
        this.nickName = rs.getString("nickName");
        this.email = rs.getString("email");
        this.password = rs.getString("password");
        this.id = rs.getString("id");
        this.date = rs.getString("date");
    }

    public String getDate() {
        return date;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }

    public UserDTO toUserDTO(){
        return new UserDTO(nickName,email,password,id);
    }

    public UserListDTO toUserListDTO() {
        return new UserListDTO(nickName,email,id,date);
    }

}