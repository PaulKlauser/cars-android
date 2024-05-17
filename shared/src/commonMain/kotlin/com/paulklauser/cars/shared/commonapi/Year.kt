package com.paulklauser.cars.shared.commonapi

/**
 * The free tier of carapi.app only supports years 2015-2020.
 */
enum class Year(val value: String) {
    TWENTY_FIFTEEN("2015"),
    TWENTY_SIXTEEN("2016"),
    TWENTY_SEVENTEEN("2017"),
    TWENTY_EIGHTEEN("2018"),
    TWENTY_NINETEEN("2019"),
    TWENTY_TWENTY("2020")
}