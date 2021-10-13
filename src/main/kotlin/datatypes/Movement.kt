package datatypes

data class Movement(
    val startPosition: Point3,
    val endPosition: Point3?,
    val startTime: Double,
    val endTime: Double
) {
    val duration get() = endTime - startTime
    val hasMovement get() = endPosition != null && startPosition != endPosition

    constructor(
        position: Point3
    ) : this(
        startPosition = position,
        endPosition = null,
        startTime = Double.NEGATIVE_INFINITY,
        endTime = Double.POSITIVE_INFINITY
    )
}