def CONTAINER_NAME="validation-service"
def CONTAINER_TAG="01"
def DOCKER_HUB_ID="dockerHub"
def DOCKER_HUB_USER="hakdogan"
def HTTP_PORT="9080"
def HTTPS_PORT="9443"

node {

    stage('Initialize'){
        def mavenHome  = tool 'myMaven'
        env.PATH = "${mavenHome}/bin:${env.PATH}"
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Build'){
        sh "mvn clean install -f validation-service/pom.xml"
    }

    stage("Image Prune"){
        imagePrune(CONTAINER_NAME)
    }

    stage('Image Build'){
        imageBuild(CONTAINER_NAME, CONTAINER_TAG)
    }

    stage('Push to Docker Registry'){
        withCredentials([usernamePassword(credentialsId: DOCKER_HUB_ID, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            pushToImage(CONTAINER_NAME, CONTAINER_TAG, USERNAME, PASSWORD)
        }
    }

    stage('Deploy to Kubernetes'){
        deployKubernetes(CONTAINER_NAME, HTTP_PORT)
    }

    //stage('Run App'){
    //    runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT, HTTPS_PORT)
    //}
}

def imagePrune(containerName){
    try {
        sh "docker image prune -f"
        sh "docker stop $containerName"
    } catch(error){}
}

def imageBuild(containerName, tag){
    dir("validation-service") {
        sh "docker build -t $containerName:$tag -t $containerName --pull --no-cache ."
        echo "Image build complete"
    }
}

def pushToImage(containerName, tag, dockerUser, dockerPassword){
    sh "docker login -u $dockerUser -p $dockerPassword"
    sh "docker tag $containerName:$tag $dockerUser/$containerName:$tag"
    sh "docker push $dockerUser/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPort, httpsPort){
    sh "docker pull $dockerHubUser/$containerName:$tag"
    sh "docker run -d --rm -p $httpPort:$httpPort --name $containerName --env HTTP_PORT=$httpPort --env HTTPS_PORT=$httpsPort $dockerHubUser/$containerName:$tag"
    echo "Application started on port: ${httpPort} (http)"
}

def deployKubernetes(deploymentName, port){
    sh "microk8s.kubectl apply -f validation.yaml"
    sh "microk8s.kubectl expose deployment $deploymentName --type=LoadBalancer --port $port"
    echo "Application started on port: ${port} (http)"
}