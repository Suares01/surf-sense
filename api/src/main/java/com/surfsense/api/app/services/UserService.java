package com.surfsense.api.app.services;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public interface UserService {
  public void sendEmailVerification(String userId);

  public void updateEmail(String userId, String email);

  public void resetPassword(String email);

  public UserProfile updateUserRootAttributes(String userId, RootAttributes attributes);

  public static class RootAttributes implements Serializable {
    private String given_name;
    private String family_name;
    private String name;
    private String nickname;
    private String picture;

    public String getGiven_name() {
      return given_name;
    }

    public void setGiven_name(String given_name) {
      this.given_name = given_name;
    }

    public String getFamily_name() {
      return family_name;
    }

    public void setFamily_name(String family_name) {
      this.family_name = family_name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public String getPicture() {
      return picture;
    }

    public void setPicture(String picture) {
      this.picture = picture;
    }
  }

  public static class Identity implements Serializable {
    private String user_id;
    private String provider;
    private String connection;
    private boolean isSocial;

    public String getUser_id() {
      return user_id;
    }

    public void setUser_id(String user_id) {
      this.user_id = user_id;
    }

    public String getProvider() {
      return provider;
    }

    public void setProvider(String provider) {
      this.provider = provider;
    }

    public String getConnection() {
      return connection;
    }

    public void setConnection(String connection) {
      this.connection = connection;
    }

    public boolean isSocial() {
      return isSocial;
    }

    public void setSocial(boolean isSocial) {
      this.isSocial = isSocial;
    }
  }

  public static class UserProfile implements Serializable {
    private Instant created_at;
    private String email;
    private boolean email_verified;
    private List<Identity> identities;
    private String name;
    private String nickname;
    private String picture;
    private Instant updated_at;
    private String user_id;
    private String last_ip;
    private Instant last_login;
    private int logins_count;

    public Instant getCreated_at() {
      return created_at;
    }

    public void setCreated_at(Instant created_at) {
      this.created_at = created_at;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public boolean isEmail_verified() {
      return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
      this.email_verified = email_verified;
    }

    public List<Identity> getIdentities() {
      return identities;
    }

    public void setIdentities(List<Identity> identities) {
      this.identities = identities;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public String getPicture() {
      return picture;
    }

    public void setPicture(String picture) {
      this.picture = picture;
    }

    public Instant getUpdated_at() {
      return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
      this.updated_at = updated_at;
    }

    public String getUser_id() {
      return user_id;
    }

    public void setUser_id(String user_id) {
      this.user_id = user_id;
    }

    public String getLast_ip() {
      return last_ip;
    }

    public void setLast_ip(String last_ip) {
      this.last_ip = last_ip;
    }

    public Instant getLast_login() {
      return last_login;
    }

    public void setLast_login(Instant last_login) {
      this.last_login = last_login;
    }

    public int getLogins_count() {
      return logins_count;
    }

    public void setLogins_count(int logins_count) {
      this.logins_count = logins_count;
    }
  }
}
