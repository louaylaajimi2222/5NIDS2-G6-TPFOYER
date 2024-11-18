pipeline {
    agent any
    
    stages {
        stage("git") {
            steps {
                git branch: 'HichemNajjar-5NIDS2-G6',
                    credentialsId: 'hichemnajj',
                    url: 'https://github.com/louaylaajimi2222/5NIDS2-G6-TPFOYER.git'
            }
        }
        stage("maven") {
            steps {
                // Run Maven build with JaCoCo coverage
                sh "mvn clean install jacoco:report"
            }
        }
        stage('Packaging') { 
            steps {
                echo 'Packaging...'
                sh 'mvn package -DskipTests'
            }
        }
        stage('Building image') { 
            steps {
                echo 'Building Docker image...'
                sh 'docker build --no-cache -t hichemnajjar/hichemnajjar-back-end:1.1.0 .'
            }
        }
        stage('Trivy Scan') {
            steps {
                script {
                    echo 'Running Trivy vulnerability scan...'
                    // Pull Trivy image
                    sh 'docker pull aquasec/trivy'

                    // Run Trivy scan on the Docker image
                    sh '''
                    docker run --rm \
                        -v /var/run/docker.sock:/var/run/docker.sock \
                        aquasec/trivy image --no-secret hichemnajjar/hichemnajjar-back-end:1.1.0
                    '''
                }
            }
        }
        stage('Docker Compose') { 
            steps {
                echo 'Starting Docker Compose...'
                sh 'docker-compose down -v'
                sh 'docker-compose up -d'
            }
        }
        stage('Graphana') { 
            steps {
                echo 'Graphana...'
                sh 'docker stop grafana'
                sh 'docker start grafana'
            }
        }
    }
    
    post {
        success {
            // Publish JaCoCo coverage report
            jacoco(execPattern: '**/target/jacoco.exec', 
                   classPattern: '**/target/classes', 
                   sourcePattern: '**/src/main/java', 
                   exclusionPattern: '')
        }
        always {
            // Optional: Cleanup or notification actions
            echo 'Build completed.'
        }
    }
}
