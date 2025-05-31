# Makefile for Deep Space Planetary System Analysis

# Compiler
JAVAC = javac

# Directories
SRC = src
BUILD = build

# Source files list
SOURCES_FILE = sources.txt

# Default target to build and run
.PHONY: all
all: build run

# Collect all .java files
.PHONY: collect
collect:
	find $(SRC) -name "*.java" > $(SOURCES_FILE)

# Build the project (always forced)
.PHONY: build
build: collect
	@rm -rf $(BUILD)
	@mkdir -p $(BUILD)
	$(JAVAC) -d $(BUILD) @$(SOURCES_FILE)

# Run the program
.PHONY: run
run:
	@java -cp $(BUILD) Main.Main $(ARGS)

# Clean up
.PHONY: clean
clean:
	rm -rf $(BUILD) $(SOURCES_FILE)

# Generate Javadoc documentation
.PHONY: javadoc
javadoc: collect
	@mkdir -p docs
	javadoc -d docs -sourcepath $(SRC) -subpackages Main:PlanetSystem
	@echo "Javadoc generated in docs/ directory"