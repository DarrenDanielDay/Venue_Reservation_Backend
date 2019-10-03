package cn.ecnuer996.bean;

import org.apache.taglibs.standard.extra.spath.Step;

public class User {
    private Integer id;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    public User() {

    }

    public User(String nickname, String avatar, String phone,String email) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}