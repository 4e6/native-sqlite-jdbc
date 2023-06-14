# Build

```
$ mvn package
```

# Issue

`org.xerial/sqlite-jdbc` provides `SqliteJdbcFeature` for building native images.

```
$ unzip -l target/native-sqlite-jdbc-1.0-SNAPSHOT-jar-with-dependencies.jar | grep nativeimage
        0  2023-05-22 12:00   META-INF/versions/9/org/sqlite/nativeimage/
        0  2023-06-14 16:35   META-INF/maven/org.graalvm.nativeimage/
        0  2023-06-14 16:35   META-INF/maven/org.graalvm.nativeimage/native-sqlite-jdbc/
     7900  2023-05-22 12:00   META-INF/versions/9/org/sqlite/nativeimage/SqliteJdbcFeature.class
      245  2023-05-22 12:00   META-INF/versions/9/org/sqlite/nativeimage/SqliteJdbcFeature$1.class
     1127  2023-05-22 12:00   META-INF/versions/9/org/sqlite/nativeimage/SqliteJdbcFeature$SqliteJdbcFeatureException.class
     3140  2023-06-14 16:31   META-INF/maven/org.graalvm.nativeimage/native-sqlite-jdbc/pom.xml
      133  2023-06-14 16:35   META-INF/maven/org.graalvm.nativeimage/native-sqlite-jdbc/pom.properties
```

There is no issue when using the original `sqlite-jdbc` jar as a `-cp` argument to native image (like the maven plugin does)

```
[INFO] Executing: /usr/lib/jvm/java-17-graalvm/lib/svm/bin/native-image -cp /home/dbushev/.m2/repository/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar:/home/dbushev/projects/luna/native-sqlite-jdbc/target/native-sqlite-jdbc-1.0-SNAPSHOT.jar --no-fallback -H:Class=example.Main -H:Name=example
```

But when you try to replace `-cp` dependencies with a single fat jar, the native image fails with
```
$ /usr/lib/jvm/java-17-graalvm/lib/svm/bin/native-image -cp target/native-sqlite-jdbc-1.0-SNAPSHOT-jar-with-dependencies.jar --no-fallback -H:Class=example.Main -H:Name=example
==========================================================================================================
GraalVM Native Image: Generating 'example' (executable)...
==========================================================================================================
[1/7] Initializing...
                                                                      (0.0s @ 0.17GB)
Error: Feature org.sqlite.nativeimage.SqliteJdbcFeature class not found on the classpath. Ensure that the name is correct and that the class is on the classpath.
Error: Use -H:+ReportExceptionStackTraces to print stacktrace of underlying exception
----------------------------------------------------------------------------------------------------------
                  0.1s (8.0% of total time) in 6 GCs | Peak RSS: 0.47GB | CPU load: 4.28
==========================================================================================================
Failed generating 'example' after 0.9s.
Error: Image build request failed with exit status 1
```
