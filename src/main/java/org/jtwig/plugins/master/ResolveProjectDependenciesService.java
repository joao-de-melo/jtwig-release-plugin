package org.jtwig.plugins.master;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ResolveProjectDependenciesService {

    private final GetProjectDependenciesService getProjectDependenciesService;

    public ResolveProjectDependenciesService(GetProjectDependenciesService getProjectDependenciesService) {
        this.getProjectDependenciesService = getProjectDependenciesService;
    }

    public List<String> resolveDependencies (List<String> projects) {
        DirectedAcyclicGraph<String, DefaultEdge> dependencies = new DirectedAcyclicGraph<>(DefaultEdge.class);

        for (String project : projects) {
            if (!dependencies.containsVertex(project)) dependencies.addVertex(project);
            List<String> dependsOn = getProjectDependenciesService.dependencies("jtwig/" + project);

            for (String dependency : dependsOn) {
                if (!dependencies.containsVertex(dependency)) dependencies.addVertex(dependency);
                try {
                    dependencies.addDagEdge(project, dependency);
                } catch (DirectedAcyclicGraph.CycleFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        List<String> result = new ArrayList<>();
        Iterator<String> iterator = dependencies.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            result.add(next);
        }
        Collections.reverse(result);
        return result;
    }
}
