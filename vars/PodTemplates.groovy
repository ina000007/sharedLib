public void mavenTemplate(body) {
  podTemplate(
        containers: [containerTemplate(name: 'maven', image: 'maven', command: 'sleep', args: '99d')]) {
    body.call()
}
}
return this
