package com.exadel.carinsurance.mapper.toEntity;

import com.exadel.carinsurance.model.assignment.CommentEntity;
import com.exadel.carinsurance.model.request.CommentRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentEntityMapper implements IEntityMapper<CommentEntity, CommentRequestEntity> {
  @Override
  public CommentEntity toEntity( CommentRequestEntity request ) {
    return CommentEntity
        .builder()
        .text( request.getText() )
        .isRead( 0 )
        .build();
  }
}
