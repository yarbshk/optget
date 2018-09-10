# Contributing

## Debugging an annotation processor with IntelliJ IDEA and Gradle

1. Set a custom VM option `-Dcompiler.process.debug.port=5005`: press **Ctrl + Shift + A** and select **Edit Custom VM Options...** in the list of actions to add a custom VM option then restart the IDE.
2. Create a remote debug configuration with default parameters: **Run -> Edit Configurations... -> Add New Configuration (Alt + Insert) -> Remote**.
[![enter image description here][1]][1]
3. Set breakpoints.
4. Build with Gradle from the terminal: `$ ./gradlew --no-daemon -Dorg.gradle.debug=true clean build` (it's okay if the execution of the command is frozen, don't terminate a process).
5. Debug the remote debug configuration within the IDE (see step 3): select a suitable remote debug configuration and press **Shift + F9**.
[![enter image description here][2]][2]

Hope it helps somebody :)

  [1]: https://i.stack.imgur.com/hG3VJ.jpg
  [2]: https://i.stack.imgur.com/a0pck.png
