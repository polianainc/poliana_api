package com.poliana.shell;

import com.poliana.config.shell.DangerShell;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.support.logging.HandlerUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class StartShell {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("com/poliana/config/shell");

        try {
            DangerShell dangerShell = new DangerShell(ctx);
            ExitShellRequest exitShellRequest = dangerShell.run(null);
        } catch (RuntimeException t) {
            throw t;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            HandlerUtils.flushAllHandlers(Logger.getLogger(""));
        }
    }
}