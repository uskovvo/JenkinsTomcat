pipeline {
    agent any
    environment{
        PATH = "/opt/apache-maven-3.8.6/bin:$PATH"
    }

    stages {
        stage ("clone code") {
            steps {
                git credentialsId: 'ssh-key-github', url: 'git@github.com:uskovvo/JenkinsTomcat.git'
            }
        }

        stage ("build code") {
            steps {
                sh "mvn clean install"
            }
        }
        stage ("build code") {
            steps {
                sshagent(['deploy_srv_tomcat'])
            }
        }
    }
}