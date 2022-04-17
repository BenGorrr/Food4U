package com.example.food4u.modal

data class Cart(
    val id: String = "",
    val products: Map<String, Boolean> = hashMapOf<String, Boolean>(),

) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            id to products,
        )
    }
}

data class CartItem(
    val productId: String = "",
    val qty: Long = 0,

    ) {
}