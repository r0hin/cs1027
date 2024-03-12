import java.io.IOException;

public class FrogPath {
  private Pond pond;

  public FrogPath(String filename) {
    try {
      pond = new Pond(filename);
    } catch (InvalidMapException | IOException e) {
      e.printStackTrace();
    }
  }

  public Hexagon findBest(Hexagon currCell) {
    // To determine the next best cell for Freddy, we will assign priorities to the cells based on their location and type. So, you must first search through the six (or fewer) cells adjacent to the current cell and determine their priorities as explained below. Then, if the current cell is a lilypad cell, look at the cells two away from the current cell and determine their priorities. While examining the neighbouring cells around a cell, you must start at index 0 and continue clockwise up to index 5.
    // As you examine each cell in the order explained and illustrated above, you must determine the priority of the cell based on its type (water, reeds, lilypad, etc.) as well as where it is located relative to the current cell (adjacent, two away in a straight line, or two away not in a straight line). However, when Freddy visits a cell, the cell will be marked and when looking for the cell with lowest priority the cells that have been already marked will not be considered.
    
    // Create priority queue
    ArrayUniquePriorityQueue<Hexagon> pq = new ArrayUniquePriorityQueue<Hexagon>();
    // Ad all reachable cells to the priority queue
    for (int i = 0; i < 6; i++) {
      Hexagon next = currCell.getNeighbour(i);
      if (next != null && !next.isMarked() && !next.isAlligator() ) {
        // Calculate priority
        // 0.0 3 flies
        // 1.0 2 flies
        // 2.0 1 fly
        // 3.0 End (Franny)
        // 4.0 Lilypad
        // 5.0 Reeds
        // 6.0 Water
        // 10.0 Reeds near alligator
        // +0.5 Cell 2 away in a straight line
        // +1.0 Cell 2 away not in a straight line

        double priority = 0.0;
        if (next.isEnd()) {
          priority = 3.0;
        } else if (next.isLilyPadCell()) {
          priority = 4.0;
        } else if (next.isReedsCell()) {
          priority = 5.0;
        } else if (next.isWaterCell()) {
          priority = 6.0;
        } else if (next instanceof FoodHexagon) {
          priority = 3.0 - ((FoodHexagon) next).getNumFlies();
        }

        pq.add(next, priority);
      }
    }

    // Return the cell with the lowest priority
    return pq.peek();
  }

  public String findPath() {
    // Create a Stack using the provided ArrayStack class to keep track of the cells that the frog has visited in its path from the starting cell toward the end cell. Remember that the cells are represented with objects of the class Hexagon. See the Path Algorithm section belo
    
    ArrayStack<Hexagon> stack = new ArrayStack<Hexagon>();
    stack.push(pond.getStart());

    // o You also need to build a String containing the cell ID's (call getID() or toString() method on the Hexagon objects to get a the ID of a cell) of EVERY cell Freddy visits along his path
    
    String path = "";
    while (!stack.isEmpty()) {
      Hexagon currCell = stack.peek();
      if (currCell.isEnd()) {
        break;
      }
      Hexagon nextCell = findBest(currCell);
      if (nextCell == null) {
        stack.pop();
      } else {
        stack.push(nextCell);
      }
    }

    while (!stack.isEmpty()) {
      path += stack.pop().getID() + " ";
    }

    return path;
  }
}
