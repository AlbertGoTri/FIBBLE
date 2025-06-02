# FIBBLE 🧠🎲
**PROP – Programming Projects**
**Spring Semester 2024–2025**
**Universitat Politècnica de Catalunya (UPC)**

## 📌 Overview

**FIBBLE** is a Java-based academic project developed as part of the *PROP* course at UPC. It features a complete implementation of the **Guy and Jacobson algorithm** to find the optimal word placement in the game of **Scrabble**. The goal was to combine algorithmic efficiency with a clean, modern user experience.

FIBBLE includes a fully functional **Graphical User Interface (GUI)** built with **Java Swing**, styled with a sleek and modern **dark theme**.

## 🧠 Key Features

- ✔️ Implementation of the **Guy and Jacobson** word-finding algorithm
- ✔️ **Java Swing** UI with modern, dark styling
- ✔️ Clean and modular **3-layer architecture**:
  - **Domain Layer** – Core logic and game rules
  - **Persistence Layer** – Data management and I/O
  - **Presentation Layer** – User interaction and GUI
- ✔️ All classes are documented using **Javadoc**
- ✔️ Easy-to-run with provided execution script

## 📂 Project Structure

```
FIBBLE/
│
├── FONTS/
│   ├── launch_main.sh         # Launch script for the application
│   └── src/
│       ├── domain/            # Domain logic
│       ├── persistence/       # Persistence layer
│       ├── presentation/      # UI and interaction
│       ├── saves/             # Saved games
│       ├── resources/         # Game resources (dictionaries, .png, etc)
│       ├── data_persistencia/
│       │   └── usuarios/      # Saved users
│       └── presentation/      # UI and interaction
│
│
├── DOCS/                      # User manual and test cases
├── EXE/                       # Executable of the project (.jar)
├── javadoc/                   # Javadoc generated documentation.
└── README.md
```

## 🚀 Running the Project

1. **Make sure you have Java 21 or higher installed.**

2. **Navigate to the `/FONTS` directory in your terminal:**
   ```bash
   cd FONTS
   ```

3. **Launch the application:**
   ```bash
   ./launch_main.sh
   ```

> 💡 *If you encounter permission issues, you may need to run `chmod +x launch_main.sh` once before executing.*

## 📖 Documentation

All classes and public methods are documented using **Javadoc**.
To generate the documentation, run:

```bash
javadoc -d docs -sourcepath FONTS/src -subpackages domain:persistence:presentation
```
---

Feel free to fork, explore, and build upon our implementation!
