pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhubcredentials')
        DOCKER_IMAGE = 'khaledbaccouche19/baccouchekhaled-5nids2-g6'
        VERSION = "latest"
        GIT_BRANCH = 'Baccouchekhaled-5NIDS2-G6'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: "*/${GIT_BRANCH}"]],
                        userRemoteConfigs: [[
                            url: 'https://github.com/louaylaajimi2222/5NIDS2-G6-TPFOYER.git',
                            credentialsId: 'KB'
                        ]]
                    ]
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    echo "Building and testing the Maven project..."
                    // Run 'package' to build and test, generating the JAR file in the target directory
                    sh 'mvn clean package'

                    // Confirm JAR file creation
                    echo 'Checking if the JAR file was created...'
                    sh 'ls -l target/'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    echo 'Building Docker image...'
                    // Ensure Dockerfile is in the correct location
                    sh "docker build -t ${DOCKER_IMAGE}:${VERSION} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo 'Pushing Docker image to DockerHub...'
                    withCredentials([usernamePassword(credentialsId: 'dockerhubcredentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin || exit 1'
                        sh "docker push ${DOCKER_IMAGE}:${VERSION}"
                    }
                }
            }
        }
    }
}
