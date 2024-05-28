def dockerImageBuild(containerName, tag){
    sh "docker image prune -f"
    sh "docker build -t $containerName:$tag --pull --no-cache ."

    withCredentials([usernamePassword(credentialsId: 'DockerHubCred', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
        sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD"
        sh "docker tag $containerName:$tag $DOCKERHUB_USERNAME/$containerName:$tag"
        sh "docker push $DOCKERHUB_USERNAME/$containerName:$tag"
  }  
    echo "Image build complete"
}

def kubernetesDeploy(){
    withCredentials([file(credentialsId: 'SERVICEACCOUNT', variable: 'SERVICEACCOUNT')]) {
        sh """
            echo $SERVICEACCOUNT
            gcloud auth activate-service-account --key-file=$SERVICEACCOUNT
            gcloud auth list
            gcloud config set project apt-reality-418106
            gcloud container clusters get-credentials nishant-ag-cluster --zone us-central1-b --project apt-reality-418106
            kubectl get node
            kubectl get pods
            curl -LO https://get.helm.sh/helm-v3.9.0-linux-amd64.tar.gz
            tar -xzvf helm-v3.9.0-linux-amd64.tar.gz
            cd linux-amd64
            ./helm version
            helm repo add bitnami https://charts.bitnami.com/bitnami
            helm install my-nginx bitnami/nginx
            helm status my-nginx
            kubectl get pods
        """
    } 
} 

