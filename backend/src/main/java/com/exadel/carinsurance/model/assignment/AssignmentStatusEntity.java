package com.exadel.carinsurance.model.assignment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
  @Column( name = "status_id" )
  private int statusId;

  @Enumerated( EnumType.STRING )
  @Column( name = "name" )
  private EAssignmentStatusEntity name;

  @JsonIgnore
  @OneToMany( fetch = FetchType.EAGER,
      mappedBy = "status",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE,
          CascadeType.DETACH, CascadeType.REFRESH } )
  @Fetch( value = FetchMode.SUBSELECT )
  private List<AssignmentEntity> assignments;

  @Override
  public String toString() {
    return "AssignmentStatusEntity{" +
        "statusId=" + statusId +
        ", name=" + name +
        '}';
  }
}
