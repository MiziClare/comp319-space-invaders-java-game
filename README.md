<p align="center">
  <a href="https://www.liverpool.ac.uk/" target="blank">
    <img src="Liverpool_logo.png" alt="Logo" width="156" height="156">
  </a>
</p>

<h1 align="center" style="font-weight: 600">COMP319 &nbsp;&nbsp;Software Engineering II</h1>
<h3 align="center">⭐ If this project inspires or helps you, feel free to star it. Thanks!</h3>

# Space Invaders Java Game

## Project Overview

A Java-based implementation of the classic **Space Invaders** arcade game, developed as part of the COMP319: Software Engineering II coursework. The project focuses on applying **object-oriented design principles** and patterns.

---

## How to Use

1. **Run the application** via your preferred Java IDE.
2. The main screen loads the game UI where you can start playing immediately.
3. Player controls a ship and attempts to shoot down invading enemies.
4. The game progresses until all enemies are defeated or the player is hit.

---

## Notable Features

1. Fully object-oriented structure with clear pattern usage.
2. Implements key design principles:
   - ✅ Factory Pattern
   - ✅ Chain of Responsibility
   - ✅ Open/Closed Principle
   - ✅ Single Responsibility Principle
3. All visual elements (except barriers) are implemented as interactive game classes.

---

## Project Details

### Key Classes

- **GameController.java**: Manages game logic, UI flow, and object coordination.
- **Enemy.java / Player.java / Bullet.java**: Core game actors modeled as individual classes.
- **FactoryManager.java**: Handles dynamic creation of objects using Factory pattern.
- **CollisionHandler.java**: Demonstrates the Chain of Responsibility in collision detection.
- **Screen.java**: Visual representation of the game interface.

---

## Must See Before Reading Code

* Programming Language: Java
* Assignment Topic: Object Oriented Pattern Design

---

## Feedback Summary

- ✅ Add a **score tracker** in `GameController`, display on `Screen`
- ✅ Provide clearer comments for complex methods
- ✅ Test extensively for edge cases (especially collisions)

---

## Module Specification

* Module: COMP319 — Software Engineering II
* Year: 2024–2025
* Lecturer: Sebastian Coope
* Grade: 90 / 100

⚠ Please respect the University's Academic Integrity Policy. This code was submitted after the assignment deadline and contains only publicly shareable information.
