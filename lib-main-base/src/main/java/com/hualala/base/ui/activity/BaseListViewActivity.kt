package com.hualala.base.ui.activity

import android.os.Bundle
import com.hualala.base.R
import com.hualala.base.ui.fragment.BaseFragment

abstract class BaseListViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_recycler)

        getDisplayFragment()?.let {
            val fragment = it

            intent.extras?.let {
                fragment.arguments = intent.extras
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.content, getDisplayFragment())
            transaction.commit()

        }
    }

    protected abstract fun getDisplayFragment(): BaseFragment?

}