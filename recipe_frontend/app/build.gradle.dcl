androidApplication {
    namespace = "org.example.app"

    // Declarative DSL does not support direct android/buildFeatures blocks here.
    // Parcelize is not required since Recipe no longer implements Parcelable.

    dependencies {
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation(project(":utilities"))
    }
}
