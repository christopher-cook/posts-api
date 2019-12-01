package com.example.postsapi.model;

public class Comment {
  private Long id;

  private String text;

  private Long user_id;

  private User user;

  public Comment() {}

  public Comment (Long id, String text, Long user_id) {
    this.id = id;
    this.text = text;
    this.user_id = user_id;
  };

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
