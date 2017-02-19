package org.jtwig.plugins.config

import org.gradle.api.Project

public class ReleaseGitExtension {
    String baseUrl = "https://github.com"
    String username
    String password
    String owner = "jtwig"
    String repository

    ReleaseGitExtension(Project project) {
        this.repository = project.getName();
    }
}
