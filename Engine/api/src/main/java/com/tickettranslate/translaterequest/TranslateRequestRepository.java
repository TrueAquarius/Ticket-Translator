package com.tickettranslate.translaterequest;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TranslateRequestRepository extends MongoRepository<TranslateRequest, String> {
}
