package materials

import datatypes.Colour
import datatypes.HitRecord
import datatypes.Ray

interface Material {

    val name: String

    val output: String

    fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Colour>?
}
