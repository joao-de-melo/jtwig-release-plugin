package org.jtwig.plugins.git;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jtwig.plugins.util.UrlBuilder;

import java.io.File;

public class GitBranchService {
    public void createBranch(GitBranchRequest request) throws Exception {
        FileUtils.deleteQuietly(request.getDirectory());
        request.getDirectory().mkdirs();

        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
                request.getUsername(),
                request.getPassword()
        );

        Git git = Git.cloneRepository()
                .setDirectory(request.getDirectory())
                .setURI(UrlBuilder.url(request.getBaseUrl())
                        .addToPath(request.getOwner())
                        .addToPath(request.getRepository()+"")
                        .build())
                .setRemote("origin")
                .setCredentialsProvider(credentialsProvider)
                .call();

        FileUtils.writeStringToFile(new File(request.getDirectory(), request.getVersionFileName()), request.getVersion());

        git.checkout()
                .setCreateBranch(true)
                .setName(request.getVersion())
                .call();

        git.add().addFilepattern(request.getVersionFileName()).call();

        git.commit()
                .setMessage("Branch for release "+request.getVersion())
                .setAuthor("Jtwig Release Plugin", "bot@jtwig.org")
                .call();

        git.push()
                .setRemote("origin")
                .setRefSpecs(new RefSpec(request.getVersion()+":"+request.getVersion()))
                .setCredentialsProvider(credentialsProvider)
                .call();

    }
}
