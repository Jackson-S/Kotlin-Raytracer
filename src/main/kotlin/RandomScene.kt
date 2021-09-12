import datatypes.Colour
import datatypes.Point3
import datatypes.Scene
import materials.Material
import materials.impl.Dielectric
import materials.impl.Lambertian
import materials.impl.Metal
import shapes.impl.Sphere
import kotlin.random.Random

fun randomScene(): Scene {
    val scene = Scene();

    val groundMaterial = Lambertian(Colour(.5, .5, .5))
    scene.addObject(Sphere(Point3(0.0, -1000.0, 0.0), 1000.0, groundMaterial))

    for (a in -11..11) {
        for (b in -11..11) {
            val chooseMat = Random.nextDouble()
            val center = Point3(a + 0.9 * Random.nextDouble(), 0.2, b + 0.9 * Random.nextDouble())

            if ((center - Point3(4.0, 0.2, .0)).length() > 0.9) {
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

                scene.addObject(Sphere(center, 0.2, sphereMaterial))
            }
        }
    }

    val material1 = Dielectric(1.5)
    val material2 = Lambertian(Colour(0.4, 0.2, 0.1))
    val material3 = Metal(Colour(0.7, 0.6, 0.5), 0.0)

    scene.addObject(Sphere(Point3(y = 1.0), 1.0, material1))
    scene.addObject(Sphere(Point3(-4.0, 1.0, .0), 1.0, material2))
    scene.addObject(Sphere(Point3(4.0, 1.0, .0), 1.0, material3))

    return scene
}