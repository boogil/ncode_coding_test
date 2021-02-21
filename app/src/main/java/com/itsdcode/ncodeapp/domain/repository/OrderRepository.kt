package com.itsdcode.ncodeapp.domain.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.itsdcode.ncodeapp.Order
import com.itsdcode.ncodeapp.OrderEntity
import com.itsdcode.ncodeapp.data.Failure
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.nio.charset.Charset
import javax.inject.Inject

class OrderRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    val order: MutableLiveData<Order> = MutableLiveData()
    val failure: MutableLiveData<Failure> = MutableLiveData()

    suspend fun getOrder() {
        try {
            val deferred = GlobalScope.async {
                withTimeout(5000) {
                    delay(500)
                    // (1) fetch json
                    fetchMyOrder { json ->
                        // (2) parse json
                        parseOrder(json) { orderEntity ->
                            this@OrderRepository.order.postValue(orderEntity.toOrder())
                        }
                    }
                }
            }

            deferred.await()
        } catch (e: FileNotFoundException) {
            this.failure.postValue(Failure.FileException)
        } catch (te: TimeoutCancellationException) {
            this.failure.postValue(Failure.TimeoutException)
        }
    }

    private fun fetchMyOrder(onCompleted: (json: String) -> Unit) {
        appContext.assets.open("order.json")
            .use { inputStream ->
                val buffer = ByteArray(inputStream.available())
                inputStream.read(buffer)
                String(buffer, Charset.defaultCharset())
            }
            .let { json ->
                onCompleted(json)
            }
    }

    // (2) parse json
    private fun parseOrder(json: String, onCompleted: (orderEntity: OrderEntity) -> Unit) {

        val orderEntity = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
            .fromJson(json, OrderEntity::class.java)
        onCompleted(orderEntity)

    }

}
