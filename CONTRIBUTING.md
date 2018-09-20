# Contributing

## Debugging an annotation processor with IntelliJ IDEA and Gradle

1. Set breakpoints somewhere.
2. Start building the project from the terminal: `$ ./gradlew --no-daemon -Dorg.gradle.debug=true clean build`. Gradle  will  wait  for  you  to  attach  a  debugger  at **localhost:5005** by default.
3. Create a default remote debug configuration: **Run -> Edit Configurations... -> Add New Configuration (Alt + Insert) -> Remote**.
[![enter image description here][1]][1]
4. Debug using just created remote configuration within the IDE: **Run -> Debug... (Alt + Shift + F9)**.
[![enter image description here][2]][2]

Hope it helps somebody :)

  [1]: https://i.stack.imgur.com/hG3VJ.jpg
  [2]: https://i.stack.imgur.com/a0pck.png
