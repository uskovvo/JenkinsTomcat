pipeline {
    agent any
    tools{
    maven 'MavenForTomcatSrv'
    }
    stages {
        stage("git") {
            steps {
                git credentialsId: 'ssh-key-github', url: 'git@github.com:uskovvo/JenkinsTomcat.git'
            }
        }
        stage("build") {
            steps {
                sh "mvn clean install"
            }
        }
        stage("deploy") {
            steps{
                sshagent(['new-ssh-key']) {
                    sh sshPublisher(publishers: [sshPublisherDesc(configName: 'Andersen_With_Tomcat', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'sudo systemctl status tomcat', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '**/*.war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
                }
            }

        }
    }
}