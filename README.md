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

## Environment variables
* `OG_JARPATH` - path to a jarfile that contains a Java agent (used by ASM).
* `OG_SRCPATH` - source root directories separated by colons (used by JavaParser).
