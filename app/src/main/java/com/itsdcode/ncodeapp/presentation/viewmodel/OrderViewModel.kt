package com.itsdcode.ncodeapp.presentation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.itsdcode.ncodeapp.Order
import com.itsdcode.ncodeapp.domain.repository.OrderRepository
import com.itsdcode.ncodeapp.data.Failure
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderViewModel
@ViewModelInject constructor(
    private val orderRepository: OrderRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val order: LiveData<Order> = orderRepository.order
    val failure: LiveData<Failure> = orderRepository.failure

    fun getOrder() {
        GlobalScope.launch { orderRepository.getOrder() }
    }

}