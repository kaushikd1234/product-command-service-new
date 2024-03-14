package com.javatechie.demo.service;

import com.javatechie.demo.dto.ProductEvent;
import com.javatechie.demo.entity.Product;
import com.javatechie.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ProductRepository repository;

    public Product createProduct(ProductEvent productEvent) {

        Product productDO = repository.save(productEvent.getProduct());
        ProductEvent event = new ProductEvent("CreateProduct", productDO);

        kafkaTemplate.send("product-event-topic", event);
        return productDO;
    }

    public Product updateProduct(long id, ProductEvent productEvent) {

        Product newProduct = productEvent.getProduct();
        Product existingProduct = repository.findById(id).get();
        existingProduct.setName(newProduct.getName());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setPrice(newProduct.getPrice());

        Product productDO = repository.save(existingProduct);
        ProductEvent event=new ProductEvent("UpdateProduct", productDO);

        kafkaTemplate.send("product-event-topic", event);
        return productDO;
    }
}
