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

        stage('Build') {
            steps {
                script {
                    echo "Building the Maven project..."
                    sh 'mvn clean package'

                    // Confirm JAR file creation
                    echo 'Checking if the JAR file was created...'
                    sh 'ls -l target/'

                    // Check specifically for the JAR file's existence
                    sh 'test -f target/tp-foyer-5.0.0.jar || echo "JAR file not found"'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                script {
                    try {
                        // Run unit tests
                        sh 'mvn clean test'

                        // Generate the JaCoCo report
                        sh 'mvn jacoco:report'

                        // Ensure Jenkins recognizes the JaCoCo report
                        jacoco execPattern: 'target/jacoco.exec'
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error "Tests or JaCoCo report generation failed: ${e.message}"
                    }
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    echo 'Checking JAR file presence in target directory...'
                    sh 'pwd'
                    sh 'ls -l target/'

                    echo 'Building Docker image...'
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
