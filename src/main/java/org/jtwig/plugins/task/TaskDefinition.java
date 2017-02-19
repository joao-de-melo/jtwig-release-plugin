package org.jtwig.plugins.task;

import org.gradle.api.Action;
import org.gradle.api.Task;

public class TaskDefinition {
    private final String name;
    private final String description;
    private final Action<Task> action;

    public TaskDefinition(String name, String description, Action<Task> action) {
        this.name = name;
        this.description = description;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Action<Task> getAction() {
        return action;
    }
}
