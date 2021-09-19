import datatypes.*
import materials.Material
import materials.impl.Dielectric
import materials.impl.Lambertian
import materials.impl.Metal
import shapes.impl.Sphere
import kotlin.random.Random

object Book1FinalSceneMoving {

    fun camera() = Camera(
        origin = Point3(13.0, 2.0, 3.0),
        target = Point3(0, 0, 0),
        focalDistance = 10.0,
        aperture = 0.1,
        aspectRatio = 16.0 / 9.0,
        verticalFieldOfView = 20.0,
        shutterLength = 1.0
    )

    fun scene(): Scene {
        val scene = Scene();

        val groundMaterial = Lambertian(Colour(.5, .5, .5))
        scene.addObject(Sphere(Point3(0, -1000.0, 0), 1000.0, groundMaterial))

        for (a in -11..11) {
            for (b in -11..11) {
                val chooseMat = Random.nextDouble()
                val center = Point3(a + 0.9 * Random.nextDouble(), 0.2, b + 0.9 * Random.nextDouble())

                if ((center - Point3(4.0, 0.2, 0)).length() > 0.9) {
                    val sphereMaterial: Material = if (chooseMat < 0.8) {
                        // Diffuse
                        val albedo = Colour.random() * Colour.random()
                        Lambertian(albedo)
                    } else if (chooseMat < 0.95) {
                        val albedo = Colour.random(0.5, 1.0)
                        val fuzz = Random.nextDouble(.0, 0.5)
                        Metal(albedo, fuzz)
                    } else {
                        Dielectric(1.5)
                    }

                    scene.addObject(if (chooseMat < .8) {
                        Sphere(
                            centerStart = center,
                            centerEnd = center + Vec3(0, Random.nextDouble(.0,.5), 0),
                            duration = 1.0,
                            radius = 0.2,
                            material = sphereMaterial
                        )
                    } else {
                        Sphere(
                            center = center,
                            radius = 0.2,
                            material = sphereMaterial
                        )
                    })
                }
            }
        }

        val material1 = Dielectric(1.5)
        val material2 = Lambertian(Colour(0.4, 0.2, 0.1))
        val material3 = Metal(Colour(0.7, 0.6, 0.5), 0.0)

        scene.addObject(Sphere(Point3(y = 1.0), 1.0, material1))
        scene.addObject(Sphere(Point3(-4.0, 1.0, 0), 1.0, material2))
        scene.addObject(Sphere(Point3(4.0, 1.0, 0), 1.0, material3))

        return scene
    }
}
