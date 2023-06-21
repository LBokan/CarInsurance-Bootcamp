package com.exadel.carinsurance.mapper.toResponse;

public interface IResponseMapper<EntityClass, ResponseClass> {
  ResponseClass toResponse( EntityClass entity );
}
