package br.com.storefront.service.impl;

import br.com.storefront.dto.ProductDetailDTO;
import br.com.storefront.dto.ProductInfoDTO;
import br.com.storefront.entity.ProductEntity;
import br.com.storefront.mapper.IProductMapper;
import br.com.storefront.repository.ProductRepository;
import br.com.storefront.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;
    private final RestClient warehouseClient;
    private final IProductMapper mapper;

    @Override
    public ProductEntity save(ProductEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void changeActivated(UUID id, boolean active) {
        var entity = findById(id);
        entity.setActive(active);
        repository.save(entity);
    }

    @Override
    public List<ProductEntity> findAllActive() {
        return repository.findByActiveTrueOrderByNameAsc();
    }

    @Override
    public ProductInfoDTO findInfo(UUID id) {
        var entity = findById(id);
        var price = requestCurrentAmount(id);
        return mapper.toDTO(entity, price);
    }


    @Override
    public void purchase(UUID id) {
        purchaseWarehouse(id);
    }

    private ProductEntity findById (final UUID id){
        return repository.findById(id).orElseThrow();
    }

    private BigDecimal requestCurrentAmount(UUID id) {
        var dto = warehouseClient.get()
                .uri("/products/" + id)
                .retrieve()
                .body(ProductDetailDTO.class);
        return dto.price();
    }

    private void purchaseWarehouse(final UUID id){
        var path = String.format("/products/%s/purchase", id);
        warehouseClient.post()
                .uri(path)
                .retrieve()
                .toBodilessEntity();
    }

}
