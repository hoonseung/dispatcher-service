#빌드
custom_build(
    # 컨테이너 이미지의 이름
    ref = 'dispatcher-service',
    # 컨테이너 이미지를 빌드하기 위한 명령
    command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
    # 새로운 빌드를 시작하기 위해 지켜봐야 하는 파일
    deps = ['build.gradle', 'src']
)

#배포
k8s_yaml(kustomize('k8s'))

#관리
k8s_resource('dispatcher-service', port_forwards=['9003'])