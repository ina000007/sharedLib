def dockerImageBuild(containerName, tag){
    sh "sudo su"
    sh "docker image prune -f"
    sh "docker build -t $containerName:$tag --pull --no-cache ."
    echo "Image build complete"
}
