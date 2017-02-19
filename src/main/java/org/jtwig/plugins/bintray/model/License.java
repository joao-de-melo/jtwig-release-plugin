package org.jtwig.plugins.bintray.model;

public enum License {
    APACHE_2("Apache-2.0");


    private final String value;

    License(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
