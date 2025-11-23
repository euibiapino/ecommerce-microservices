package br.com.warehouse.service.impl;

import br.com.warehouse.dto.ProductStoreFrontSaveDTO;
import br.com.warehouse.entity.ProductEntity;
import br.com.warehouse.mapper.IProductMapper;
import br.com.warehouse.repository.ProductRepository;
import br.com.warehouse.service.IProductQueryService;
import br.com.warehouse.service.IProductService;
import br.com.warehouse.service.IStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductQueryServiceImpl implements IProductQueryService {

    private final ProductRepository repository;

    @Override
    public ProductEntity findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

}
