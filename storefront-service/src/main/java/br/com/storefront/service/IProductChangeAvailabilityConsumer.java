package br.com.storefront.service;

import br.com.storefront.dto.StockStatusMessage;

public interface IProductChangeAvailabilityConsumer {

    void receive(final StockStatusMessage message);

}

