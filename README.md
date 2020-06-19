# 인문학을 위한 한국어처리 

박정열 (장소 및 일정: 경희대학교, 2019년 7월4일)

## 형태소 분석 및 품사 태깅

* `udpipe --input=horizontal --tokenizer=presegmented --tag sjmorph.model input.txt > udpipe-output.txt`
* `java -classpath kyunghee-v01.jar  UDPipe2Espresso udpipe-output.txt > sejong-output.txt`


## 구구조 분석

### 토큰만 사용한 구구조 분석
* `java -classpath kyunghee-v01.jar  UDPipe2tok udpipe-output.txt > udpipe-tokenized.txt`
* `java -jar BerkeleyParser-1.7.jar -gr berkeley.sjtree.model < udpipe-tokenized.txt > berkeley-token-output.txt`

### 토큰 및 품사를 사용한 구구조 분석
* `java -classpath kyunghee-v01.jar  MakeBerkeleyTestWithPOSIn sejong-output.txt > berkeley-pos-input.txt`
* `java -jar BerkeleyParser-1.7.jar -useGoldPOS -gr  berkeley.sjtree.model < berkeley-pos-input.txt > berkeley-pos-output.txt`


## 의존구조 분석

* `java -classpath kyunghee-v01.jar  MakeMaltTestIn sejong-output.txt > malt-input.txt`
* `java -jar maltparser-1.9.2.jar -c sejong-malt -i malt-input.txt -o malt-output.txt  -m parse`

