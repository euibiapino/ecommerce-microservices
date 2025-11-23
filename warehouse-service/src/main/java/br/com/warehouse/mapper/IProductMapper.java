package br.com.warehouse.mapper;

import br.com.warehouse.controller.request.ProductSaveRequest;
import br.com.warehouse.controller.response.ProductDetailResponse;
import br.com.warehouse.controller.response.ProductSavedResponse;
import br.com.warehouse.dto.ProductStoreFrontSaveDTO;
import br.com.warehouse.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stocks", ignore = true)
    @Mapping(target = "price", ignore = true)
    ProductEntity toEntity(final ProductSaveRequest request);

    ProductSavedResponse toSavedResponse(final ProductEntity entity);

    ProductStoreFrontSaveDTO toDTO(final ProductEntity entity);

    ProductDetailResponse toDetailResponse(final ProductEntity entity);

}
