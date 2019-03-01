package io.inker.ink.gradle.type

import org.gradle.api.Project

class BaseExtension {
    protected Project project

    BaseExtension(Project project) {
        this.project = project
    }
}
