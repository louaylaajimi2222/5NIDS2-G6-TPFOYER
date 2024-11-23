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
        
      /*
    stage("Sonar") {
            steps {
                script {
                    echo "Running Maven analysis..."
                    sh '''
                        mvn clean verify sonar:sonar \
                          -Dsonar.projectKey=DevOpsFinalTest \
                          -Dsonar.projectName='DevOpsFinalTest' \
                          -Dsonar.host.url=http://192.168.10.33:9000 \
                          -Dsonar.token=sqp_85f94326332c3ffc409e2bcb8be94f5aead33417

                        '''
                }
            }
        }  */ 



 
        stage('Owasp Zap') {
            steps {
                script {
                    echo "Running Owasp Zap Scan ..."
                    sh 'docker run --rm zaproxy/zap-stable zap.sh -daemon -host 192.168.33.10 -port 8080 '
                }
            }
        }
            stage("Trivy Security Scan") {
            steps {
                script {
                    echo "Running Trivy Security Scan ..."
                    sh '''
                    trivy image --exit-code 1 --severity HIGH \
                    youssefhessine/youssefhessine-back-end:1.1.0 || true
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
        }
    stage('Building image') { 
            steps {
                echo 'Building Docker image...'
                sh 'docker build --no-cache -t youssefhessine/youssefhessine-back-end:1.1.0 .'
            }
        }
     */



        
    stage('Monitoring') { 
            steps {
                echo 'Starting grafana + promotheus...'
                sh 'docker stop prometheus grafana'
                sh 'docker start prometheus grafana'
            }
        }
        

        
        
    }
}
