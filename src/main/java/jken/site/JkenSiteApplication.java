package jken.site;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class JkenSiteApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(JkenSiteApplication.class).run(args);
    }
}
