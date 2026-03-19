# kotlin-jpms-configurationcache

Replicator for issue with Gradle Configuration Cache, JPMS + Kotlin.

Recommended gradle block to add Kotlin compiled classes to JPMS module causes issue with gradle configuration cache
```
tasks.named("compileJava", JavaCompile::class.java) {
    options.compilerArgumentProviders.add(CommandLineArgumentProvider {
        // Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
        listOf("--patch-module", "test.module=${sourceSets["main"].output.asPath}")
    })
}
```

Running `./gradlew build --configuration-cache` fails with the following error:

```
FAILURE: Build failed with an exception.

* What went wrong:
Configuration cache problems found in this build.

1 problem was found storing the configuration cache.
- Task `:compileJava` of type `org.gradle.api.tasks.compile.JavaCompile`: cannot serialize Gradle script object references as these are not supported with the configuration cache.
  See https://docs.gradle.org/9.4.0/userguide/configuration_cache_requirements.html#config_cache:requirements:disallowed_types
```


But Running `./gradlew build` passes.
