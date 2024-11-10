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

     stage('Deploy to Nexus') {
                            steps {

                                    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USERNAME')]) {
                                        // Execute Maven deploy command
                                        sh 'mvn deploy -Dusername=$NEXUS_USERNAME -Dpassword=$NEXUS_PASSWORD'
                                    }
                                }

                        }



        stage('Scan') {
            steps {

                    script {
                        sh 'chmod +x ./mvnw'
                    }
                    withSonarQubeEnv('sonarqube') {
                        sh '''./mvnw sonar:sonar \
                          -Dsonar.java.binaries=target/classes \
                          -Dsonar.jacoco.reportPaths=target/jacoco.exec'''
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
        post {
            always {
                script {
                    def jobName = env.JOB_NAME
                    def buildNumber = env.BUILD_NUMBER
                    def pipelineStatus = currentBuild.result ?: 'UNKNOWN'
                    def bannerColor = pipelineStatus.toUpperCase() == 'SUCCESS' ? 'green' : 'red'

                    def body = """
                    <html>
                    <body>
                        <div style="border: 4px solid ${bannerColor}; padding: 10px;">
                            <h2>${jobName} - Build ${buildNumber}</h2>
                            <div style="background-color: ${bannerColor}; padding: 10px;">
                                <h3 style="color: white;">Pipeline Status: ${pipelineStatus.toUpperCase()}</h3>
                            </div>
                            <p>Check the <a href="${env.BUILD_URL}">console output</a>.</p>
                        </div>
                    </body>
                    </html>
                    """

                    emailext(
                        subject: "${jobName} - Build ${buildNumber} - ${pipelineStatus.toUpperCase()}",
                        body: body,
                        to: 'khaledaziz.baccouche@esprit.tn',
                        from: 'professoraadi@outlook.com',
                        replyTo: 'professoraadi@outlook.com',
                        mimeType: 'text/html'
                    )
                }
            }
        }
}
