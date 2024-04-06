package com.supreeth.productmicroservice.service;

import com.supreeth.productmicroservice.rest.CreateProductRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception{
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,productRestModel.getTitle(),
                productRestModel.getPrice(),productRestModel.getQuantity());
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);
        logger.info("Before publishing a ProductCreatedEvent");
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent).get();
//        future.whenComplete((result,exception) -> {   
//            if(exception!=null)
//            {
//                logger.error("********* Failed to send error message: "+exception.getMessage());
//            }
//            else
//            {
//                logger.info("********** Message Sent Successfully: "+result.getRecordMetadata());
//            }
//        });
//        future.join();
        logger.info("Partition: "+result.getRecordMetadata().partition());
        logger.info("Topic Name: "+result.getRecordMetadata().topic());
        logger.info("Offset: "+result.getRecordMetadata().offset());

        logger.info("************ Return product id");
        return productId;
    }
}
