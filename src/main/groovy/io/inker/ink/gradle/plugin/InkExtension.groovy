package io.inker.ink.gradle.plugin

import io.inker.ink.gradle.type.BaseExtension
import io.inker.ink.gradle.utils.FileUtils
import org.gradle.api.Project

class InkExtension extends BaseExtension {
    /**
     * The list of disabled rules, separated by ','
     * Note: It can override the config in {@link #inkConfig}
     *
     * Example:
     * ink {
     *     disable 'ConcurrentModificationException', 'ThreadPriority'
     * }
     */
    String[] disable

    /**
     * The list of enabled rules, separated by ','
     * Note: It can override the config in {@link #inkConfig}
     *
     * Example:
     * ink {
     *     enable 'ConcurrentModificationException', 'ThreadPriority'
     * }
     */
    String[] enable

    /**
     * The list of rules to be run, separated by ','
     * Note: It can override other settings, such as {@link #disable}, {@link #enable} and {@link #inkConfig}.
     * And ink will run only these specified rules.
     *
     * Example:
     * ink {
     *     check 'ConcurrentModificationException', 'ThreadPriority'
     * }
     */
    String[] check

    /**
     * The rule configuration file
     *
     * Example:
     * ink {
     *     inkConfig file('inkConfig_test.xml')
     * }
     */
    File inkConfig

    /**
     * true, enable text output
     * false, disable
     * Note: default setting is TRUE
     *
     * Example:
     * ink {
     *     textReport false
     * }
     */
    boolean textReport
    /**
     * The text-format report file
     *
     * Example:
     * ink {
     *     textReport true
     *     textReportFile 'ink-report.txt
     *     textReportFile file('ink-report.txt')
     * }
     */
    File textReportFile

    /**
     * true, enable html output
     * false, disable
     * Note: default setting is FALSE
     *
     * Example:
     * ink {
     *     htmlReport true
     * }
     */
    boolean htmlReport
    /**
     * The html-format report file
     *
     * Example:
     * ink {
     *     htmlReport true
     *     htmlReportFile 'ink-report.html'
     *     htmlReportFile file('ink-report.html')
     * }
     */
    File htmlReportFile

    /**
     * true, enable xml output
     * false, disable
     * Note: default setting is FALSE
     *
     * Example:
     * ink {
     *     xmlReport false
     * }
     */
    boolean xmlReport
    /**
     * The xml-format report file
     *
     * Example:
     * ink {
     *     xmlReport true
     *     xmlReportFile 'ink-report.xml'
     *     xmlReportFile file('ink-report.xml')
     * }
     */
    File xmlReportFile

    /**
     * The list of customized rules, each item is a .jar file, separated by ','
     *
     * Example:
     * ink {
     *     extendedRules file('rules1.jar'), file('rules2.jar')
     * }
     */
    File[] extendedRules

    /**
     * true, enable quiet mode, only minor important logs will be outputted
     * false, disable
     * Note: default setting is FALSE
     *
     * Example:
     * ink {
     *     quiet false
     * }
     */
    boolean quiet

    InkExtension(Project project) {
        super(project)

        textReport = true
        htmlReport = false
        xmlReport = false

        quiet = false
    }

    void setInkConfig(String inkConfig) {
        if (inkConfig != null && !inkConfig.isEmpty()) {
            if (inkConfig.startsWith('http://') ||
                    inkConfig.startsWith('https://') ||
                    inkConfig.startsWith('ftp://')) {
                this.inkConfig = FileUtils.downloadFile(inkConfig, project.buildDir.absolutePath)
            } else {
                this.inkConfig = FileUtils.safeCreateFile(inkConfig)
            }
        }
    }

    void setTextReportFile(String textReportFile) {
        this.textReportFile = FileUtils.safeCreateFile(textReportFile)
    }

    void setHtmlReportFile(String htmlReportFile) {
        this.htmlReportFile = FileUtils.safeCreateFile(htmlReportFile)
    }

    void setXmlReportFile(String xmlReportFile) {
        this.xmlReportFile = FileUtils.safeCreateFile(xmlReportFile)
    }

    void setExtendedRules(String... extendedRules) {
        if (extendedRules != null) {
            ArrayList<File> extendedRuleList = new ArrayList<>()

            String downloadDir = project.projectDir.absolutePath + '/lint-jars'
            extendedRules.each {
                if (it.startsWith('http://') ||
                        it.startsWith('https://') ||
                        it.startsWith('ftp://')) {
                    File extendedRuleFile = FileUtils.downloadFile(it, downloadDir)
                    if (extendedRuleFile != null) {
                        extendedRuleList.add(extendedRuleFile)
                    }
                } else {
                    extendedRuleList.add(FileUtils.safeCreateFile(it))
                }
            }

            this.extendedRules = extendedRuleList.toArray(this.extendedRules)
        }
    }
}
