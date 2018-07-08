package com.mql.redhope.models;

import java.time.LocalDateTime;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author mehdithe
 */
@Entity
public class EventPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  private LocalDateTime createdAt;

  public EventPost() {
    createdAt = LocalDateTime.now();
  }

  public EventPost(String content) {
    this();
    this.content = content;
  }

  public static EventPost of(String content) {
    return new EventPost(content);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "EventPost{" +
        "id=" + id +
        ", content='" + content + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
