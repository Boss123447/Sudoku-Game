# Sudoku Android App

A Sudoku game built using **Kotlin + Jetpack Compose** for Android.  
This project originally began as a **Java console application** to develop the core Sudoku logic, and was later expanded into a full Android application with a graphical interface.

---

# Project Evolution

This project was developed in two phases:

### Phase 1 — Java Console Implementation
The initial goal was to implement the core Sudoku logic:

- Sudoku board representation
- Valid move detection
- Backtracking solver
- Puzzle generator
- Difficulty levels
- Unique solution verification

The console version allowed puzzles to be generated and solved directly from the terminal.

Key classes from the original implementation:

- `SudokuBoard`
- `SudokuSolver`
- `SudokuGenerator`

This phase helped validate the algorithm and game logic before building a UI.

---

### Phase 2 — Android App (Jetpack Compose)

After confirming the game logic worked correctly, the project was expanded into an Android application using:

- **Kotlin**
- **Jetpack Compose**
- **Android Studio**

The Java Sudoku engine was reused and integrated into the Android UI.

---

# Features

- Sudoku puzzle generator
- Multiple difficulty levels
  - Easy
  - Medium
  - Hard
- Interactive 9x9 board
- Row and column highlighting
- Invalid move detection
- Reset puzzle button
- Reveal solution button
- Clean Jetpack Compose UI

---

# Screenshots

Example:
will add later


---

# Tech Stack

Languages:
- Kotlin (Android UI)
- Java (Sudoku engine)

Frameworks:
- Jetpack Compose
- Android SDK

Tools:
- Android Studio
- Git
- GitHub

---

# Project Structure
app/
├── src/main/java/com/example/sudoku
│ ├── MainActivity.kt
│ ├── SudokuScreen.kt
│ └── UI components
│
└── sudoku/
├── SudokuBoard.java
├── SudokuSolver.java
└── SudokuGenerator.java

The Sudoku logic is separated from the UI, making it reusable and easy to test.

---

# Future Improvements

Planned features:

- Pencil marks / notes
- Timer
- Puzzle completion detection
- Hint system
- Dark mode
- Animations
- Save puzzle progress

---

# Why I Built This

This project started mostly out of curiosity and for fun.

I enjoy solving Sudoku puzzles and thought it would be interesting to try building the logic behind the game myself. I first implemented the puzzle generator and solver in Java as a console program to understand how Sudoku algorithms work, especially backtracking and constraint checking.

Once the core logic was working, I decided to take it a step further and turn it into a full Android application using Kotlin and Jetpack Compose. This allowed me to experiment with mobile UI development while reusing the Sudoku engine I had already written.

Overall this project was a way for me to explore problem solving, build something interactive, and experiment with turning a simple idea into a complete application.

---

# Author

Adalberto Gonzalez-Mendoza
