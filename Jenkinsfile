pipeline {
    agent any
     environment {
        SONAR_TOKEN = credentials('sonarqube')
        NEXUS_VERSION = 'nexus3'
        NEXUS_PROTOCOL = 'http'
        NEXUS_URL = '192.168.50.4:8081'             // Nexus URL
        NEXUS_REPOSITORY = '5nids2-G6-tp_foyer'                // Nexus Repository for Maven Releases
        NEXUS_CREDENTIAL_ID = 'nexus'                        // Nexus Credentials ID
        }
    stages {
        stage('Checkout GIT') {
            steps {
                git branch: 'ZarouiAhmed-5NIDS2-G6',
                url: 'https://github.com/ahmedzaroui1/5NIDS2-G6-TPFOYER.git'            
            }
        }
        stage('Maven Clean') {
            steps{
                sh "mvn clean"
            }
        }
        stage('Maven Compile') {
            steps{
                sh "mvn compile"
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube', variable: 'SONAR_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dsonar.projectKey=projet-jenkins -Dsonar.projectName='projet-jenkins'"
                }
            }
        }
        stage('Maven Test'){
            steps{
                sh "mvn test"
            }
        }
        stage('Maven Package'){
            steps{
                sh "mvn package -DskipTests"
            }
        }
        stage('Indexing in Nexus'){
            steps {
                sh "mvn deploy -Dmaven.test.skip=true -DaltDeploymentRepository=deploymentRepo::default::http://192.168.50.4:8081/repository/5nids2-G6-tp_foyer/"
            }
        }
        stage('Login to Docker') {
            steps {
                script {
                    // Retrieve credentials from Jenkins securely
                    withCredentials([usernamePassword(
                        credentialsId: 'docker',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )]) {
                        // Login to Docker
                        sh """
                        docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
                        """
                    }
                }
            }
        }
        stage('Building Docker Image'){
            steps{
                sh "docker build -t ahmedzaroui-5nids2-g6-tpfoyer ."
            }
        }
        stage('Tagging Docker Image'){
            steps{
                sh "docker tag ahmedzaroui-5nids2-g6-tpfoyer:latest zarouiahmeed/ahmedzaroui-5nids2-g6-tpfoyer:latest"
            }
        }
        stage('Pushing Docker Image'){
            steps{
                sh "docker push zarouiahmeed/ahmedzaroui-5nids2-g6-tpfoyer:latest"
            }
        }
        stage('Clean Up Old Containers and Images') {
            steps {
                // Remove all stopped containers, unused images, and dangling images
                sh '''
                    docker compose down
                    docker system prune -af
                '''
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                // Start the application and MySQL database using Docker Compose
                sh 'docker compose up -d'
            }
        }



    }
}