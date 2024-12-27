# CLI To-Do List

A Command Line Interface (CLI) to-do list application made in Kotlin.

## Features

- Add tasks
- List tasks
- Update task descriptions
- Change task status
- Delete tasks

## Installation

### Prerequisites

- [Kotlin](https://kotlinlang.org) installed.
- [JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) 11 or higher.

### Clone the repository

```shell
git clone https://github.com/caiotdearaujo/clitodolist.git
cd clitodolist
```

## Usage

### Build the project

```shell
./gradlew build
```

### Run the project

```shell
./gradlew run
```

### Commands

- `list`: List all tasks.
- `list [id]`: List task with the specified id.
- `list [status]`: List all tasks with the specified status.
- `add [description]`: Create a task with the specified description.
- `update [id] [description]`: Change the description of the task with the specified id.
- `mark-todo [id]`: Mark the task with the specified id with "to do".
- `mark-in-progress [id]`: Mark the task with the specified id with "in progress".
- `mark-done [id]`: Mark the task with the specified id with "done".
- `delete [id]`: Delete the task with the specified id.
- `version`: Print the current version of the program.
- `reset`: Clear the task list file. Useful to fix violations.
- `help`: List all commands, parameters and descriptions.
- `help [comman]`: List specific parameters and descriptions for the specified command.

## License

This project is licensed under the [Unlicense](https://unlicense.org).

## Contact

Created by [Caio de Araújo](https://github.com/caiotdearaujo) - feel free to contact me!

---

Feel free to customize this further as needed!