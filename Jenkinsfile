pipeline {
    agent any
    environment {
       
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }
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
                sh "mvn clean install"
            }
        }
     /*   
     stage("Sonar") {
            steps {
                script {
                    echo "Running Maven analysis..."
                    sh '''
                        mvn clean verify sonar:sonar \
                          -Dsonar.projectKey=DevOpsFinal \
                          -Dsonar.projectName='DevOpsFinal' \
                          -Dsonar.host.url=http://192.168.56.10:9001 \
                          -Dsonar.token=sqa_04e73575b143f30b317b276de6282dd536175dbb

                        '''
                }
            }
        }*/
        stage('Packaging') { 
            steps {
                echo 'Packaging...'
                sh 'mvn package -DskipTests'
            }
        }
/*
        stage('Deploying') { 
            steps {
                echo 'Deploying...'
                sh 'mvn deploy -DskipTests'
            }
        }*/
    stage('Building image') { 
            steps {
                echo 'Building Docker image...'
                sh 'docker build --no-cache -t hichemnajjar/hichemnajjar-back-end:1.1.0 .'
            }
        }  
 stage("DOCKER LOGIN") {
            steps {
                sh "echo \$DOCKERHUB_CREDENTIALS_PSW | docker login -u \$DOCKERHUB_CREDENTIALS_USR --password-stdin"
            }
        }
         stage("DOCKER HUB PUSH") {
            steps {
                sh "docker push hichemnajjar/hichemnajjar-back-end:1.1.0 "
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
                sh 'docker stop  grafana'
                sh 'docker start  grafana'
            }
        }
        
        
    }
}
