package shapes.impl

import com.soywiz.korio.util.length
import datatypes.*
import datatypes.AxisAlignedBoundingBox.Companion.surroundingBox
import shapes.Shape
import kotlin.random.Random

class BoundingVolume(
    sourceObjects: List<Shape>,
    range: IntRange,
    private val timeRange: ClosedFloatingPointRange<Double>
) : Shape {
    private val left: Shape
    private val right: Shape
    private val box: AxisAlignedBoundingBox
    override val movement: Movement

    constructor(sourceObjects: List<Shape>) : this(sourceObjects, 0..sourceObjects.size, getTimeRange(sourceObjects))

    init {
        val axis = Random.nextInt(0, 2)
        when (range.last - range.first) {
            1 -> {
                left = sourceObjects[range.first]
                right = sourceObjects[range.first]
            }
            2 -> {
                left = sourceObjects[range.first]
                right = sourceObjects[range.first + 1]
            }
            else -> {
                sourceObjects.subList(range.first, range.last).sortedWith { a, b ->
                    boxCompare(a, b, axis)
                }

                val mid = range.first + range.length / 2
                left = BoundingVolume(sourceObjects, range.first..mid, timeRange)
                right = BoundingVolume(sourceObjects, mid..range.last, timeRange)

                println("${range.first} $mid ${range.last}")
            }
        }
        val boxLeft = left.boundingBox(timeRange)
        val boxRight = right.boundingBox(timeRange)

        if (boxLeft == null || boxRight == null)
            throw Error("No bounding box in bvh_node constructor.")

        box = surroundingBox(boxLeft, boxRight)

        movement = Movement(
            startPosition = (box.minimum + box.maximum) / 2.0,
            endPosition = (box.minimum + box.maximum) / 2.0,
            startTime = timeRange.start,
            endTime = timeRange.endInclusive
        )
    }

    companion object {
        private fun getTimeRange(objects: List<Shape>): ClosedFloatingPointRange<Double> {
            if (objects.isEmpty()) return 0.0..0.0

            val minimum = objects.minOf { it.movement.startTime }
            val maximum = objects.maxOf { it.movement.endTime }

            return minimum..maximum
        }
    }

    override fun boundingBox(time: ClosedFloatingPointRange<Double>): AxisAlignedBoundingBox? = box

    override fun hit(ray: Ray, tRange: ClosedFloatingPointRange<Double>): HitRecord? {
        if (!box.hit(ray, timeRange)) return null

        val leftHit = left.hit(ray, timeRange)
        val newTimeRange = timeRange.start..(leftHit?.t ?: timeRange.endInclusive)
        val rightHit = right.hit(ray, newTimeRange)

        return listOfNotNull(leftHit, rightHit).lastOrNull()
    }

    private fun boxCompare(a: Shape, b: Shape, axis: Int): Int {
        val boxA = a.boundingBox(0.0..0.0)
        val boxB = b.boundingBox(0.0..0.0)

        if (boxA == null || boxB == null)
            throw Error("No bounding box in bvh_node constructor.")

        return when {
            boxA.minimum[axis] - boxB.minimum[axis] < 0.0 -> -1
            boxA.minimum[axis] - boxB.minimum[axis] == 0.0 -> 0
            boxA.minimum[axis] - boxB.minimum[axis] > 0.0 -> 1
            else -> throw Error("The fuck?")
        }
    }
}