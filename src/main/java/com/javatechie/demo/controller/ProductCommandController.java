package com.javatechie.demo.controller;

import com.javatechie.demo.dto.ProductEvent;
import com.javatechie.demo.entity.Product;
import com.javatechie.demo.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private ProductCommandService service;


    @PostMapping
    public Product createProduct(@RequestBody ProductEvent productEvent) {
        return service.createProduct(productEvent);

    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody ProductEvent productEvent) {
        return service.updateProduct(id, productEvent);

    }
}

