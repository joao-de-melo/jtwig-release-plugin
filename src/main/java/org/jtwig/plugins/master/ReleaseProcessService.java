package org.jtwig.plugins.master;

import org.jtwig.plugins.travis.TriggerBuildService;

import java.util.List;

public class ReleaseProcessService {
    private final String travisBaseUrl;
    private final String travisToken;
    private final ResolveProjectDependenciesService resolveProjectDependenciesService;
    private final CheckVersionReleasedService projectVersionReleased;
    private final TriggerBuildService triggerBuildService;

    public ReleaseProcessService(String travisBaseUrl, String travisToken, ResolveProjectDependenciesService resolveProjectDependenciesService, CheckVersionReleasedService projectVersionReleased, TriggerBuildService triggerBuildService) {
        this.travisBaseUrl = travisBaseUrl;
        this.travisToken = travisToken;
        this.resolveProjectDependenciesService = resolveProjectDependenciesService;
        this.projectVersionReleased = projectVersionReleased;
        this.triggerBuildService = triggerBuildService;
    }

    public void release(List<String> projects, String version) {
        List<String> ordered = resolveProjectDependenciesService.resolveDependencies(projects);

        for (String project : ordered) {
            if (!projectVersionReleased.released(project, version)) {
                triggerBuildService.trigger(travisBaseUrl, travisToken, "jtwig/" + project, version);
                waitForRelease(project, version);
            }
        }
    }

    private void waitForRelease(String project, String version) {
        System.out.println(String.format("Waiting for project %s to be released with version %s", project, version));
        boolean released = false;

        while (!released) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            released = projectVersionReleased.released(project, version);
        }
    }
}
