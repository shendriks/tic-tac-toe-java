# tic-tac-toe

[![Java CI with Gradle](https://github.com/shendriks/tic-tac-toe-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/shendriks/tic-tac-toe-java/actions/workflows/gradle.yml)

A CLI implementation of Tic Tac Toe in Java using the Minimax algorithm

## How to play

This is a simple CLI application. Once started, it expects the user to enter a
command: 

```text
Input command (type help for help): 
```

There are three commands:

1. `help`
2. `start <player_1> <player_2>` with `<player_n>` being one of: `user`, `easy`, `medium`, `hard`
3. `exit` 

The `help` command prints some helpful information about the commands available, while the `exit` command exits the
application. This leaves us with the `start <player_1> <player_2>` command which expects the types of the two players.

### The Player Types

There are four player types:

1. `user`: a human player
2. `easy`, `medium` and `hard`: more or less "intelligent" opponents controlled by the computer

When it's a human player's turn, they are asked to enter the coordinates of their next move in form of two integers
ranging from 1 to 3 each, e.g.:

```text
Enter the coordinates [row column]: 1 1
```

By entering `1 1` an X (or an O - depending on if they're the first or second player) is placed in the upper left corner
of the board:

```text
+-------+
| X     |
|       |
|       |
+-------+
```

### Computer controlled Opponents
* `easy` - this one just makes random moves
* `medium` - this one makes random moves, unless they can make a winning move (if they already have two in a row) or a 
   blocking move (if the opponent has two in a row)
* `hard` - this one uses the Minimax algorithm to make the best move possible
