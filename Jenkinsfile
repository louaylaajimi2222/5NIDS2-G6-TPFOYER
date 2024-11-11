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
                           mvn clean verify Sonar:Sonar \
                          -Dsonar.projectKey=DevOps \
                          -Dsonar.projectName='DevOps' \
                          -Dsonar.host.url=http://192.168.1.9:9000 \
                          -Dsonar.token=sqp_a3985eb37c525c06320a733ca3a2ac6a512d2e99

                        '''
                }
            }
        }
        
    }
}
