package com.poliana.cli;

import com.poliana.config.cli.CliShell;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.support.logging.HandlerUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class StartCli {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("com/poliana/config/cli");

        try {
            CliShell cliShell = new CliShell(ctx);
            String[] options = {};
            ExitShellRequest exitShellRequest = cliShell.run(null);
        } catch (RuntimeException t) {
            throw t;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HandlerUtils.flushAllHandlers(Logger.getLogger(""));
        }
    }
}