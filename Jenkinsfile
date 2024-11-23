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
        /*stage('OWASP Dependency Vulnerability check'){
            steps {
                sh "mvn dependency-check:check -Dformats=XML,JSON,HTML"
            }
        }*/
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
        stage("Trivy Security Scan") {
            steps {
                script {
                    echo "Running Trivy Security Scan..."
                    sh '''
                    trivy image --exit-code 1 --severity HIGH \
                    hichemnajjar/hichemnajjar-back-end:1.1.0 || true
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
                sh 'docker stop prometheus grafana'
                sh 'docker start prometheus grafana'
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
