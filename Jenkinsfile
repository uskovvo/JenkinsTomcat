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
                sshagent(['1-ssh-key'])  {
                    sh 'ssh -v StrictHostKeyChecking=no /JenkinsTomcat.war valera@192.168.0.200:8080:/opt/tomcat/webapps'
                }
            }

        }
    }
}