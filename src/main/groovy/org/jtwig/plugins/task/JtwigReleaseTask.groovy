package org.jtwig.plugins.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jtwig.plugins.environment.Environment

class JtwigReleaseTask extends DefaultTask {
    public static final String TASK_NAME = "jtwigRelease";
    public static final String GROUP = "Jtwig Release"

    @Override
    String getGroup() {
        return GROUP
    }

    @Override
    String getDescription() {
        return "Task to release Jtwig"
    }

    @TaskAction
    public void release () {
        if (Environment.version(TriggerTravisTask.VERSION_VARIABLE).isPresent()) {
            println "Released"
        } else {
            println "Not released"
        }
    }
}
