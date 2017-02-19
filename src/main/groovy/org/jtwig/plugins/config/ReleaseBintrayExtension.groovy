package org.jtwig.plugins.config

import org.gradle.api.Project

public class ReleaseBintrayExtension {
    String bintrayBaseUrl = "https://bintray.com/api/v1";
    String user;
    String apiKey;
    String subject = "jtwig";
    String packageName;
    String website = "http://jtwig.org";
    String trackerUrl = "https://github.com/jtwig/jtwig/issues";


    ReleaseBintrayExtension(Project project) {
        this.packageName = project.getName();
    }
}
