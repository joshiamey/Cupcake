package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel : ViewModel() {
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price : LiveData<Double> = _price

    val dateOptions = getPickupOptions()
    companion object {
        private const val PRICE_PER_CUPCAKE = 2.00
        private const val SAME_DAY_PICKUP_CHARGE = 3.00
    }
    init {
        resetOrder()
    }

    private fun resetOrder() {
        _quantity.value = 0
        _date.value = dateOptions[0]
        _flavor.value = ""
        _price.value = 0.0
    }

    fun setQuantity(numberOfCupCakes: Int) {
        _quantity.value = numberOfCupCakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun updatePrice() {
        var calculatedPrice : Double =(_quantity.value?:0) * PRICE_PER_CUPCAKE

        if(_date.value == dateOptions[0]) {
            calculatedPrice += SAME_DAY_PICKUP_CHARGE
        }

        _price.value = calculatedPrice
    }
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE,1)
        }

        return options
    }


}