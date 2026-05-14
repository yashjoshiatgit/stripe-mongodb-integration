package com.yashJoshi.stripe_payment_integration.client;

import com.yashJoshi.stripe_payment_integration.dto.PaymentNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificationClient", url = "${clients.notification.base-url}")
public interface NotificationClient {

    @PostMapping("/api/v1/notifications/payments")
    void sendPaymentNotification(@RequestBody PaymentNotificationRequest request);
}
