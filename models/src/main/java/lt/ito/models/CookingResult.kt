package lt.ito.models

import com.google.gson.annotations.SerializedName

/** @param value used to convert this [enum] to [Int]. Example use: for calculating a score */
enum class CookingResult(val value: Int) {
    @SerializedName("totalDisaster")
    TOTAL_DISASTER(1), // User was unable to prepare the dish correctly

    @SerializedName("itWasEdible")
    IT_WAS_EDIBLE(2),  // User prepared the dish, but it was not completely successful.

    @SerializedName("perfection")
    PERFECTION(3)  // User prepared the dish perfectly.
}