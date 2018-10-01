# OptGet
A set of annotation processors each of its **generates getter methods**  that wrap a returned value by `java.util.Optional` for fields without of any accessor. Designed for informational and/or testing purposes and are used mainly on DTOs.

## Processor types
There are a few implementations of the processors called _processor types_:
* `ast` - transform the AST using [Lombok](https://projectlombok.org/);
* `byt` - modify bytecode at runtime using [ASM](https://asm.ow2.io/);
* `ref` - modify (refactor) source files using [JavaParser](https://javaparser.org/).

## Requirements
* set the getter method name according to the type of a field generated for (`is` prefix for `boolean` types and `get` one for any other types);
* avoid generating a getter method if one is already exists;
* use the `ofNullable` method for nullable values and `of` for non-null ones;
* make a generated getter method accessible at least through the Reflection API.

## Getting started

Run a standalone sample project JAR (the bash script populates just a set of environment variables for convenience):

```
$ ./scripts/run <TYPE>
```

...where `<TYPE>` is meant one of the [processor types](#processor-types) (e.g. `$ ./scripts/run ast`).

## Troubleshooting
* `java.lang.IllegalArgumentException: Only directories are allowed as root path: src/main/java` - provide an `OG_SRCPATH` environment variable to specify root directories of sets of source files separated by colons if many (e.g. `og-apps/sample-ref/src/main/java`).
* `com.sun.tools.attach.AgentLoadException: Agent JAR not found or no Agent-Class attribute` - provide an `OG_JARPATH` environment variable to specify path to a jarfile that contains a Java agent (e.g. `optget/og-processors/byt/build/libs/byt-1.0-SNAPSHOT.jar`).
