# MineSweeper

An OO designed implementation of MineSweeper.  When an empty square is clicked it traverses recursively through all neighboring squares without repetition until the edge is found.

There is no reset/new game button, but that would be quite easy to implement.

Uses a thought pattern along the lines of
enum directions {UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT}
but numbers are hardcoded.