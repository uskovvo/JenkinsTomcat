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
                sshagent(['2-ssh-key'])  {
                    sh 'echo "Hello World"'
                    sh 'ls'
                    sh 'ssh -o StrictHostKeyChecking=yes valera@192.168.0.200:8080'
                    sh 'cd /opt/tomcat/webapps'
                }
            }

        }
    }
}