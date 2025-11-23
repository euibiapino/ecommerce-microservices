package br.com.warehouse.controller;

import br.com.warehouse.controller.request.StockSaveRequest;
import br.com.warehouse.controller.response.StockSavedResponse;
import br.com.warehouse.mapper.IStockMapper;
import br.com.warehouse.service.IStockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("stocks")
@AllArgsConstructor
public class StockController {

    private final IStockService service;
    private final IStockMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    StockSavedResponse save(@RequestBody final StockSaveRequest request){
        var entity = mapper.toEntity(request);
        entity = service.save(entity);
        return mapper.toResponse(entity);
    }

    @PutMapping("{id}/release")
    @ResponseStatus(NO_CONTENT)
    void active(@PathVariable final UUID id){
        service.release(id);
    }

    @DeleteMapping("{id}/release")
    @ResponseStatus(NO_CONTENT)
    void inactive(@PathVariable final UUID id){
        service.inactive(id);
    }

}
