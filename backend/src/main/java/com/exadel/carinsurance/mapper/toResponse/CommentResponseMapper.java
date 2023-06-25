package com.exadel.carinsurance.mapper.toResponse;

import com.exadel.carinsurance.model.assignment.CommentEntity;
import com.exadel.carinsurance.model.response.CommentResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentResponseMapper implements IResponseMapper<CommentEntity, CommentResponseEntity> {
  @Override
  public CommentResponseEntity toResponse( CommentEntity entity ) {
    return CommentResponseEntity
        .builder()
        .id( entity.getId() )
        .dateOfCreation( entity.getDateOfCreation() )
        .text( entity.getText() )
        .isRead( entity.getIsRead() )
        .build();
  }
}
