# Docker

<aside>
💡 Based on Nana Youtube

</aside>

[Docker Crash Course for Absolute Beginners [NEW]](https://www.youtube.com/watch?v=pg19Z8LL06w)

Based on Nana youtube

### What is Docker?

- **\*\***developing**\*\*** and **\*\*\*\***deploying applications**\*\*\*\*** much easier
- application: package → easily shared and distributed
- packages application with all the necessary dependencies, configuration, system tools and runtime

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled.png)

모든 configuration을 operation 팀에 넘겨주었어야 함

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%201.png)

---

### Docker vs Virtual Machine

How docker runs on OS?

어디서 Docker와 VM이 실제로 작동되나?

<aside>
💡 Hardware ↔ OS Kernel ↔ OS application layer

</aside>

- Docker는 OS application layer에서 virtualise됨
- VM은 Kernel과 app layer를 동시에 virtualise함

---

### Docker의 장점

- iamge가 훨씬 작다
- containers bootup하는 시간이 훨씬 빠르다
- but 다른 os는 호환 안됨. (VM은 호환 가능)
  - 그래서 Docker Desktop를 씀
  - Hypervisor layer with a lightweight Linux distro
  - 그래서 보통 Docker Desktop을 설치해서 그 위에 **Docker image를 실행함**

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%202.png)

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%203.png)

---

### Docker Images vs Docker Containers

- Docker Image: Zip file(artifact). 모든 dependency, code, configuration이 담긴 application artifact(packate)
- Docker Container: Actually starts image, running instance of image

www.nethopper.io

---

### Docker Registries

Docker images들이 올라와 있는 곳: Docker Hub

Find and share Docker images

---

### Docker Hub에서 Nginx Pull받기

[nginx - Official Image | Docker Hub](https://hub.docker.com/_/nginx)

```bash
docker pull nginx:1.25 (:1.25는 version)
```

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%204.png)

로컬 cmd창에서 위의 명령어를 치면 자동으로 nginx image가 pull됨

아니면 Docker Desktop에서 바로 검색해서 pull 해도 됨

---

### Images를 Container로 run하기

```bash
docker run nginx:1.25 (docker run {name}:{tag}
```

```bash
docker ps (process status: 현재 실행중인 container)
```

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%205.png)

```bash
docker run -d nginx:1.25 (not blocking terminal / background mode)
```

```bash
docker logs 4eaa64dd58cb (background 상태의 container log 보기 {container id}
```

```bash
docker stop 4eaa64dd58cb (background container 죽이기)
```

---

### How to access Container?

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%206.png)

서비스는 Container 내부의 포트에 마운팅됨. 그걸 우리가 접근할 수 있도록 host port에 mapping 시켜줘야 함

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%207.png)

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%208.png)

만약 localhost:80에 접근하려 하면 안될것임

bind that port 80 to [localhost](http://localhost) port(it doesn’t matter which port. ex)80, 9000 etc..

```bash
docker run -d -p 9000:80 nginx:1.25 (-p {Host_port}:{container_port}
```

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%209.png)

그러면 localhost:9000이 80에 바인딩 된 것을 볼 수 있음

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2010.png)

Standard to use the same port on your host as container is using

---

### Warning ⚠️

사실 `docker run` 를 할때마다 새로운 container가 생성됨.

`docker ps -a` 하면 exited된 모든 containers를 확인 가능.

```bash
docker start {container-id | name} (exit한 기존의 container restart)
```

```bash
docker run --rm -it nginx:1.25
(--rm: remove the container once it exits to avoid clutter)
(-i: interactive, input from keyboard)
(-t: provide pseudo-TTY, provides a terminal we can type into)
```

```bash
docker run --name web-app -d -p 9000:90 nginx:1.25
(--name: 이름 붙여줌, not auto-generated)

<최종본>
docker run --name web-app --rm -d -p 9000:80 nginx:1.25
```

---

### Registry vs Repository

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2011.png)

- Docker Hub is a registry
- On Docker Hub you can host private or public repositories for your applications

### DockerFile - Create own Images

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2012.png)

We need to create a “**definition**” of how to build an image from our application

→ **DockerFIle**

definition is written in a docker file

- Dockerfiles start from a parent image or “base image”
  - ex) server.js에선 node가 base image
- You choose the base image, **depending on** which tools you **need to have** available
  - java, python, tomcat… 보통 lightweight linux에 base image가 있음

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2013.png)

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2014.png)

the latest node version으로 쓴다

```docker
FROM node:20-alpine
```

`FROM` : Build this image from the specified image

`RUN` : Will execute any command in a shell **inside** the container environment

ex) container 내부에서 express나 dependencies를 설치할 때, _npm instll.._

`COPY` : Copies files or directories from **<src>** and adds them to the filesystem of the container at the path ****\*\*\*\*****<dest>****\*\*\*\*****

ex) package.json이 필요할 것, 그러니까 host에서 json 파일을 container에 copy해야함

`WORKDIR` : **********************************\*\*\*\***********************************Sets the working directory**********************************\*\*\*\*********************************** for all following commands

ex) “cd /app/src/…”

`CMD` : The instruction that is to be executed when a Docker container starts

ex) _node src/server.js_ 와 같이 컨테이너 내부에서 프로그램을 시작하는 명령어.

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2015.png)

---

### Build DockerFIle

```docker
docker build -t node-app:1.0 .
(-t: 도커 이미지의 name을 지정)
(.: 이미 COPY에서 컨테이너 root dir을 정해줬으니 현재 dir에 이미지를 빌드해라는 명령.
/app에 빌드될 것임)
```

---

### Docker in Complete Software Development Cycle

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2016.png)

Docker hub에서 mongodb를 pull해서 js 프로젝트 생성

Git, Jenkins를 통해서 CI/CD, integration

private repository에 push해서 서비스가 되는지 계속 테스트

최종: deployment server에서 내가 private repo에 올린 js 프로젝트 + dockerHub에 있는 db(publicly deployed in docker server)

![Untitled](Docker%20e48aa41e29434458be53cc046577044b/Untitled%2017.png)
