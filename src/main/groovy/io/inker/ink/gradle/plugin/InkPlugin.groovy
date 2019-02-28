package io.inker.ink.gradle.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class InkPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        initPlugin(project)

        addInkTask(project)
    }

    private void initPlugin(Project project) {
        project.extensions.create('ink', InkExtension.class, project)
    }

    private void addInkTask(Project project) {
        project.afterEvaluate {
            InkExtension inkExtension = project.extensions.getByType(InkExtension.class)
        }
    }
}
