package com.hualala.base.presenter.view

interface BaseView {
    fun showLoading(state: String="")
    fun hideLoading()
    fun onError(error: String)
}