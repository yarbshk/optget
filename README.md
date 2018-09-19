# OptGet
A set of processors each of its **generates getter methods**  that wrap a returned value by `java.util.Optional` for fields without of any accessor. Designed for informational and/or testing purposes and are used mainly on DTOs.

## Processor types
There are a few implementations of the processors called _processor types_:
* `asm` - transform the AST using [Lombok](https://projectlombok.org/);
* `ref` - modify (refactor) source files using [JavaParser](https://javaparser.org/);
* `run` - modify bytecode at runtime using [ASM](https://asm.ow2.io/).

## Requirements
* set the getter method name according to the type of a field generated for (`is` prefix for `boolean` types and `get` one for any other types);
* avoid generating a getter method if one is already exists;
* make a generated getter method accessible at least through the Reflection API.

## Getting started

Run a standalone sample project JAR:

```
$ ./run <TYPE>
```

...where `<TYPE>` is meant one of the [processor types](#processor-types).

## Troubleshooting
* `java.lang.IllegalArgumentException: Only directories are allowed as root path: src/main/java` - provide an `OG_SRCPATH` environment variable to specify root directories of sets of source files separated by colons (e.g. `og-sample-ref/src/main/java:og-sample-ref/src/test/java`).
