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
                        branches: [[name: '*/Baccouchekhaled-5NIDS2-G6']],
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
                        // Print current directory and list files
                        sh 'pwd'
                        sh 'ls -la' // List files in the current directory

                        // Change permission to make mvnw executable
                        sh 'chmod +x ./mvnw'
                    }
                    sh './mvnw clean package -DskipTests'

            }
        }



stage('Run Unit Tests') {
    steps {
            script {
                try {
                    // Run the unit tests
                    sh 'mvn clean test'

                    // Generate the JaCoCo report after tests pass
                    sh 'mvn jacoco:report'

                    // Ensure that JaCoCo report generation is recognized by Jenkins
                    jacoco execPattern: 'target/jacoco.exec'
                } catch (Exception e) {
                    // Mark the build as failed and provide an error message
                    currentBuild.result = 'FAILURE'
                    error "Tests failed or JaCoCo report generation failed: ${e.message}"
                }
            }
        }

}


        stage('Docker Build') {
            steps {
                script {
                    echo 'Building Docker image...'
                    // Ensure you're in the correct directory containing the Dockerfile
                        sh "docker build -t khaledbaccouche19/baccouchekhaled-5nids2-g6:latest ."

                }
            }
        }
            stage('Push Docker Image') {
                    steps {
                        echo 'Pushing Docker image to DockerHub...'
                        script {
                            withCredentials([usernamePassword(credentialsId: 'dockerhubcredentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                sh 'echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin || exit 1'
                                sh 'docker push khaledbaccouche19/baccouchekhaled-5nids2-g6:latest'
                            }
                        }
                    }
                }
            }
}