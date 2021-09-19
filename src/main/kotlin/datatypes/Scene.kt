package datatypes

import materials.Material
import shapes.Shape

class Scene(
    private val children: MutableList<Shape> = mutableListOf()
) : Shape {
    override val material: Material? = null

    fun addObject(obj: Shape) {
        children.add(obj)
    }

    fun clear() {
        children.clear()
    }

    override fun hit(ray: Ray, tRange: ClosedRange<Double>): HitRecord? {
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
}
