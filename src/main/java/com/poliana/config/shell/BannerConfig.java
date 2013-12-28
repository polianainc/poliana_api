package com.poliana.config.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

/**
 * Poliana's Banner provider.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BannerConfig implements BannerProvider {

    public String getBanner() {
        StringBuilder sb = new StringBuilder();
        sb.append(banner());
        sb.append(getVersion()).append(OsUtils.LINE_SEPARATOR);
        sb.append(OsUtils.LINE_SEPARATOR);

        return sb.toString();
    }

    public String getVersion() {
        return "Version 0.1";
    }

    public String getWelcomeMessage() {
        return "Yo, welcome to the " + name() + ". If you're a noob, type \"hint\"";
    }

    public String name() {
        return "Poliana Danger Shell";
    }

    private String banner() {
        return "\n\n             _ _                   _       _   _\n" +
                "            | (_)                 | |     | | (_)\n" +
                " _ __   ___ | |_  __ _ _ __   __ _| |_   _| |_ _  ___ ___\n" +
                "| '_ \\ / _ \\| | |/ _` | '_ \\ / _` | | | | | __| |/ __/ __|\n" +
                "| |_) | (_) | | | (_| | | | | (_| | | |_| | |_| | (__\\__ \\\n" +
                "| .__/ \\___/|_|_|\\__,_|_| |_|\\__,_|_|\\__, |\\__|_|\\___|___/\n" +
                "| |                                   __/ |\n" +
                "|_|                                  |___/\n\n";
    }
}