package poliana.data;

import config.CliConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.support.logging.HandlerUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class Start {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("config");

        CliConfig.initCommandContext(ctx);

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