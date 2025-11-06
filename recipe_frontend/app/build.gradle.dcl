androidApplication {
    namespace = "org.example.app"

    dependencies {
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation(project(":utilities"))
    }
}
