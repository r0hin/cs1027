public class Line {
    private int[] start; // start[0] is the row, start[1] is the column
    private int[] end; // end[0] is the row, end[1] is the column

    public Line(int row, int col, boolean horizontal, int length) {
        start = new int[2];
        start[0] = row;
        start[1] = col;

        end = new int[2];
        // Calculate the end point of the line
        if (horizontal) {
            end[0] = row;
            end[1] = col + length - 1;
        } else {
            end[0] = row + length - 1;
            end[1] = col;
        }
    }

    public int[] getStart() {
        return start; // start[0] is the row, start[1] is the column
    }

    public int length() {
        // Return the length of the line
        // absolute value of the difference between the start and end points
        return Math.abs(start[0] - end[0]) + Math.abs(start[1] - end[1]) + 1;
        // If horiz/vert, then one of the differences is 0, so the length is the other difference + 1
    }

    public boolean isHorizontal() {
        // Return true if the line is horizontal
        // row num is the same
        return start[0] == end[0];
    }

    public boolean inLine(int row, int col) {
        if (isHorizontal()) {
            // Return true if the cell is in the line
            return row == start[0] && col >= start[1] && col <= end[1];
        } else {
            // col must be same, bc it's vertical
            return col == start[1] && row >= start[0] && row <= end[0];
        }
    }

    public String toString() {
        // (new Line(1,1,true,2)).toString() returns "Line:[1,1]->[1,2]"
        return "Line:[" + start[0] + "," + start[1] + "]->[" + end[0] + "," + end[1] + "]";
    }
}