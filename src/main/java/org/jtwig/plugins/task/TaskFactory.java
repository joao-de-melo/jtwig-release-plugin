package org.jtwig.plugins.task;

import org.gradle.api.Project;
import org.gradle.api.Task;

public class TaskFactory {
    public static final String JTWIG_RELEASE = "Jtwig Release";

    public static Task create (Project project, TaskDefinition definition) {
        Task task = project.task(definition.getName());
        task.setGroup(JTWIG_RELEASE);
        task.setDescription(definition.getDescription());
        task.doFirst(definition.getAction());

        return task;
    }
}
