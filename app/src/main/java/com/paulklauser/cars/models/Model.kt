package com.paulklauser.cars.models

data class Model(
    val id: String,
    val name: String
) {
    companion object {
        fun fromApiModel(apiModel: ModelsResponse.ApiModel): Model {
            return Model(apiModel.id.toString(), apiModel.name)
        }
    }
}
