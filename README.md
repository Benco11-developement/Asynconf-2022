## Configuring
* `gradle.properties`
  * `maven_group` is the groupId so set it to the group id of your project,
    for example `com.replit`  
  * `archives_base_name` is the archive base name, so set it to the archive name,
    for example `java17withgradle`

* `build.gradle`
  * To set the entrypoint class edit the following lines
      ```java
      ext {
         javaMainClass = "${project.maven_group}.${archivesBaseName}.CLASS_HERE"
      }
      ```
