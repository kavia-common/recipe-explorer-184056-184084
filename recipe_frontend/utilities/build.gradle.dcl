androidLibrary {
    namespace = "org.gradle.experimental.android.utilities"

    testing {
        unitTests {
            failOnNoTests = false
        }
    }

    dependencies {
        api(project(":list"))
    }
}
