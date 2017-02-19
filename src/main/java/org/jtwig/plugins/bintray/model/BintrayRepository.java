package org.jtwig.plugins.bintray.model;

public class BintrayRepository {
    public static BintrayRepository maven (String subject) {
        return new BintrayRepository(subject, "maven");
    }

    private final String subject;
    private final String repository;

    public BintrayRepository(String subject, String repository) {
        this.subject = subject;
        this.repository = repository;
    }


    public String getPath() {
        return subject + "/" + repository;
    }
}
