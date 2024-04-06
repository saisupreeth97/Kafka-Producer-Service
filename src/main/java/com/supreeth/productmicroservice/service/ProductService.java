package com.supreeth.productmicroservice.service;

import com.supreeth.productmicroservice.rest.CreateProductRestModel;

public interface ProductService {
    String createProduct(CreateProductRestModel productRestModel) throws Exception;
}
