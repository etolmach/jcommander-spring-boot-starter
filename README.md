# JCommander integration with SpringBoot
[![Master Branch Build Status](https://travis-ci.org/etolmach/jcommander-spring-boot-starter.svg?branch=master)](https://travis-ci.org/etolmach/jcommander-spring-boot-starter/builds) 
[![codecov](https://codecov.io/gh/etolmach/jcommander-spring-boot-starter/branch/master/graph/badge.svg)](https://codecov.io/gh/etolmach/jcommander-spring-boot-starter)
[![Requirements Status](https://requires.io/github/etolmach/jcommander-spring-boot-starter/requirements.svg?branch=master)](https://requires.io/github/etolmach/jcommander-spring-boot-starter/requirements/?branch=master)

> "Because life is too short to parse command line parameters"
>
> *-Cédric Beust*

JCommander Spring-Boot-Starter will help you to use [JCommander](http://jcommander.org) with ease.

For more detailed explanation and code samples please consult the [home page](http://jcommander.spring.etolmach.com).

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
            String template;
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
    java -jar hello.jar greeting -name=Jack -lang=es
    ```
5. Output
    ```
    ¡Hola Jack!
    ```

**Configuration**

By default the starter automatically listens for command-line arguments on application start-up. You can disable this option by setting parameter:
```
jcommander.runner.enabled=false    
```
    
**Requirements**
- JDK 1.8+
- SpringBoot 2+