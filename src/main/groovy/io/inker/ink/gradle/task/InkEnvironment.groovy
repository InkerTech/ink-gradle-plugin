package io.inker.ink.gradle.task

class InkEnvironment {
    String inkHome

    String[] disable
    String[] enable
    String[] check
    File inkConfig

    boolean textReport
    String textReportFile

    boolean htmlReport
    String htmlReportFile

    boolean xmlReport
    String xmlReportFile

    File[] extendedRules

    boolean quiet
}
