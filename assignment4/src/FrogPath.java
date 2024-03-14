import java.io.IOException;

public class FrogPath {
  private Pond pond;

  // Constructor
  public FrogPath(String filename) {
    try {
      pond = new Pond(filename);
    } catch (InvalidMapException | IOException e) {
      e.printStackTrace();
    }
  }

  public Hexagon findBest(Hexagon currCell) {
    ArrayUniquePriorityQueue<Hexagon> pq = new ArrayUniquePriorityQueue<Hexagon>(); // Priority queue
    // Add all reachable cells to the priority queue

    for (int i = 0; i < 6; i++) { // Add all reachable cells to the priority queue
      Hexagon next = currCell.getNeighbour(i);

      if (next == null) {
        continue; // Skip this cell
      }

      // Cell is not marked, alligator, or mud
      if (!next.isMarked() && !next.isAlligator() && !next.isMudCell() ) {
        boolean alligatorNear = false; // Check if alligator is adjacent
        for (int j = 0; j < 6; j++) {
          Hexagon alligator = next.getNeighbour(j);
          if (alligator != null && alligator.isAlligator()) {
            alligatorNear = true;
            break;
          }
        }
        if (alligatorNear && !next.isReedsCell()) {
          continue; // Skip this cell, cant go to it unless it is a reed cell
        }

        double priority = 0.0;
        if (next.isEnd()) { // end cell, 3
          priority = 3.0; 
        } else if (next.isLilyPadCell()) { // lily pad cell, 4
          priority = 4.0;
        } else if (next.isReedsCell() && alligatorNear) { // reed cell + alligator, 10
          priority = 10.0;
        } else if (next.isReedsCell()) { // reed cell, 5
          priority = 5.0;
        } else if (next.isWaterCell()) { // water cell, 6
          priority = 6.0;
        } else if (next instanceof FoodHexagon) { // food cell, 3 - numFlies. Ex. 1 fly is is 3-1 = 2
          priority = 3.0 - ((FoodHexagon) next).getNumFlies();
        }

        // Add to priority queue
        pq.add(next, priority);
      }
    }

    // If current cell is a lily pad cell, end cell, or start cell, it can jump to any cell 2 away
    if (currCell.isLilyPadCell() || currCell.isEnd() || currCell.isStart()){
      for (int i = 0; i < 6; i++) {
        Hexagon next = currCell.getNeighbour(i);
        if (next != null) { // For each neigbour of current, cell, get their neighbours
          // There will be some duplicate cells, but the priority queue will handle that (wont get added same cell)
          for (int j = 0; j < 6; j++) {
            Hexagon next2 = next.getNeighbour(j);

            if (next2 == null) {
              continue; // Skip this cell, doesn't exist
            }

            boolean alligatorNear = false; // Check if alligator is adjacent
            for (int k = 0; k < 6; k++) {
              Hexagon alligator = next2.getNeighbour(k);
              if (alligator != null && alligator.isAlligator()) {
                alligatorNear = true;
                break;
              }
            }
            if (alligatorNear && !next2.isReedsCell()) { // alligator is near and not a reed cell
              continue; // Skip this cell
            }

            // Cell is not marked, alligator, or mud
            if (!next2.isMarked() && !next2.isAlligator() && !next2.isMudCell()) {
              double priority = 0.0;
              if (next2.isEnd()) { // end cell, 3
                priority = 3.0;
              } else if (next2.isLilyPadCell()) { // lily pad cell, 4
                priority = 4.0;
              } else if (next2.isReedsCell() && alligatorNear) { // reed cell + alligator, 10
                priority = 10.0;
              } else if (next2.isReedsCell()) { // reed cell, 5
                priority = 5.0;
              } else if (next2.isWaterCell()) { // water cell, 6
                priority = 6.0;
              } else if (next2 instanceof FoodHexagon) { // food cell, 3 - numFlies. Ex. 1 fly is is 3-1 = 2
                priority = 3.0 - ((FoodHexagon) next2).getNumFlies();
              }

              if (i == j) { // Straight line, add 0.5 to priority
                priority += 0.5;
              } else {
                priority += 1.0; // Not straight line, add 1 to priority
              }
              pq.add(next2, priority);
            }
          }
        }
      }
    }

    if (pq.isEmpty()) {
      return null;
    }

    // Return the cell with the lowest priority
    return pq.peek();
  }

  // Evaluate the entire path
  public String findPath() {
    // Create a stack to store the path
    ArrayStack<Hexagon> stack = new ArrayStack<Hexagon>();
    stack.push(pond.getStart());
    pond.getStart().markInStack();

    // Create a string to store the path and the number of flies eaten
    int fliesEaten = 0;
    String s = "";
    
    while (!stack.isEmpty()) { // While the stack is not empty
      Hexagon curr = stack.peek();
      s += curr.getID() + " ";
      if (curr.isEnd()) {
        break; // End cell reached
      }

      if (curr instanceof FoodHexagon) { // If the current cell is a food cell, eat the flies
        fliesEaten += ((FoodHexagon) curr).getNumFlies();
        ((FoodHexagon)curr).removeFlies();
      }

      Hexagon next = findBest(curr); // Find the best cell to jump to

      if (next == null) { // If there is no cell to jump to, pop the stack
        stack.pop();
        curr.markOutStack();
      } else { // If there is a cell to jump to, push it onto the stack
        stack.push(next);
        next.markInStack();
      }
    }

    // If the stack is empty, there is no solution
    if (stack.isEmpty()) {
      s = "No solution";
    }
    else {
      s += "ate " + fliesEaten + " flies";
    }

    return s;
  }
}
