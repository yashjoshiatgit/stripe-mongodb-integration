package com.yashJoshi.stripe_payment_integration.repository;

import com.yashJoshi.stripe_payment_integration.entity.StripePayment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StripePaymentRepository extends MongoRepository<StripePayment, String> {
    Optional<StripePayment> findBySessionId(String sessionId);
}
