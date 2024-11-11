pipeline {
    agent any

    environment {
       
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        ENCRYPTED = credentials('vault_password_id')
        SONAR = credentials('sonar')
    }

    stages {
        stage("Git Clone") {
            steps {
                git branch: 'main',
                    credentialsId: 'gitlab',
                    url: 'git@gitlab.com:devops4392533/projet_5eme.git'
            }
        }

       /* stage("Pre-commit Check") { // Uncomment the following stage if needed
            steps {
                sh "pre-commit run --all-files"
            }
        }*/

        stage("Maven Build") {
            steps {
                sh "mvn clean install"
            }
        }

        stage("Cleanup Previous Build") {
            steps {
                script {
                    // Remove the previous build container if it exists
                    sh """
                    if [ \$(docker ps -a -q -f name=myappjava_${BUILD_NUMBER - 1}) ]; then
                        docker rm -f myappjava_${BUILD_NUMBER - 1}
                    fi
                    """
                    // Remove the previous Docker image if it exists
                    sh """
                    if [ \$(docker images -q louay222/jenkinsdock_${BUILD_NUMBER - 1}) ]; then
                        docker rmi -f louay222/jenkinsdock_${BUILD_NUMBER - 1}
                    fi
                    """
                }
            }
        }

        stage("Sonar") {
            steps {
                script {
                    echo "Running Maven analysis..."
                    sh '''
                   mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=devecsecops \
                    -Dsonar.projectName='devecsecops' \
                    -Dsonar.host.url=http://35.180.21.137:9000 \
                    -Dsonar.token=sqp_cc3a39044100d601c3fb998f14f703a4175c24bc
                    '''
                }
            }
        }

        /* Uncomment if needed for decryption
        stage("Decrypt Dockerfile") {
            steps {
                script {
                    withCredentials([string(credentialsId: 'vault_password_id', variable: 'VAULT_PASSWORD')]) {
                        sh """
                            ansible-vault decrypt Dockerfile --vault-password-file <(echo \$VAULT_PASSWORD)
                        """
                    }
                }
            }
        }
        */

        stage("DOCKER IMAGE") {
            steps {
                sh "docker build -t louay222/fy:9.0.0 ."
            }
        }
         /*stage("trivy scan ") {
            steps {
                sh "trivy image --scanners vuln louay222/fy:9.0.0 "
            }
        }*/

        stage("DOCKER LOGIN") {
            steps {
                sh "echo \$DOCKERHUB_CREDENTIALS_PSW | docker login -u \$DOCKERHUB_CREDENTIALS_USR --password-stdin"
            }
        }

        stage("DOCKER HUB PUSH") {
            steps {
                sh "docker push louay222/fy:9.0.0"
            }
        }

        stage("DOCKER-COMPOSE") {
            steps {
                sh "docker-compose up -d"
            }
        }
    }
}
