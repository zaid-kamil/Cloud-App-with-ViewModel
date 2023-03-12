package com.example.cloudapp.models


data class Product(
    var name:String = "",
    var brand:String = "",
    var category:String = "",
    val imgsrc:String = "https://wabisabiproject.com/wp-content/uploads/woocommerce-placeholder.png",
    var price:Double = 0.0,
)
