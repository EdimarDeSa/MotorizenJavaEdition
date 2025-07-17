package com.efscode.motorizen_backend.models.backlog;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.efscode.motorizen_backend.models.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "backlog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackLogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false, updatable = false, name = "backlog_event_type_id")
  private BackLogEventTypeEntity backlogEventType;

  @ManyToOne
  @JoinColumn(nullable = false, updatable = false, name = "user_id")
  private UserEntity user;

  @Column(nullable = false)
  private String description;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;
}
