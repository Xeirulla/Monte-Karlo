package com.bignerdranch.android.koteln

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bignerdranch.android.koteln.presentation.viewModel.CalculatePiViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: CalculatePiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(CalculatePiViewModel::class.java)


        if (viewModel.getIteration() > 0)
            edit_text.setText(viewModel.getIteration().toString())

        calculateBtn.setOnClickListener {
            val iteration = edit_text.text.toString().toLongOrNull() ?: 0
            viewModel.calculate(iteration)
            hideKeyboard()
        }

        cancelBtn.setOnClickListener {
            viewModel.cancelCalculate()
        }

        viewModel.result.observe(this, Observer {
            pi.text = it?.piValue?.toString()
            result_iterac.text = it?.countPoint?.toString()
        })

        viewModel.inProgress.observe(this, Observer {
            progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
            calculateBtn.isEnabled = !(it ?: false)
            edit_text.isEnabled = !(it ?: false)
        })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus

        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
