package com.exadel.carinsurance.model.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "assignment_statuses" )
public class AssignmentStatusEntity {
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Column( name = "id" )
  private int id;

  @Enumerated( EnumType.STRING )
  @Column( name = "name" )
  private EAssignmentStatusEntity name;

  @JsonIgnore
  @OneToMany( fetch = FetchType.LAZY,
      mappedBy = "status",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  private List<AssignmentEntity> assignments;

  @Override
  public String toString() {
    return "AssignmentStatusEntity{" +
        "id=" + id +
        ", name=" + name +
        '}';
  }
}
