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
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;
    private final IProductQueryService queryService;
    private final IStockService stockService;
    private final RestClient storefrontClient;
    private final IProductMapper mapper;

    @Override
    public ProductEntity save(ProductEntity entity) {
        repository.save(entity);
        var dto = mapper.toDTO(entity);
        saveStorefront(dto);
        return entity;
    }

    @Override
    public void purchase(UUID id) {
        var entity = queryService.findById(id);
        var stock = entity.decStock();
        repository.save(entity);
        if(stock.isUnavailable()){
            stockService.changeStatus(stock.getId(), stock.getStatus());
        }
    }

    private void saveStorefront(final ProductStoreFrontSaveDTO dto) {
        storefrontClient.post()
                .uri("/products")
                .body(dto)
                .retrieve()
                .body(ProductStoreFrontSaveDTO.class);
    }
}
