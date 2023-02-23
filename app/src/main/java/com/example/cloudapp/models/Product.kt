package com.example.cloudapp.models


data class Product(
    val name:String = "",
    val brand:String = "",
    val category:String = "",
    val imgsrc:String = "https://wabisabiproject.com/wp-content/uploads/woocommerce-placeholder.png",
    val price:Double = 0.0,
)
