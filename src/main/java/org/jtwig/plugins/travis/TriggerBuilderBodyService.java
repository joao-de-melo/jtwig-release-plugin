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
                .put("request",
                        new JSONObject()
                                .put("message", String.format("Triggered release from downstream project %s", request.getParentProject()))
                                .put("config", new JSONObject()
                                        .put("env", new JSONObject()
                                                .put(versionVariable, version)
                                        )
                                )
                );
    }
}
