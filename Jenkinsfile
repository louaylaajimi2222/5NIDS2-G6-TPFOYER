pipeline {
    agent any
    stages {
        stage("git clone") {
            steps {
                git branch: 'HichemNajjar-5NIDS2-G6',
                    credentialsId: 'hichemnajj',
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
        }/*
        stage('Packaging') { 
            steps {
                echo 'Packaging...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploying') { 
            steps {
                echo 'Deploying...'
                sh 'mvn deploy -DskipTests'
            }
        }
    stage('Building image') { 
            steps {
                echo 'Building Docker image...'
                sh 'docker build --no-cache -t hichemnajjar/hichemnajjar-back-end:1.1.0 .'
            }
        }*/
      
    stage('Docker Compose') { 
            steps {
                echo 'Starting Docker Compose...'
                sh 'docker-compose down -v'
                sh 'docker-compose up -d'
            }
        }

        
        
    }
}
