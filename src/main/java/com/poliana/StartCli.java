package com.poliana;

import com.poliana.config.CliConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.logging.Logger;

public class StartCli {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext ctx =
//                new AnnotationConfigApplicationContext("com/poliana/config");

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles("production");
        ctx.setEnvironment(environment);
        ctx.scan("com/poliana/config");

        try {
            CliConfig cliConfig = new CliConfig(ctx);
            String[] options = {};
            ExitShellRequest exitShellRequest = cliConfig.run(null);
        } catch (RuntimeException t) {
            throw t;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HandlerUtils.flushAllHandlers(Logger.getLogger(""));
        }
    }
}