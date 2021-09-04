package lt.ito.models

import android.graphics.Color
import com.google.gson.annotations.SerializedName

enum class Difficulty {
    @SerializedName("easy")
    EASY,

    @SerializedName("normal")
    NORMAL,

    @SerializedName("hard")
    HARD;
}

val Difficulty.color: Int
    get() {
        return when (this) {
            Difficulty.EASY -> Color.parseColor("#2e7d32")     // dark green
            Difficulty.NORMAL -> Color.parseColor("#fb8c00")   // orange
            Difficulty.HARD -> Color.parseColor("#bf360c")     // red
            else -> Color.BLACK
        }
    }