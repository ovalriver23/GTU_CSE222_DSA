# Compiler
JAVAC = javac
JAVA = java

# Directories
SRC = src
BUILD = bin

# Default target to build and run
.PHONY: all
all: build run

# Collect all .java files
.PHONY: collect
collect:
	find $(SRC) -name "*.java" > sources.txt

# Build the project
.PHONY: build
build: collect
	@rm -rf $(BUILD)
	@mkdir -p $(BUILD)
	$(JAVAC) -d $(BUILD) @sources.txt

# Run the program
.PHONY: run
run:
	$(JAVA) -cp $(BUILD) SpellChecker.SpellChecker

# Clean up
.PHONY: clean
clean:
	rm -rf $(BUILD) sources.txt
