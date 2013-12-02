package com.poliana;

import com.poliana.config.CliConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.support.logging.HandlerUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class StartCli {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("com/poliana/config");

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