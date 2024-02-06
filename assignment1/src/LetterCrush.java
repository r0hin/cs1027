public class LetterCrush {
    private char[][] grid; // [row][col]

    public static final char EMPTY = ' '; // Empty cell

    public LetterCrush(int width, int height, String initial) {
        grid = new char[height][width]; // Make a grid of the given size
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Fill the grid with empty cells
                grid[j][i] = EMPTY;
            }
        }

        // Fill the grid with the initial string
        for (int i = 0; i < initial.length(); i++) {
            int row = i / width; // 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2 for example
            int col = i % width; // 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3 for example
            if (i >= width * height) {
                break; // Ignore extra characters
            }
            grid[row][col] = initial.charAt(i);
        }
    }

    public String toString() {
        // Return a string representation of the grid
        StringBuilder result = new StringBuilder();
        result.append("LetterCrush\n");
        for (int row = 0; row < grid.length; row++) {
            result.append("|"); // things at the sides
            for (int col = 0; col < grid[row].length; col++) {
                result.append(grid[row][col]); // the actual grid
            }
            result.append("|"); // things at the sides
            result.append(row); // the row number
            result.append("\n"); // new line
        }
        result.append("+"); // things at the sides
        for (int col = 0; col < grid[0].length; col++) {
            result.append(col); // the column number
        }
        result.append("+"); // things at the sides
        return result.toString();
    }

    public boolean isStable() {
        // Any non-empty character is above an empty character
        for (int row = 0; row < grid.length - 1; row++) {
            // Checking all the rows except the last one
            for (int col = 0; col < grid[row].length; col++) {
                // Checking all the columns
                char currentCell = grid[row][col];
                char cellBelow = grid[row + 1][col];
                // If the current cell is not empty and the cell below is empty, return false
                if (currentCell != EMPTY && cellBelow == EMPTY) {
                    return false;
                }
            }
        }
        // If no cell is found that is not stable, return true
        return true;
    }

    public void applyGravity() {
        // Start at bottom row, if cell empty and cell above not empty, swap
        for (int row = grid.length - 1; row > 0; row--) {
            // Loop rows from bottom to top (excluding the top row)
            for (int col = 0; col < grid[row].length; col++) {
                // Loop columns
                char currentCell = grid[row][col];
                char cellAbove = grid[row - 1][col];
                // If the current cell is empty and the cell above is not empty, swap
                if (currentCell == EMPTY && cellAbove != EMPTY) {
                    grid[row][col] = cellAbove;
                    grid[row - 1][col] = EMPTY;
                }
            }
        }
    }

    public boolean remove(Line theLine) {
        // If the lines start or end points are not in the grid, return false
        int[] start = theLine.getStart();
        int[] end = new int[2];

        // Calculate the end point of the line
        if (theLine.isHorizontal()) {
            end[0] = start[0];
            end[1] = start[1] + theLine.length();
        } else {
            end[0] = start[0] + theLine.length();
            end[1] = start[1];
        }

        // If the end point is not in the grid, return false
        if (start[0] < 0 || start[0] >= grid.length || start[1] < 0 || start[1] >= grid[0].length) {
            return false;
        }

        // Replace with EMPTY all cells in the line in the grid
        if (theLine.isHorizontal()) {
            // If the line is horizontal, replace the cells in the same row
            for (int col = start[1]; col < end[1]; col++) {
                grid[start[0]][col] = EMPTY;
            }
        } else {
            // If the line is vertical, replace the cells in the same column
            for (int row = start[0]; row < end[0]; row++) {
                grid[row][start[1]] = EMPTY;
            }
        }

        return true;
    }

    public String toString(Line line) {
        // Return a string representation of the grid with the line in lower case
        StringBuilder result = new StringBuilder();
        result.append("CrushLine\n");
        // Same as toString() basically
        for (int row = 0; row < grid.length; row++) {
            result.append("|"); // things at the sides
            for (int col = 0; col < grid[row].length; col++) {
                if (line.inLine(row, col)) {
                    // If the cell is in the line, append it in lower case
                    result.append(Character.toLowerCase(grid[row][col]));
                    // result.append("X");
                } else {
                    result.append(grid[row][col]);
                }
            }
            result.append("|");
            result.append(row); // the row number
            result.append("\n"); // new line
        }
        result.append("+");
        for (int col = 0; col < grid[0].length; col++) {
            result.append(col); // the column number
        }
        result.append("+");
        return result.toString();
    }

    public Line longestLine() {
        // Horizontal has priority over vertical
        // Lowest row has priority over higher row
        // Leftmost column has priority over rightmost column
        // If same column, prefer higher one
        // Need at least 3 adjacent cells with the same character

        Line longest = null; // The longest line found
        int largestLength = 0; // The length of the longest line found

        // Loop cols right to left (leftmost columns will overwrite the longest line)
        for (int col = grid[0].length - 1; col >= 0; col--) {
            // Loop row from BOTTOM to TOP (top rows will overwrite the longest line)
            for (int row = grid.length - 1; row >= 0; row--) {
                // Check if the cell is the same as the one above
                int length = 1;
                int topMostRow = row; // keep topmost for line construction
                for (int i = row - 1; i >= 0; i--) {
                    if (grid[i][col] == grid[row][col] && grid[i][col] != EMPTY) {
                        length++;
                        topMostRow = i;
                    } else {
                        break; // Stop if the cell is different
                    }
                }
                if (length >= largestLength) { // >= because of the priority rules
                    longest = new Line(topMostRow, col, false, length);
                    largestLength = length;
                }
            }
        }

        // Loop row top to bottom (bottom rows will overwrite the longest line)
        for (int row = 0; row < grid.length; row++) {
            // Loop col from RIGHT to LEFT (leftmost columns will overwrite the longest line)
            for (int col = grid[0].length - 1; col >= 0; col--) {
                // Check if the cell is the same as the one to the left
                int length = 1; // start with 1
                int leftMostCol = col; // keep leftmost for line construction
                for (int i = col - 1; i >= 0; i--) {
                    if (grid[row][i] == grid[row][col] && grid[row][i] != EMPTY) {
                        length++;
                        leftMostCol = i;
                    } else {
                        break; // Stop if the cell is different
                    }
                }
                if (length >= largestLength) { // >= because of the priority rules
                    longest = new Line(row, leftMostCol, true, length);
                    largestLength = length;
                }
            }
        }

        // Terninary operator to return the longest line if it is long enough, otherwise return null
        return largestLength >= 3 ? longest : null;
    }

    public void cascade() {
        // Apply gravity
        applyGravity();
        // While there are lines to remove
        while (true) {
            Line line = longestLine();
            if (line == null) {
                break; // No more lines to remove
            }
            remove(line);
            applyGravity(); // Always apply gravity after removing a line
        }
    }

}
