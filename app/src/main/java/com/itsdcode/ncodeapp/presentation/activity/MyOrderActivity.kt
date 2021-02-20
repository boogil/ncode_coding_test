package com.itsdcode.ncodeapp.presentation.activity

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.gilly.gifsearch.core.extension.observe
import com.itsdcode.ncodeapp.Order
import com.itsdcode.ncodeapp.R
import com.itsdcode.ncodeapp.data.Failure
import com.itsdcode.ncodeapp.databinding.ActivityMainBinding
import com.itsdcode.ncodeapp.presentation.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MyOrderActivity : BindingActivity<ActivityMainBinding>() {

    private val orderViewModel: OrderViewModel by viewModels()

    val handler = Handler()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgress()

        orderViewModel.apply {
            getOrder()
            observe(order, ::onSuccess)
            observe(failure, ::onFailure)
        }
    }

    private fun onSuccess(order: Order?) {
        hideProgress()
        order?.let { presentOrder(it) }
    }

    private fun onFailure(failure: Failure?) {
        hideProgress()
        failure?.let {
            when {
                failure is Failure.FileException -> "파일을 찾을 수 없습니다."
                failure is Failure.TimeoutException -> "타임아웃 오류 입니다."
                else -> "오류입니다."
            }.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                }.show()
            }
        }
    }

    private fun showProgress() {
        handler.post {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgress() {
        handler.post {
            progressBar.visibility = View.GONE
        }
    }

    private fun updateOrderInfo(info: String) {
        handler.post {
            orderInfo.text = info
        }
    }

    // (3) present model
    private fun presentOrder(order: Order) {
        val out = StringBuilder()
        out.println("주문번호: ${order.id}")
        out.println("주문일: ${order.orderAt}")
        out.println("총 결제금액: ${order.amount}원")
        out.println("")
        out.println("----------------------------")
        out.println("")
        order.ships.forEachIndexed { index, shipping ->
            out.println("[배송정보 ${index + 1}]")
            out.println("송장번호: ${shipping.trackingNumber}")
            out.println("배송료: ${shipping.shippingFee}원")
            out.println("주소: [${shipping.post}] ${shipping.address}")
            out.println("메시지: ${shipping.message}")

            out.println("")
            out.println("--- 상품목록 리스트 ---")

            shipping.products.forEachIndexed { index, p ->
                out.println("")
                out.println("[상품목록 ${index + 1}]")
                out.println("상품명: ${p.name}")
                out.println("가격: ${p.price}원")
                out.println("주문정보: ${p.stock.color}/${p.stock.size} ${p.stock.quantity}개")
            }

            out.println("")
            out.println("----------------------------")
            out.println("")
        }
        updateOrderInfo(out.toString())
    }
}

fun StringBuilder.println(text: String): Unit {
    append(text)
    append("\n")
}