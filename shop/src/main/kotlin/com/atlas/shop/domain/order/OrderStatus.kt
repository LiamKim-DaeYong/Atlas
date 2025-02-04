package com.atlas.shop.domain.order

enum class OrderStatus {
    PENDING_PAYMENT, // 결제 대기
    PAID, // 결제 완료
    PREPARING_SHIPMENT, // 배송 준비
    SHIPPED, // 배송중
    DELIVERED, // 배송 완료
    CANCELED // 주문 취소
}