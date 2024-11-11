pipeline {
    agent any
    stages {
        stage("git clone") {
            steps {
                git branch: 'YoussefHessine',
                    credentialsId: 'youssefhessine',
                    url: 'https://github.com/louaylaajimi2222/5NIDS2-G6-TPFOYER.git'
            }
        }
         stage("maven build") {
            steps {
                sh "mvn clean install"
            }
        }
    stage("Sonar") {
            steps {
                script {
                    echo "Running Maven analysis..."
                    sh '''
                        mvn clean verify sonar:sonar \
                          -Dsonar.projectKey=DevOpsFinal \
                          -Dsonar.projectName='DevOpsFinal' \
                          -Dsonar.host.url=http://192.168.1.9:9000 \
                          -Dsonar.token=sqp_e700b11fc4853714e09799b6c2a5856360ceaf24

                        '''
                }
            }
        }
        
    }
}
