def dockerImageBuild(containerName, tag){
    sh "docker image prune -f"
    sh "docker build -t $containerName:$tag --pull --no-cache ."

    withCredentials([usernamePassword(credentialsId: 'DockerHubCred', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
     sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD"
     sh "docker tag $containerName:$tag $containerName:$tag"
     sh "docker push $containerName:$tag"
}
    
    echo "Image build complete"
}
