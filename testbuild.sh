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

echo "빌드 시작"
rm -rf build
./gradlew build -x test

echo "서버 시작"
java -jar build/libs/cafe-0.0.1-SNAPSHOT.jar
echo "배포 완료!"
