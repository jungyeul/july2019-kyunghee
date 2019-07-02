./udpipe --input=horizontal --tokenizer=presegmented --tag sjmorph.model input.txt > udpipe-output.txt
java UDPipe2Espresso udpipe-output.txt  > sejong-output.txt 

java UDPipe2tok udpipe-output.txt > udpipe-tokenized.txt
java -jar BerkeleyParser-1.7.jar -gr berkeley.sjtree.model < udpipe-tokenized.txt > berkeley-token-output.txt

java MakeBerkeleyTestWithPOSIn sejong-output.txt > berkeley-pos-input.txt 
java -jar BerkeleyParser-1.7.jar -useGoldPOS -gr berkeley.sjtree.model < berkeley-pos-input.txt > berkeley-pos-output.txt

java MakeMaltTestIn sejong-output.txt > malt-input.txt
java -jar maltparser-1.9.2.jar -c sejong-malt -i malt-input.txt -o malt-output.txt  -m parse

