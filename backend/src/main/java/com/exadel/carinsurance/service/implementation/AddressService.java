package com.exadel.carinsurance.service.implementation;

import com.exadel.carinsurance.exceptions.NotFoundException;
import com.exadel.carinsurance.mapper.toEntity.AddressEntityMapper;
import com.exadel.carinsurance.mapper.toResponse.AddressResponseMapper;
import com.exadel.carinsurance.model.assignment.AddressEntity;
import com.exadel.carinsurance.model.assignment.AddressTypeEntity;
import com.exadel.carinsurance.model.assignment.ContactInfoEntity;
import com.exadel.carinsurance.model.request.AddressRequestEntity;
import com.exadel.carinsurance.repository.IAddressRepository;
import com.exadel.carinsurance.repository.IAddressTypeRepository;
import com.exadel.carinsurance.repository.IContactInfoRepository;
import com.exadel.carinsurance.service.IAddressService;
import com.exadel.carinsurance.service.IAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressService implements IAddressService {
  private final IAssignmentService assignmentService;
  private final IContactInfoRepository contactInfoRepository;
  private final IAddressTypeRepository addressTypeRepository;
  private final IAddressRepository addressRepository;
  private final AddressEntityMapper addressEntityMapper;
  private final AddressResponseMapper addressResponseMapper;

  @Autowired
  public AddressService(
      IAssignmentService assignmentService,
      IContactInfoRepository contactInfoRepository,
      IAddressTypeRepository addressTypeRepository,
      IAddressRepository addressRepository,
      AddressEntityMapper addressEntityMapper,
      AddressResponseMapper addressResponseMapper
  ) {
    this.assignmentService = assignmentService;
    this.contactInfoRepository = contactInfoRepository;
    this.addressTypeRepository = addressTypeRepository;
    this.addressRepository = addressRepository;
    this.addressEntityMapper = addressEntityMapper;
    this.addressResponseMapper = addressResponseMapper;
  }

  @Override
  public AddressEntity createAddressInDB( ContactInfoEntity contactInfo, AddressRequestEntity request ) {
    AddressTypeEntity addressTypeFromDB = addressTypeRepository
        .findByName( request.getType() )
        .orElseThrow( () ->
            new NotFoundException( "The address type is not found" )
        );

    AddressEntity address = addressEntityMapper.toEntity( request );
    address.setType( addressTypeFromDB );
    address.setContactInfo( contactInfo );

    AddressEntity addressFromDB = addressRepository.save( address );

    return addressFromDB;
  }

  @Override
  public ResponseEntity createAddress( AddressRequestEntity request ) {
    ContactInfoEntity contactInfoFromDB = contactInfoRepository
        .findById( request.getContactInfoId() )
        .orElseThrow( () ->
            new NotFoundException( "The contact information is not found" )
        );

    AddressEntity addressFromDB = createAddressInDB( contactInfoFromDB, request );

    assignmentService.checkAndSetAssignmentStatus( addressFromDB.getContactInfo().getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( addressFromDB.getId() );
  }

  @Override
  public ResponseEntity editAddress( Long addressId, AddressRequestEntity request ) {
    AddressEntity addressFromDB = addressRepository
        .findById( addressId )
        .orElseThrow( () ->
            new NotFoundException( "The address is not found" )
        );

    if ( !addressFromDB.getType().getName().equals( request.getType() ) ) {
      AddressTypeEntity addressTypeFromDB = addressTypeRepository
          .findByName( request.getType() )
          .orElseThrow( () ->
              new NotFoundException( "The address type is not found" )
          );

      addressFromDB.setType( addressTypeFromDB );
    }

    addressFromDB.setCity( request.getCity() );
    addressFromDB.setState( request.getState() );
    addressFromDB.setZip( request.getZip() );
    addressFromDB.setAddressLine( request.getAddressLine() );

    AddressEntity addressEditedFromDB = addressRepository.save( addressFromDB );

    assignmentService.checkAndSetAssignmentStatus( addressEditedFromDB.getContactInfo().getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( addressResponseMapper.toResponse( addressEditedFromDB ) );
  }

  @Override
  public ResponseEntity deleteAddress( Long addressId ) {
    AddressEntity addressFromDB = addressRepository
        .findById( addressId )
        .orElseThrow( () ->
            new NotFoundException( "The address is not found" )
        );

    addressRepository.delete( addressFromDB );

    assignmentService.checkAndSetAssignmentStatus( addressFromDB.getContactInfo().getAssignmentId() );

    return ResponseEntity
        .ok()
        .body( addressId );
  }
}
