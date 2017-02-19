package org.jtwig.plugins.config

import org.gradle.api.Project

public class ReleaseExtension {
    public static final String EXTENSION = "jtwigRelease";

    public static ReleaseExtension retrieve (Project project) {
        return project.getExtensions().getByName(EXTENSION);
    }
    public static ReleaseExtension create (Project project) {
        project.getExtensions().create(EXTENSION, ReleaseExtension, project)
    }

    String version;
    String group = "org.jtwig";
    String destinationDir = "jtwig-release";
    String versionProperty = "jtwigVersion";

    ReleaseGitExtension git;
    ReleaseGithubExtension github;
    ReleaseMavenExtension maven;
    ReleaseBintrayExtension bintray;
    ReleaseTravisExtension travis;

    private final Project project;

    public ReleaseExtension(Project project) {
        this.project = project
        this.maven = new ReleaseMavenExtension();
        this.travis = new ReleaseTravisExtension();
        this.git = new ReleaseGitExtension(project);
        this.github = new ReleaseGithubExtension(project);
        this.bintray = new ReleaseBintrayExtension(project);
    }

    ReleaseGitExtension git (Closure closure) {
        project.configure(this.git, closure);
        return this.git;
    }

    ReleaseGithubExtension github (Closure closure) {
        project.configure(this.github, closure);
        return this.github;
    }

    ReleaseMavenExtension maven (Closure closure) {
        project.configure(this.maven, closure);
        return this.maven;
    }

    ReleaseBintrayExtension bintray (Closure closure) {
        project.configure(this.bintray, closure);
        return this.bintray;
    }

    ReleaseTravisExtension travis (Closure closure) {
        project.configure(this.travis, closure);
        return this.travis;
    }
}
