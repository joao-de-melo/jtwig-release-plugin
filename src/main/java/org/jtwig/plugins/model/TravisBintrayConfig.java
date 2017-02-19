package org.jtwig.plugins.model;

public class TravisBintrayConfig {
    private TravisConfig travis = new TravisConfig();

    public TravisConfig getTravis() {
        return travis;
    }

    public void setTravis(TravisConfig travis) {
        this.travis = travis;
    }
}
