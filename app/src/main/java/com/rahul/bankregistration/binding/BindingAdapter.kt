package com.rahul.bankregistration.binding

import android.graphics.Color
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

@BindingAdapter("borderColor")
fun setBorderColor(editText: EditText, isValid: LiveData<Boolean>?) {
    isValid?.observeForever {
        if (it) {
            editText.background.setTint(Color.BLUE)
        } else {
            editText.background.setTint(Color.RED)
        }
    }
}
