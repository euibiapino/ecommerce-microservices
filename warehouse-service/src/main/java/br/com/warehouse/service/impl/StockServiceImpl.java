package br.com.warehouse.service.impl;

import br.com.warehouse.dto.StockStatusMessage;
import br.com.warehouse.entity.StockEntity;
import br.com.warehouse.entity.StockStatus;
import br.com.warehouse.repository.StockRepository;
import br.com.warehouse.service.IProductChangeAvailabilityProducer;
import br.com.warehouse.service.IProductQueryService;
import br.com.warehouse.service.IStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.warehouse.entity.StockStatus.AVAILABLE;
import static br.com.warehouse.entity.StockStatus.UNAVAILABLE;

@AllArgsConstructor
@Service
public class StockServiceImpl implements IStockService {

    private final StockRepository repository;
    private final IProductQueryService productQueryService;
    private final IProductChangeAvailabilityProducer producer;

    @Override
    public StockEntity save(StockEntity entity) {
        entity.setProduct(productQueryService.findById(entity.getProduct().getId()));
        return repository.save(entity);
    }

    @Override
    public void release(UUID id) {
        changeStatus(id, AVAILABLE);
    }

    @Override
    public void inactive(UUID id) {
        changeStatus(id, UNAVAILABLE);
    }

    @Override
    public void changeStatus(UUID id, StockStatus status) {
        var entity = repository.findById(id).orElseThrow();
        entity.setStatus(status);
        repository.save(entity);
        producer.notifyStatusChange(new StockStatusMessage(entity.getProduct().getId(), status));
    }
}
