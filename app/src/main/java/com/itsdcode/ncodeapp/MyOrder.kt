package com.itsdcode.ncodeapp

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset

class MyOrder : AppCompatActivity() {
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showProgress()

        // (1) fetch json
        fetchMyOrder { json ->

            // (2) parse json
            parseOrder(json) { order ->

                hideProgress()

                // (3) present model
                presentOrder(order)
            }
        }
    }

    fun showProgress() {
        handler.post {
            progressBar.visibility = View.VISIBLE
        }
    }

    fun hideProgress() {
        handler.post {
            progressBar.visibility = View.GONE
        }
    }

    fun updateOrderInfo(info: String) {
        handler.post {
            orderInfo.text = info
        }
    }

    // (1) fetch json
    fun fetchMyOrder(onCompleted: (json: String) -> Unit) {
        Thread(Runnable {
            Thread.sleep(500)

            assets.open("order.json")
                    .use { inputStream ->
                        val buffer = ByteArray(inputStream.available())
                        inputStream.read(buffer)
                        String(buffer, Charset.defaultCharset())
                    }
                    .let { json ->
                        onCompleted(json)
                    }
        }).start()
    }

    // (2) parse json
    fun parseOrder(json: String, onCompleted: (order: Order) -> Unit) {
        Thread(Runnable {
            Thread.sleep(100)
            val order = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create()
                    .fromJson(json, Order::class.java)
            onCompleted(order)
        }).start()
    }

    // (3) present model
    fun presentOrder(order: Order) {
        val out = StringBuilder()
        out.println("주문번: ${order.id}")
        out.println("주문일: ${order.orderAt}")
        out.println("총 결제금액: ${order.amount}원")
        out.println("")
        out.println("----------------------------")
        out.println("[상품목록]")
        out.println("")
        order.products.forEach { p ->
            out.println("상품명: ${p.name}")
            out.println("가격: ${p.price}원")
            out.println("주문정보: ${p.stock.color}/${p.stock.size} ${p.stock.quantity}개")
            out.println("")
        }
        out.println("----------------------------")
        out.println("[배송정보]")
        out.println("송장번호: ${order.shipping.trackingNumber}")
        out.println("배송료: ${order.shipping.shippingFee}원")
        out.println("주소: [${order.shipping.post}] ${order.shipping.address}")
        out.println("메시지: ${order.shipping.message}")

        updateOrderInfo(out.toString())
    }
}

fun StringBuilder.println(text: String): Unit {
    append(text)
    append("\n")
}