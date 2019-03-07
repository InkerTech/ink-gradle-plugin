package io.inker.ink.gradle.task

import io.inker.ink.gradle.plugin.InkConstants
import io.inker.ink.gradle.plugin.InkExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

class InkTask extends DefaultTask {
    private InkEnvironment env = new InkEnvironment()

    @Inject
    InkTask(InkExtension gradleParams) {
        if (gradleParams != null) {
            env.inkHome = gradleParams.inkHome
            if (env.inkHome == null || env.inkHome.isEmpty()) {
                env.inkHome = System.getenv(InkConstants.INK_HOME)
            }

            env.disable = gradleParams.disable
            env.enable = gradleParams.enable
            env.check = gradleParams.check
            env.inkConfig = gradleParams.inkConfig

            env.textReport = gradleParams.textReport
            env.textReportFile = gradleParams.textReportFile

            env.htmlReport = gradleParams.htmlReport
            env.htmlReportFile = gradleParams.htmlReportFile

            env.xmlReport = gradleParams.xmlReport
            env.xmlReportFile = gradleParams.xmlReportFile

            env.extendedRules = gradleParams.extendedRules

            env.quiet = gradleParams.quiet
        }
    }

    @TaskAction
    void analyze() {
        if (!validateInk()) {
            logger.error('Error : Cannot find ink.')
        }
    }

    private boolean validateInk() {
        if (env.inkHome == null || env.inkHome.isEmpty()) {
            return false
        }
        return true
    }
}
