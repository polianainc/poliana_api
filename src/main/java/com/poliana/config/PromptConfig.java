package com.poliana.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * Prompt for the interactive shell
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PromptConfig implements PromptProvider {

    public String getPrompt() {
        return "data-manager>";
    }

    public String name() {
        return "poliana prompt provider";
    }
}
