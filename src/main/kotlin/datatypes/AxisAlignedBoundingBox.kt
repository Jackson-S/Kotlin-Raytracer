package datatypes

import kotlin.math.max
import kotlin.math.min

class AxisAlignedBoundingBox(
    val minimum: Point3,
    val maximum: Point3
) {
    companion object {
        fun surroundingBox(box0: AxisAlignedBoundingBox, box1: AxisAlignedBoundingBox): AxisAlignedBoundingBox {
            val small = Point3(
                min(box0.minimum.x, box1.minimum.x),
                min(box0.minimum.y, box1.minimum.y),
                min(box0.minimum.z, box1.minimum.z),
            )

            val big = Point3(
                max(box0.maximum.x, box1.maximum.x),
                max(box0.maximum.y, box1.maximum.y),
                max(box0.maximum.z, box1.maximum.z),
            )

            return AxisAlignedBoundingBox(small, big)
        }
    }

    fun hit(ray: Ray, timeRange: ClosedFloatingPointRange<Double>): Boolean {
        var tStart = timeRange.start
        var tEnd = timeRange.endInclusive

        for (a in 0 until 3) {
            val t0 = min((minimum[a] - ray.origin[a]) / ray.direction[a], (maximum[a] - ray.origin[a]) / ray.direction[a])
            val t1 = max((minimum[a] - ray.origin[a]) / ray.direction[a], (maximum[a] - ray.origin[a]) / ray.direction[a])

            tStart = max(t0, tStart)
            tEnd = min(t1, tEnd)

            if (tEnd <= tStart) return false
        }

        return true

//        var newTimeRange = timeRange
//
//        for (a in 0..3) {
//            val invD = 1.0 / ray.direction[a]
//            var t0 = (minimum[a] - ray.origin[a]) * invD
//            var t1 = (maximum[a] - ray.origin[a]) * invD
//
//            if (invD < .0)
//                t0 = t1.also { t1 = t0 }
//
//            newTimeRange = min(t0, newTimeRange.start)..max(t1, newTimeRange.endInclusive)
//
//            if (newTimeRange.endInclusive <= newTimeRange.start) {
//                return false
//            }
//        }
//
//        return true
    }
}