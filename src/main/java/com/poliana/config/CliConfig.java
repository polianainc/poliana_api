
package com.poliana.config;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.Converter;
import org.springframework.shell.core.ExitShellRequest;
import org.springframework.shell.core.JLineShellComponent;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.Map;

public class CliConfig {

    private JLineShellComponent shell;
    private ConfigurableApplicationContext ctx;
    private static StopWatch sw = new StopWatch("Spring Shell");

    public CliConfig(AnnotationConfigApplicationContext ctx) throws IOException {
        initCommandContext(ctx);

        this.ctx = ctx;

        shell = ctx.getBean("shell", JLineShellComponent.class);
        shell.setApplicationContext(ctx);

        Map<String, CommandMarker> commands = BeanFactoryUtils.beansOfTypeIncludingAncestors(ctx, CommandMarker.class);
        for (CommandMarker command : commands.values()) {
            shell.getSimpleParser().add(command);
        }

        Map<String, Converter> converters = BeanFactoryUtils.beansOfTypeIncludingAncestors(ctx, Converter.class);
        for (Converter converter : converters.values()) {
            shell.getSimpleParser().add(converter);
        }
    }

    public ExitShellRequest run(String[] executeThenQuit) {

        ExitShellRequest exitShellRequest;

        if (null != executeThenQuit) {
            boolean successful = false;
            exitShellRequest = ExitShellRequest.FATAL_EXIT;

            for (String cmd : executeThenQuit) {
                successful = shell.executeCommand(cmd);
                if (!successful)
                    break;
            }

            //if all commands were successful, set the normal exit status
            if (successful) {
                exitShellRequest = ExitShellRequest.NORMAL_EXIT;
            }
        }
        else {
            shell.start();
            shell.promptLoop();
            exitShellRequest = shell.getExitShellRequest();
            if (exitShellRequest == null) {
                // shouldn't really happen, but we'll fallback to this anyway
                exitShellRequest = ExitShellRequest.NORMAL_EXIT;
            }
            shell.waitForComplete();
        }

        ctx.close();

        return exitShellRequest;
    }

    public void initCommandContext(AnnotationConfigApplicationContext annctx) {
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.StringConverter.class);
        createAndRegisterBeanDefinition(annctx,
                org.springframework.shell.converters.AvailableCommandsConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.BigDecimalConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.BigIntegerConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.BooleanConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.CharacterConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.DateConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.DoubleConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.EnumConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.FloatConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.IntegerConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.LocaleConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.LongConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.ShortConverter.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.StaticFieldConverterImpl.class);
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.core.JLineShellComponent.class, "shell");
        createAndRegisterBeanDefinition(annctx, org.springframework.shell.converters.SimpleFileConverter.class);

        annctx.scan("org.springframework.shell.commands");
        annctx.scan("org.springframework.shell.converters");
        annctx.scan("org.springframework.shell.plugin.support");
    }


    protected static void createAndRegisterBeanDefinition(AnnotationConfigApplicationContext annctx, Class clazz) {
        createAndRegisterBeanDefinition(annctx, clazz, null);
    }

    protected static void createAndRegisterBeanDefinition(AnnotationConfigApplicationContext annctx, Class clazz, String name) {
        RootBeanDefinition rbd = new RootBeanDefinition();
        rbd.setBeanClass(clazz);
        if (name != null) {
            annctx.registerBeanDefinition(name, rbd);
        }
        else {
            annctx.registerBeanDefinition(clazz.getSimpleName(), rbd);
        }
    }
}