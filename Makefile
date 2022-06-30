.PHONY: build delete run

PROJECT=mastermind

build:
	./gradlew build
	docker build -f docker/Dockerfile -t $(PROJECT):latest .


deploy:
	docker-compose -f docker/docker-compose.yml -p $(PROJECT) up -d


.PHONY: run-jmeter-gui build
run-jmeter-gui:
	jmeter -t src/test/functional/functional.jmx &