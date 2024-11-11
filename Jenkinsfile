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
    }
}
