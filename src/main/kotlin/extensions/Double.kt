package extensions

import datatypes.Vec3

operator fun Double.times(other: Vec3) = other * this
