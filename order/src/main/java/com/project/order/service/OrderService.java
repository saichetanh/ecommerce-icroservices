package com.project.order.service;

import com.project.order.config.WebClientConfig;
import com.project.order.dto.InventoryDto;
import com.project.order.dto.OrderDTO;
import com.project.order.dto.OrderLineItemsDTO;
import com.project.order.event.OrderInfoEvent;
import com.project.order.model.Orders;
import com.project.order.model.OrderLineItems;
import com.project.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderInfoEvent> kafkaTemplate;

    public String saveOrder(OrderDTO orderDTO) {
        Orders order = new Orders();
        order.setOrderNo(UUID.randomUUID().toString());
        List<OrderLineItems> oli = orderDTO.getOrderLineItems().stream().map(this::to).toList();
        order.setOrderLineItems(oli);
        List<String> skuList = order.getOrderLineItems().stream().map(OrderLineItems::getSku).toList();
        InventoryDto[] inventoryDto = webClientBuilder.build().get().uri("http://inventory/api/inventory", uriBuilder -> uriBuilder.queryParam("sku", skuList).build()).retrieve()
                .bodyToMono(InventoryDto[].class).block();
        boolean isPresent = Arrays.stream(inventoryDto).allMatch(InventoryDto::isPresent);
        if (isPresent) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderInfoEvent(order.getOrderNo()));
            return "created";
        } else {
            return "product is not present";
        }
    }

    public OrderLineItems to(OrderLineItemsDTO lineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSku(lineItemsDTO.getSku());
        orderLineItems.setPrice(lineItemsDTO.getPrice());
        orderLineItems.setQuantity(lineItemsDTO.getQuantity());
        return orderLineItems;
    }

    public List<Orders> getAllOrders() {
        List<Orders> all = orderRepository.findAll();
        return all;
    }
}
