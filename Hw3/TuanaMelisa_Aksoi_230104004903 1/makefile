SRC = src
BUILD = build

collect:
	find $(SRC) -name "*.java" > sources.txt

build:
	javac -d $(BUILD) @sources.txt

run:
	@java -cp build src.Main.Main $(ARGS)

