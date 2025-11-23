package br.com.warehouse.service;

import br.com.warehouse.entity.ProductEntity;

import java.util.UUID;

public interface IProductService {

    ProductEntity save(final ProductEntity entity);

    void purchase(final UUID id);

}
