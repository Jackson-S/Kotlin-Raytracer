package extensions

fun Math.clamp(x: Double, max: Double, min: Double) = when {
    x > max -> max
    x < min -> min
    else -> x
}
