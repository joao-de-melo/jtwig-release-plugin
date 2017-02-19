package org.jtwig.plugins.config

import org.gradle.api.Project

public class ReleaseGithubExtension {
    String apiBaseUrl = "https://api.github.com"
    String username
    String token
    String owner = "jtwig"
    String repository


    ReleaseGithubExtension(Project project) {
        this.repository = project.getName();
    }
}
