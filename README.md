# Console Task Manager 

## About the Project 
 A simple Java console program for managing daily tasks. It supports creating, reading, updating, deleting (CRUD) tasks, sorting them by priority, and storing data in a local text file (`tasks.txt`).

## Features
- **Add Task:** Enter name, description, and set priority (`LOW`, `MEDIUM`, `HIGH`).
- **Show All Tasks:** Displays all saved tasks in a table format.
- **Mark as Completed:** Updates task status to `DONE`.
- **Delete Task:** Removes task using its ID.
- **Sort by Priority:** Uses Java Streams to sort tasks from HIGH to LOW priority.
- **Save & Exit:** Automatically saves all current tasks into `tasks.txt` on menu option 6.

## Technologies Used
- Java 8+
- Java Collections Framework (`ArrayList`, `List`)
- Java Streams & Lambda expressions (for sorting)
- File I/O (`BufferedReader`, `BufferedWriter`)
- Exception Handling (`try-catch`)
