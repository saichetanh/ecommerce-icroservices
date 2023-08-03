package com.project.inventory;

import com.project.inventory.model.Inventory;
import com.project.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Bean
    public CommandLineRunner add(InventoryRepository repository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSku("sku1");
            inventory.setQuantity(2);

            Inventory inventory1 = new Inventory();
            inventory1.setSku("sku2");
            inventory1.setQuantity(3);

            Inventory inventory2 = new Inventory();
            inventory2.setSku("sku3");
            inventory2.setQuantity(4);

            Inventory inventory4 = new Inventory();
            inventory4.setSku("sku4");
            inventory4.setQuantity(5);

            Inventory inventory3 = new Inventory();
            inventory3.setSku("sku5");
            inventory3.setQuantity(6);

            repository.save(inventory1);
            repository.save(inventory2);
            repository.save(inventory3);
            repository.save(inventory4);
            repository.save(inventory);

        };
    }

}
