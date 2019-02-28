package io.inker.ink.gradle.plugin


import io.inker.ink.gradle.type.BaseExtension
import org.gradle.api.Project

class InkExtension extends BaseExtension {
    String disable          // 需disable的规则列表
    String enable           // 需enable的规则列表
    String check            // 指定扫描的规则列表
    File inkConfig         // 规则列表配置文件
    File textOutput         // TextReporter
    File htmlOutput         // HtmlReporter
    File xmlOutput          // XmlReporter
    Set<String> customRules = new HashSet<>()     // 自定义规则库Jar包列表

    boolean quiet = false

    InkExtension(Project project) {
        super(project)
    }

    void setDisable(String disable) {
        this.disable = disable
    }

    void setEnable(String enable) {
        this.enable = enable
    }

    void setCheck(String check) {
        this.check = check
    }

    void setInkConfig(String inkConfig) {
        if (inkConfig != null && !inkConfig.isEmpty()) {
            if (inkConfig.startsWith('http://') ||
                    inkConfig.startsWith('https://') ||
                    inkConfig.startsWith('ftp://')) {
                this.inkConfig = FileUtils.downloadFile(inkConfig, project.buildDir.absolutePath)
            } else {
                this.inkConfig = new File(inkConfig)
            }
        }
    }

    void setInkConfig(File inkConfig) {
        this.inkConfig = inkConfig
    }

    void setTextOutput(String textOutput) {
        this.textOutput = FileUtils.safeCreateFile(textOutput)
    }

    void setTextOutput(File textOutput) {
        this.textOutput = textOutput
    }

    void setHtmlOutput(String htmlOutput) {
        this.htmlOutput = FileUtils.safeCreateFile(htmlOutput)
    }

    void setHtmlOutput(File htmlOutput) {
        this.htmlOutput = htmlOutput
    }

    void setXmlOutput(String xmlOutput) {
        this.xmlOutput = FileUtils.safeCreateFile(xmlOutput)
    }

    void setXmlOutput(File xmlOutput) {
        this.xmlOutput = xmlOutput
    }

    void setCustomRule(String customRule) {
        if (customRule != null && !customRule.isEmpty()) {
            String downloadDir = project.projectDir.absolutePath + '/lint-jars'
            if (customRule.startsWith('http://') ||
                    customRule.startsWith('https://') ||
                    customRule.startsWith('ftp://')) {
                File customRuleFile = FileUtils.downloadFile(customRule, downloadDir)
                if (customRuleFile != null) {
                    this.customRules.add(customRuleFile.absolutePath)
                }
            } else {
                this.customRules.add(customRule)
            }
        }
    }

    void setQuiet(boolean quiet) {
        this.quiet = quiet
    }
}
