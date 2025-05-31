
# Directories
SRC_DIR = src
BUILD_DIR = build
CONFIG = config.txt

# Java compiler settings
JAVAC = javac
JAVA = java
JAVAC_FLAGS = -d $(BUILD_DIR)

# Main class
MAIN_CLASS = src.Main.Main

# Find all Java source files
.PHONY: collect
collect:
	find $(SRC_DIR) -name "*.java" > sources.txt

# Compile all Java files
.PHONY: build
build: collect
	$(JAVAC) $(JAVAC_FLAGS) @sources.txt

# Run the application
.PHONY: run
run: build
	$(JAVA) -cp $(BUILD_DIR) $(MAIN_CLASS) $(CONFIG)

# Clean build artifacts
.PHONY: clean
clean:
	rm -rf $(BUILD_DIR) sources.txt

# Default target
.DEFAULT_GOAL := run

# Help target
.PHONY: help
help:
	@echo "Available targets:"
	@echo "  collect  - Find all Java source files"
	@echo "  build    - Compile all Java files"
	@echo "  run      - Run the application"
	@echo "  clean    - Remove build artifacts"
	@echo "  help     - Display this help message"