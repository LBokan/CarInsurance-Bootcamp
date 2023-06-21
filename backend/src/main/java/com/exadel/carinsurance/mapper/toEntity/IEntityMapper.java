package com.exadel.carinsurance.mapper.toEntity;

public interface IEntityMapper<EntityClass, RequestClass> {
  EntityClass toEntity( RequestClass request );
}
