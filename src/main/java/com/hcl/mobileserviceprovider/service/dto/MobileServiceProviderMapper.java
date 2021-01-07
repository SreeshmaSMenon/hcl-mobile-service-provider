package com.hcl.mobileserviceprovider.service.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.hcl.mobileserviceprovider.service.entity.Connection;

@Mapper
public interface MobileServiceProviderMapper {

	MobileServiceProviderMapper MAPPER = Mappers.getMapper(MobileServiceProviderMapper.class);
	
	ConnectionResponse toConnectionResponse(Connection connection);

}
