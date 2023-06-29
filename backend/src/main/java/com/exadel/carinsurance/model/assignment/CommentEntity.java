package com.exadel.carinsurance.model.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "comments" )
public class CommentEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private Long id;

  @Column( name = "date_of_creation" )
  private LocalDateTime dateOfCreation;

  @Column( name = "text" )
  private String text;

  @Column( name = "is_read" )
  private int isRead;

  @Column( name = "assignment_id",
      nullable = false,
      insertable = false,
      updatable = false )
  private Long assignmentId;

  @JsonIgnore
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "assignment_id" )
  private AssignmentEntity assignment;

  @Override
  public String toString() {
    return "CommentEntity{" +
        "id=" + id +
        ", dateOfCreation=" + dateOfCreation +
        ", text='" + text + '\'' +
        ", isRead=" + isRead +
        ", assignmentId=" + assignmentId +
        '}';
  }
}
