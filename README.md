# NES for Spring Boot 2.7

Welcome to the trial version of NES for Spring Boot 2.7! This is a simple example of how to use NES with Spring Boot 2.7.

**⚠️ Important:** This is a trial version and is not intended for production use. Trial versions have zero CVE fixes included. The purpose of trial versions is to verify integration with HeroDevs repositories. To obtain fully secure, drop in replacements please [contact sales](https://www.herodevs.com/contact).

**🟡 Caution:** This repository demonstrates a quick and simple way to integrate with the HeroDevs NES repository. Usernames and password values are hard coded in Maven and Gradle settings files. When moving to production, take care of your username and password values in a more secure way.

# Pet Clinic Lite

This application stores Pet data in a local H2 database. The information is automatically loaded when the Spring Boot application starts and is exposed via REST endpoints.

This application uses:
- Spring Boot 2.7
- Spring Framework 5.3
- Spring Security 5.7
- Spring Data JPA 2.7

These versions are managed by Spring Boot.

# Build System Choice
This project has two folders, `maven` and `gradle` for the respective build systems. The code (located in `src`) is the same for both build systems. The only difference is the build system itself. The `maven` folder contains a `pom.xml` file and the `gradle` folder contains a `build.gradle` file. You can use either one to build the project.

Notes are below on the specific changes required for each build system.

___

## Maven

### Changes to `settings.xml`:
- Add the HeroDevs repository URL and credentials:
```xml
<settings>
  <servers>
    <server>
      <id>herodevs-nes-registry</id>
      <username>any_text_here_not_used</username>
      <password>YOUR_NES_ACCESS_TOKEN</password>
    </server>
  </servers>
</settings>
```

### Changes to `pom.xml`:
- Add the HeroDevs repository to the `repositories` and `pluginRepositories` blocks:
```xml
<repositories>
    <repository>
        <id>herodevs-nes-registry</id>
        <url>https://registry.nes.herodevs.com/maven</url>
    </repository>
</repositories>

<pluginRepositories>
    <pluginRepository>
        <id>herodevs-nes-registry</id>
        <url>https://registry.nes.herodevs.com/maven</url>
    </pluginRepository>
</pluginRepositories>
```

- Update the Spring Boot dependency to use the HeroDevs NES for Spring Boot trial version:
```xml
<version>2.7.18-spring-boot-2.7.20-trial</version>
```

### Testing

Verify that the application is using HeroDevs NES for Spring dependencies by running the following command:
```bash
./mvnw dependency:tree
```

You should see dependencies like these in the output to signify that you are using HeroDevs NES for Spring:
```
org.springframework.boot:spring-boot-starter-aop:jar:2.7.18-spring-boot-2.7.20-trial:compile
org.springframework:spring-context:jar:5.3.39-spring-framework-5.3.41-trial:compile
org.springframework.boot:spring-boot-starter-jdbc:jar:2.7.18-spring-boot-2.7.20-trial:compile
```

___

## Gradle

### Changes to `gradle.properties`:

- Add the HeroDevs repository URL and credentials:
```properties
herodevs_nes_registry_url=https://registry.nes.herodevs.com/maven
herodevs_nes_registry_user=any_text_here_not_used
herodevs_nes_registry_token=NES_TOKEN_HERE
```

### Changes to `settings.gradle`:
- Add the HeroDevs repository to the `pluginManagement` block
  - The `pluginManagement` block must be the first element in the file
  - The HeroDevs repository must appear before `mavenCentral()`
```groovy
pluginManagement {
  repositories {
    maven {
      url = uri(providers.gradleProperty("herodevs_nes_registry_url").get())
      credentials {
        username = providers.gradleProperty("herodevs_nes_registry_user").get()
      }
    }
    mavenCentral()
  }
}
```

### Changes to `build.gradle`:

- Add the HeroDevs repository to the `repositories` block:
```groovy
repositories {
    maven {
        url = uri(providers.gradleProperty("herodevs_nes_registry_url").get())
        credentials {
            username = providers.gradleProperty("herodevs_nes_registry_user").get()
        }
    }
    mavenCentral()
}
```
- Update the Spring Boot dependency to use the HeroDevs NES for Spring Boot trial version:
```groovy
id 'org.springframework.boot' version '2.7.18-spring-boot-2.7.20-trial'
```
### Testing

Verify that the application is using HeroDevs NES for Spring dependencies by running the following command:
```bash
./gradlew clean dependencies --configuration runtimeClasspath
```

You should see dependencies like these in the output to signify that you are using HeroDevs NES for Spring:
```
org.springframework.boot:spring-boot-starter-aop:2.7.18-spring-boot-2.7.20-trial
org.springframework:spring-context:5.3.39-spring-framework-5.3.41-trial
org.springframework.boot:spring-boot-starter-jdbc:2.7.18-spring-boot-2.7.20-trial
```

Congratulations! Your Spring Gradle project is ready to be secure. [Contact HeroDevs](https://www.herodevs.com/contact) for a registry token to get full access. Simply change the `herodevs_nes_registry_token` field in `gradle.properties` to your specific token and the next build will use NES for Spring dependencies with zero CVEs.

___

# Running the Application

Run the application by executing the corresponding command for your build system.

Maven:
```bash
./mvnw spring-boot:run
```
Gradle:
```bash
./gradlew bootRun
```

When the application is running, verify that the unsecured endpoint is reachable:
```bash
curl http://localhost:8080/helloPets
```
You should see the following output:
```
Woof! Meow! Blub! Tweet!
```

To access the other REST endpoints, you need to authenticate. The application uses Spring Security to secure the endpoints. You can use the following command to pass the simple authentication:
```bash
curl -u user:password "http://localhost:8080/pets?name=Buddy"
```
You should see the following output:
```
[{"name":"Buddy","type":"Dog"}]
```