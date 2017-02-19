package org.jtwig.plugins.task

import org.apache.commons.lang3.StringUtils
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.jtwig.plugins.bintray.http.BintrayHttpClient
import org.jtwig.plugins.bintray.http.BintrayHttpClientFactory
import org.jtwig.plugins.bintray.http.auth.BintrayAuthentication
import org.jtwig.plugins.bintray.model.BintrayPackage
import org.jtwig.plugins.bintray.model.BintrayPackageVersion
import org.jtwig.plugins.bintray.model.BintrayRepository
import org.jtwig.plugins.bintray.model.License
import org.jtwig.plugins.bintray.services.PackagesService
import org.jtwig.plugins.bintray.services.UploadsService
import org.jtwig.plugins.bintray.services.VersionsService
import org.jtwig.plugins.bintray.services.model.CreatePackageRequest
import org.jtwig.plugins.bintray.services.model.CreateVersionRequest
import org.jtwig.plugins.bintray.services.model.UploadArtifactRequest
import org.jtwig.plugins.config.ReleaseBintrayExtension
import org.jtwig.plugins.config.ReleaseExtension
import org.jtwig.plugins.util.ProducedFile

class UploadBintrayTask extends DefaultTask {
    public static final String TASK_NAME = "jtwigReleaseBintrayUploadArtifacts";

    public static void create (Project project) {
        project.task(TASK_NAME, type: UploadBintrayTask)
                .dependsOn(
                GenerateMavenPomTask.TASK_NAME,
                GenerateSourcesJarTask.TASK_NAME,
                GenerateJarTask.TASK_NAME,
                GenerateJavadocJarTask.TASK_NAME
        );
    }

    @Override
    String getGroup() {
        return JtwigReleaseTask.GROUP;
    }

    @Override
    String getDescription() {
        return "Publishes artifacts in bintray"
    }


    @TaskAction
    public void upload() {
        ReleaseExtension releaseExtension = ReleaseExtension.retrieve(getProject());

        validate(releaseExtension.getBintray())

        String baseUrl = releaseExtension.getBintray().getBintrayBaseUrl()
        BintrayHttpClient httpClient = BintrayHttpClientFactory.create(
                baseUrl,
                new BintrayAuthentication(
                        releaseExtension.getBintray().getUser(),
                        releaseExtension.getBintray().getApiKey()
                )
        );
        PackagesService packagesService = new PackagesService(httpClient);

        BintrayPackage bintrayPackage = new BintrayPackage(
                BintrayRepository.maven(releaseExtension.getBintray().getSubject()),
                releaseExtension.getBintray().getPackageName()
        );

        if (!packagesService.exists(baseUrl, bintrayPackage)) {
            packagesService.create(baseUrl, new CreatePackageRequest(
                    bintrayPackage,
                    "Package for " + bintrayPackage.getName(),
                    Collections.emptyList(),
                    Collections.singletonList(License.APACHE_2),
                    String.format("%s/%s", releaseExtension.getGithub().getOwner(), releaseExtension.getGithub().getRepository()),
                    releaseExtension.getBintray().getWebsite(),
                    releaseExtension.getBintray().getTrackerUrl(),
                    true,
                    true
            ));
        }

        VersionsService versionsService = new VersionsService(httpClient);
        BintrayPackageVersion packageVersion = new BintrayPackageVersion(bintrayPackage, releaseExtension.getVersion())

        if (!versionsService.versionExists(baseUrl, bintrayPackage, releaseExtension.getVersion())) {
            versionsService.create(baseUrl, new CreateVersionRequest(
                    packageVersion,
                    "Version " + releaseExtension.version
            ));
        }

        UploadsService uploadsService = new UploadsService(httpClient);
        for (ProducedFile producedFile : ProducedFile.values()) {
            uploadsService.upload(baseUrl, new UploadArtifactRequest(
                    packageVersion,
                    producedFile.bintrayPath(getProject()),
                    true,
                    producedFile.file(getProject())
            ))
        }
    }

    static void validate(ReleaseBintrayExtension releaseBintrayExtension) {
        if (StringUtils.isBlank(releaseBintrayExtension.user)) throw new RuntimeException(String.format("%s.bintray.user is undefined", ReleaseExtension.EXTENSION));
        if (StringUtils.isBlank(releaseBintrayExtension.apiKey)) throw new RuntimeException(String.format("%s.bintray.apiKey is undefined", ReleaseExtension.EXTENSION));
    }
}
