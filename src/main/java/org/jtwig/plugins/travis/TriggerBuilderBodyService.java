package org.jtwig.plugins.travis;

import org.json.JSONObject;

public class TriggerBuilderBodyService {
    private final String versionVariable;
    private final String version;

    public TriggerBuilderBodyService(String versionVariable, String version) {
        this.versionVariable = versionVariable;
        this.version = version;
    }

    public JSONObject generate (TriggerBuildRequest request) {
        return new JSONObject()
                .append("request",
                        new JSONObject()
                                .append("message", String.format("Triggered release from downstream project %s", request.getParentProject()))
                                .append("config", new JSONObject()
                                        .append("env", new JSONObject()
                                                .append(versionVariable, version)
                                        )
                                )
                );
    }
}
