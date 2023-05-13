## Push notifications to PubSub or PubSub emulator from Google Cloud Storage emulator buckets:


docker network create testnetwork

---

docker run --rm -it -p 8085:8085 --network=testnetwork --name pubsub google/cloud-sdk:emulators gcloud beta emulators pubsub start --project=project-dev --host-port=0.0.0.0:8085

---

docker run --rm -it -p 4443:4443 --network=testnetwork -e PUBSUB_EMULATOR_HOST=pubsub:8085 --name fakegcs fsouza/fake-gcs-server:latest -scheme http -event.pubsub-project-id project-dev -event.pubsub-topic NEW_FILE_UPLOAD

---

docker exec -it fakegcs sh

---

apk update && apk add curl

curl $PUBSUB_EMULATOR_HOST