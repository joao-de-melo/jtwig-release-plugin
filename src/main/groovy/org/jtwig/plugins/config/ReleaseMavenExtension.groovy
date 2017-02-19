package org.jtwig.plugins.config

import org.gradle.api.Project


public class ReleaseMavenExtension {
    public static ReleaseMavenExtension retrieve (Project project) {
        ReleaseExtension.retrieve(project).maven;
    }

    Map<String, String> configurationsScope = [compile: "compile", provided: "provided", runtime: "runtime"];


}
