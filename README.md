GC LOG 전송 서버
===

## 1. API 설명

해당 서버는 GC 로그를 찾거나 받아서 influxdb 에 전송합니다.

#### 목적
1. 여러 서버의 GC 관련 로그를 influxdb에 저장하고 + grafana 조합을 사용하여 여러 서버의 GC 로그를 한 곳에서 모니터링 하기 위함
2. 필요한 만큼만의 GC 로그(양)을 해당 서버에서 직접 생성하고 이를 받아서 처리하는 것이 가장 리소스 절약적일거 같았음


### API 종류
#### 1. [GET] : 다른 서버와 같이 설치되어 GC 로그 파일에 직접 접근이 가능한 경우
* /v1/gc/util/{serverName} : gcutil 옵션의 GC 로그 처리
* /v1/gc/capacity/{serverName} : gccapacity 옵션의 GC 로그 처리

#### 2. [POST] : 다른 서버와 다른 인스턴스에 설치되어 타 서버에서 GC LOG 서버로 로그 파일을 보내주어야 하는 경우
* /v1/file/gc/util/{serverName} + gcutil LOG 파일 포함
* /v1/file/gc/capacity/{serverName} + gccapacity LOG 파일 포함

#### 3. [POST] API 를 사용하기 위한 스크립트
```
curl -F file=@"gc-util.txt" -X POST http://localhost:8080/v1/file/gc/util
```

swagger 문서 주소 -  http://localhost:8080/swagger-ui/index.html

<br>

## 2. 사용 가능한 LOG 파일을 생성하는 방법

해당 서버는 gcutil 과 gccapacity 옵션의 로그만을 처리합니다.

### 1) jps 명령어
1. 현재 실행중인 자바 인스턴스 확인
```
$ jps -v
```

<br>

2. gccapacity : 현재 각 영역에 할당되어 있는 메모리의 크기를 KB 단위로 보여준다.
* -t: JVM 실행 시간 기준 초과 시간(초) 표시 옵션
```
$ jstat -gccapacity -t {PID} {출력 간격} {출력 횟수}
$ jstat -gccapacity -t 27675 1000 20
```

* 결과
```
Timestamp        NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC       MCMN     MCMX      MC     CCSMN    CCSMX     CCSC    YGC    FGC   CGC
       202041.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202042.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202043.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202044.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202045.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202046.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202047.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202048.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202049.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202050.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202051.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202052.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202053.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202054.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202055.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202056.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202057.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202058.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202059.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
       202060.3      0.0 262144.0 149504.0    0.0 1024.0 148480.0        0.0   262144.0   112640.0   112640.0      0.0 1157120.0 123812.0      0.0 1048576.0  16768.0    113     0     8
```

<br>

3. gcutil: 힙 영역의 사용량을 %로 보여준다.
```
$ jstat -gcutil -t {PID} {출력 간격} {출력 횟수}
$ jstat -gcutil -t 27675 1000 20
```

* 결과
```
Timestamp         S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT    CGC    CGCT     GCT
       202227.0   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202228.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202229.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202230.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202231.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202232.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202233.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202234.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202235.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202236.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202237.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202238.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202239.1   0.00 100.00  77.93  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202240.1   0.00 100.00  78.62  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202241.1   0.00 100.00  78.62  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202242.1   0.00 100.00  78.62  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202243.1   0.00 100.00  78.62  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202244.1   0.00 100.00  78.62  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202245.1   0.00 100.00  78.62  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
       202246.1   0.00 100.00  78.62  79.05  97.11  92.04    113    1.026     0    0.000     8    0.063    1.089
```

<br>

## 3. 시각화를 위한 도커 컨테이너: influxdb + grafana

[docker-compose.yaml]
```yaml
version: "3.4"

services:
  influxdb:
    container_name: influxdb
    image: influxdb:1.8
    ports:
      - 8086:8086
    volumes:
      - ./influxdb:/var/lib/influxdb
    environment:
      - INFLUXDB_DB=gc; performance; 
      - INFLUXDB_ADMIN_USER=admin-user12345
      - INFLUXDB_ADMIN_PASSWORD=admin-user12345
      - INFLUXDB_HTTP_AUTH_ENABLED=false 
    networks: 
      - gc

  grafana:
    container_name: grafana
    image: grafana/grafana
    ports:
      - 3000:3000
    links:
      - "influxdb:influxdb"
    networks: 
      - gc

networks:
  gc:
    driver: bridge
```

### 4. 개인 블로그
* GC 
  * https://velog.io/@zenon8485/GC-Garbage-Collect
  * https://velog.io/@zenon8485/GC-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81