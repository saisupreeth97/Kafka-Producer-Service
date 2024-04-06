package com.supreeth.productmicroservice.rest;

import com.supreeth.productmicroservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping

    public ResponseEntity<Object> createProduct(@RequestBody CreateProductRestModel product)
    {
        String productId = null;
        try {
            productId = productService.createProduct(product);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(),e.getMessage(),"/products"));
        }
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }
}
