package com.example.cloudapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cloudapp.models.Product
import com.example.cloudapp.ui.home.HomeFragment.Companion.COLL_PRODUCTS
import com.google.firebase.firestore.FirebaseFirestore

class AddProductViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isSaved = MutableLiveData(false)
    val isSaved: MutableLiveData<Boolean> = _isSaved

    fun saveProduct(
        db: FirebaseFirestore,
        name: String,
        price: String,
        brand: String,
        category: String
    ) {
        _isLoading.value = true

        if (!validateProduct(name, brand, category, price)) {
            _isLoading.value = false
            _isSaved.value = false
        } else {
            val product = Product(name, brand, category, price = price.toDouble())
            db.collection(COLL_PRODUCTS).add(product).addOnSuccessListener {
                _isLoading.value = false
                _isSaved.value = true
            }.addOnFailureListener {
                _isLoading.value = false
                _isSaved.value = false
            }.addOnCanceledListener {
                _isLoading.value = false
                _isSaved.value = false
            }
        }
    }

    private fun validateProduct(
        product: String,
        brand: String,
        category: String,
        price: String
    ): Boolean {
        return product.isNotEmpty() && brand.isNotEmpty() && category.isNotEmpty() && price.isNotEmpty()
    }
}