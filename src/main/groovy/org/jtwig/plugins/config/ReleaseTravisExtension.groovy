package org.jtwig.plugins.config

public class ReleaseTravisExtension {
    String baseUrl = "https://api.travis-ci.org"
    String branch = "master"
    String token
    List<String> upstreamProjects = []
}
