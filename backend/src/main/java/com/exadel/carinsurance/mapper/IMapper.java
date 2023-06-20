package com.exadel.carinsurance.mapper;

public interface IMapper<EntityClass, RequestClass, ResponseClass> {
  EntityClass toEntity( RequestClass request );

  ResponseClass toResponse( EntityClass entity );
}
