package com.gilly.gifsearch.core.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun <reified T : ViewModel> Fragment.viewModel(body: T.() -> Unit): T {
    val vm: T = ViewModelProvider(this)[T::class.java]
    vm.body()
    return vm
}
