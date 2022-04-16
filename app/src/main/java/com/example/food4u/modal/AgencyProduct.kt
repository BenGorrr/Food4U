package com.example.food4u.modal

data class AgencyProduct(
    val products: Map<String, Boolean> = hashMapOf<String, Boolean>(),

) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "products" to products,
        )
    }
}
