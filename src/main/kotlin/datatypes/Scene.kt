package datatypes

import datatypes.AxisAlignedBoundingBox.Companion.surroundingBox
import materials.Material
import shapes.Shape
import shapes.impl.BoundingVolume

class Scene(
    private val children: MutableList<Shape> = mutableListOf()
) : Shape {

    var boundingBox = if (children.isNotEmpty()) BoundingVolume(children) else null

    fun addObject(obj: Shape) {
        children.add(obj)
//        boundingBox = BoundingVolume(children)
    }

    fun clear() {
        children.clear()
    }

    override val movement: Movement
        get() = TODO("Scenes don't have a movement")

    override fun hit(ray: Ray, tRange: ClosedFloatingPointRange<Double>): HitRecord? {
//        return boundingBox?.hit(ray, tRange)

        var closestValue: HitRecord? = null
        var range = tRange

        children.forEach { child ->
            child.hit(ray, range)?.let {
                range = tRange.start..it.t
                closestValue = it
            }
        }

        return closestValue
    }

    override fun boundingBox(time: ClosedFloatingPointRange<Double>): AxisAlignedBoundingBox? {
        if (children.isEmpty()) return null

        var outputBox: AxisAlignedBoundingBox? = null

        children.forEach { child ->
            child.boundingBox(time)?.let { tempBox ->
                outputBox = outputBox?.let { surroundingBox(it, tempBox) } ?: tempBox
            } ?: return null
        }

        return outputBox
    }
}
