#!/usr/bin/bash
echo "저장소 갱신"
git fetch origin
REMOTE='git rev-parse origin/dev'
LOCAL='git rev-parse HEAD'

if [[ $REMOTE = $LOCAL ]]; then
        echo "빌드할 필요가 없습니다."
        exit 0
fi

git merge origin/dev

# 자바 프로세스를 읽어온다.
CAFEID=`ps -ef | grep "java -jar build/libs/cafe-0.0.1-SNAPSHOT.jar" | grep -v "grep"| awk '{ print $2 }'`

if [ -z $CAFEID ]; then
        echo "동작 중인 서버가 없습니다."
else
        echo "$CAFEID 프로세스를 삭제합니다."
        kill -9 $CAFEID
fi

echo "빌드 시작"
rm -rf build
./gradlew build -x test

echo "서버 시작"
nohup java -jar build/libs/cafe-0.0.1-SNAPSHOT.jar > ../log.txt 2>&1 &
echo "배포 완료!"
