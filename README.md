# JCommander integration with SpringBoot

JCommander Spring-Boot-Starter will help you to use JCommander with ease.

**Hello World**

1. Add jcommander-spring-boot-starter to your SpringBoot project
    ```xml
    <dependency>
        <groupId>com.etolmach.spring.boot</groupId>
        <artifactId>jcommander-spring-boot-starter</artifactId>
        <version>${jcommander.starter.version}</version>
    </dependency>
    ```

2. Define your command parameters bean
    ```java
    @Data
    @Component
    @Parameters(
        commandNames = "greeting",
        commandDescription = "Say hi to user",
        separators = "="
    )
    public class GreetingCommand {
        @Parameter(names = {"-name", "-n"},  description = "User name", required = true)
        private String name;
     
        @Parameter(names = {"-lang"},  description = "Language")
        private String topic = "en";
    }
    ```
    For more details consult [JCommander guide](http://jcommander.org).

3. Define your command controller bean
    ```java
    @CommandController
    public class HelloWorldController {
        
        private static final String EN_TEMPLATE = "Hello %s!";
        private static final String ES_TEMPLATE = "¡Hola %s!";
        private static final String RU_TEMPLATE = "Привет %s!";
    
        @CommandHandler(command = "greeting")
        public void greet(
            @CommandParameter(name = "-name") String name,
            @CommandParameter(name = "-lang") String language
        ) {  
            String template = null;
            switch (language){  
                case "es":
                    template = ES_TEMPLATE;
                    break;   
                case "ru":
                    template = RU_TEMPLATE;
                    break;   
                case "en":
                default:
                    template = EN_TEMPLATE;
            }
            System.out.println(String.format(template, name));
        }
    }
    ```
4. Build and run
    ```
    java -jar target/hello-world-0.0.1-SNAPSHOT.jar -name=Jack -lang=es
    ```
5. Output
    ```
    ¡Hola Jack!
    ```

**Configuration**

By default the starter automatically listens for command-line arguments on application start-up. You can disable this option by setting parameter:
```
jcommander.commandline.runner.enabled=false    
```

**Versions**
- 0.1 - PoC 
- 0.2 - Added conditional auto-configuration
- 0.3 - Added thread safety
- 0.4 - Added support of @CommandParameter
- 0.5 - Improved exception management
- 0.6 - Added documentation
- 0.7 - (in-progress) Improved property management
- 0.8 - (planned) Unit tests
    
**Requirements**
- Java 8
- SpringBoot 2.0.0.M6