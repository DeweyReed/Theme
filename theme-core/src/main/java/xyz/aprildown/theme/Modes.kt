package xyz.aprildown.theme

enum class ColorMode(val value: Int) {
    PRIMARY(0),
    ACCENT(1);

    companion object {
        internal fun fromInt(value: Int): ColorMode {
            return when (value) {
                0 -> PRIMARY
                else -> ACCENT
            }
        }
    }
}

enum class AutoSwitchMode(val value: Int) {
    OFF(0),
    ON(1),
    AUTO(2);

    companion object {
        internal fun fromInt(value: Int): AutoSwitchMode {
            return when (value) {
                0 -> OFF
                1 -> ON
                else -> AUTO
            }
        }
    }
}

