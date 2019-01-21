package io.inker.ink.gradle.plugin

import io.inker.ink.gradle.task.InkExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class InkerPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        initPlugin(project)

        addInkTask(project)
    }

    private void initPlugin(Project project) {
        project.extensions.create('inker', InkerExtension.class, project)
    }

    private void addInkTask(Project project) {
        project.inker.extensions.create('ink', InkExtension.class, project)

        project.afterEvaluate {
            InkExtension inkExtension = project.inker.extensions.getByType(InkExtension.class)
        }
    }
}
