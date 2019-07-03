# 인문학을 위한 한국어처리 

박정열 jungyeul@buffalo.edu
경희대학교, 2019년 7월4일

## 형태소 분석 및 품사 태깅
`udpipe --input=horizontal --tokenizer=presegmented --tag sjmorph.model input.txt > udpipe-output.txt`
`java -classpath kyunghee-v01.jar  UDPipe2Espresso udpipe-output.txt > sejong-output.txt`

## 구구조 분석
