package com.itsdcode.ncodeapp

import java.util.*

class OrderEntity(
    val id: Int,
    val orderAt: Date,
    val amount: Double,
    val ships: List<Shipping>
) {
    fun toOrder(): Order {
        return Order(id, orderAt, amount, ships)
    }
}

class OrderProductEntity(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrls: List<String>,
    val stock: Stock
)

class StockEntity(
    val color: String,
    val size: String,
    val quantity: Int
)

class ShippingEntity(
    val id: Int,
    val trackingNumber: String,
    val shippingFee: Double,
    val address: String,
    val post: String,
    val message: String,
    val products: List<OrderProduct>
)