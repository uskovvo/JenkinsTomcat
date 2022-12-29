pipeline {
    agent any

    tools {
        jdk "11"
        maven "3.8.6"
    }

    stages {
        booleanParam(defaultValue: true, description: 'run test tests', name: 'rest')
        booleanParam(defaultValue: false, description: 'run web tests', name: 'web')
    }

    stages {
        stage ('rest tests') {
            when {
                expression { return params.rest }
            }
            steps
        }
    }
}