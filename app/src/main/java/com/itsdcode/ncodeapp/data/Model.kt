package com.itsdcode.ncodeapp

import java.util.*

class Order(
    val id: Int,
    val orderAt: Date,
    val amount: Double,
    val ships: List<Shipping>
)

class OrderProduct(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrls: List<String>,
    val stock: Stock
)

class Stock(
    val color: String,
    val size: String,
    val quantity: Int
)

class Shipping(
    val id: Int,
    val trackingNumber: String,
    val shippingFee: Double,
    val address: String,
    val post: String,
    val message: String,
    val products: List<OrderProduct>
)