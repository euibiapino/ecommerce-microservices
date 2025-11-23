package br.com.warehouse.service;

import br.com.warehouse.entity.ProductEntity;

import java.util.UUID;

public interface IProductQueryService {

    ProductEntity findById(final UUID id);


}
