package com.paulklauser.cars.makes

data class Make(
    val id: String,
    val name: String
) {
    companion object {
        fun fromApi(apiMake: ApiMake): Make {
            return Make(
                id = apiMake.id.toString(),
                name = apiMake.name
            )
        }
    }
}
