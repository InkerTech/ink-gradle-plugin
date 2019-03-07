package io.inker.ink.gradle.plugin

import io.inker.ink.gradle.type.BaseExtension
import io.inker.ink.gradle.utils.FileUtils
import org.gradle.api.Project

class InkExtension extends BaseExtension {
    /**
     * The home directory of Ink analyzer
     */
    String inkHome

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
     *     textReportFile 'ink-report.txt'
     * }
     */
    String textReportFile

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
     * }
     */
    String htmlReportFile

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
     * }
     */
    String xmlReportFile

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

    /**
     * Ink working directory
     */
    private String inkDir

    /**
     * Ink reports sub-directory
     */
    private String inkReportsDir

    InkExtension(Project project) {
        super(project)

        textReport = true
        htmlReport = false
        xmlReport = false

        quiet = false

        inkDir = "${project.projectDir.absolutePath}/${InkConstants.INK_DIR}"
        inkReportsDir = "${inkDir}/${InkConstants.INK_SUBDIR_REPORTS}"
    }

    void setInkConfig(String inkConfig) {
        if (inkConfig != null && !inkConfig.isEmpty()) {
            File file
            if (FileUtils.isFileUrl(inkConfig)) {
                file = FileUtils.downloadFile(inkConfig, "${inkDir}/${InkConstants.INK_SUBDIR_CONFIG}")
            } else {
                file = FileUtils.getFile(inkConfig)
            }

            if (file != null) {
                this.inkConfig = file
            }
        }
    }

    void setInkConfig(File inkConfig) {
        if (inkConfig != null) {
            this.inkConfig = inkConfig
        }
    }

    void setTextReportFile(String textReportFile) {
        if (textReportFile == null || textReportFile.isEmpty()) {
            textReportFile = "${inkReportsDir}/${project.name}.txt"
        }
        this.textReportFile = textReportFile
    }

    void setHtmlReportFile(String htmlReportFile) {
        if (htmlReportFile == null || htmlReportFile.isEmpty()) {
            htmlReportFile = "${inkReportsDir}/${project.name}.html"
        }
        this.htmlReportFile = htmlReportFile
    }

    void setXmlReportFile(String xmlReportFile) {
        if (xmlReportFile == null || xmlReportFile.isEmpty()) {
            xmlReportFile = "${inkReportsDir}/${project.name}.xml"
        }
        this.xmlReportFile = xmlReportFile
    }

    void setExtendedRules(String... extendedRules) {
        if (extendedRules != null) {
            ArrayList<File> extendedRuleList = new ArrayList<>()

            String downloadDir = "${inkDir}/${InkConstants.INK_SUBDIR_EXTENDED_RULES}"
            extendedRules.each {
                File extendedRuleFile
                if (FileUtils.isFileUrl(it)) {
                    extendedRuleFile = FileUtils.downloadFile(it, downloadDir)
                } else {
                    extendedRuleFile = FileUtils.getFile(it)
                }

                if (extendedRuleFile != null) {
                    extendedRuleList.add(extendedRuleFile)
                }
            }

            this.extendedRules = (File[])extendedRuleList.toArray()
        }
    }
}
