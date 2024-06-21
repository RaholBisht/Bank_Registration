package com.rahul.bankregistration.model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.rahul.bankregistration.utility.currentActivity
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    val day = MutableLiveData<String>()
    val month = MutableLiveData<String>()
    val year = MutableLiveData<String>()
    val pan = MutableLiveData<String>()

    private val _isBirthdateValid = MutableLiveData<Boolean>()
    val isBirthdateValid: LiveData<Boolean> get() = _isBirthdateValid

    private val _isPanValid = MutableLiveData<Boolean>()
    val isPanValid: LiveData<Boolean> get() = _isPanValid

    val isFormValid: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(_isBirthdateValid) { value = _isBirthdateValid.value == true && _isPanValid.value == true }
        addSource(_isPanValid) { value = _isBirthdateValid.value == true && _isPanValid.value == true }
    }

    fun onNextClicked() {
        if (isFormValid.value == true) {
            viewModelScope.launch {
                Toast.makeText(getApplication(), "Details submitted successfully", Toast.LENGTH_SHORT).show()
                (getApplication<Application>().currentActivity)?.finish()
            }
        }
    }

    fun onNoPanClicked() {
        viewModelScope.launch {
            Toast.makeText(getApplication(), "No PAN provided", Toast.LENGTH_SHORT).show()
            (getApplication<Application>().currentActivity)?.finish()
        }
    }

    private fun validateBirthdate(day: String?, month: String?, year: String?) {
        _isBirthdateValid.value = day?.toIntOrNull()?.let { it in 1..31 } == true &&
                month?.toIntOrNull()?.let { it in 1..12 } == true &&
                year?.toIntOrNull()?.let { it in 1901..2099 } == true
    }

    private fun validatePan(pan: String?) {
        _isPanValid.value = pan?.matches(Regex("[A-Z]{5}[0-9]{4}[A-Z]")) == true
    }

    init {
        day.observeForever { validateBirthdate(day.value, month.value, year.value) }
        month.observeForever { validateBirthdate(day.value, month.value, year.value) }
        year.observeForever { validateBirthdate(day.value, month.value, year.value) }
        pan.observeForever { validatePan(it) }
    }
}
