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

    override fun output(): String {
        var currentMaterial: String = ""
        val outputList = mutableListOf<String>()
        val sortedChildren = children.sortedBy { it.material!!.output }

        sortedChildren.forEach {
            if (currentMaterial != it.material?.output) {
                currentMaterial = it.material!!.output
                outputList.add(it.material!!.output)
            }

            outputList.add(it.output())
        }

        return outputList.joinToString("\n") { it }
    }
}
