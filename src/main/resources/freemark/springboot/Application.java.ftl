package ${basePackageName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * Application
 * </p>
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
 **/

<#noparse>
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
</#noparse>
