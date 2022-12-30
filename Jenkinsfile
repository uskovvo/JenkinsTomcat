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
                build job: 'TomcatProjectDeploy'
            }
        }
    }
}