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
    // Create priority queue
    ArrayUniquePriorityQueue<Hexagon> pq = new ArrayUniquePriorityQueue<Hexagon>();
    // Ad all reachable cells to the priority queue

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

    for (int i = 0; i < 6; i++) {
      Hexagon next = currCell.getNeighbour(i);

      if (next == null) {
        continue;
      }

      if (!next.isMarked() && !next.isAlligator() && !next.isMudCell() ) {
        // Calculate priority

        // Check if alligator is adjacent
        boolean alligatorNear = false;
        for (int j = 0; j < 6; j++) {
          Hexagon alligator = next.getNeighbour(j);
          if (alligator != null && alligator.isAlligator()) {
            alligatorNear = true;
            break;
          }
        }
        if (alligatorNear) {
          continue; // Skip this cell
        }

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

    if (currCell.isLilyPadCell() || currCell.isEnd() || currCell.isStart()){
      // Can jump to any cell 2 away, if in a straight line add 0.5 to priority else add 1.0
      for (int i = 0; i < 6; i++) {
        Hexagon next = currCell.getNeighbour(i);
        if (next != null) {
          for (int j = 0; j < 6; j++) {
            Hexagon next2 = next.getNeighbour(j);

            if (next2 == null) {
              continue;
            }

            // Check if alligator is adjacent
            boolean alligatorNear = false;
            for (int k = 0; k < 6; k++) {
              Hexagon alligator = next2.getNeighbour(k);
              if (alligator != null && alligator.isAlligator()) {
                alligatorNear = true;
                break;
              }
            }
            if (alligatorNear) {
              continue; // Skip this cell
            }

            if (!next2.isMarked() && !next2.isAlligator() && !next2.isMudCell()) {
              double priority = 0.0;
              if (next2.isEnd()) {
                priority = 3.0;
              } else if (next2.isLilyPadCell()) {
                priority = 4.0;
              } else if (next2.isReedsCell()) {
                priority = 5.0;
              } else if (next2.isWaterCell()) {
                priority = 6.0;
              } else if (next2 instanceof FoodHexagon) {
                priority = 3.0 - ((FoodHexagon) next2).getNumFlies();
              }
              if (i == j) {
                priority += 0.5;
              } else {
                priority += 1.0;
              }
              pq.add(next2, priority);
            }
          }
        }
      }
    }

    // Return the cell with the lowest priority
    if (pq.isEmpty()) {
      return null;
    }
    return pq.peek();
  }

  public String findPath() {
    ArrayStack<Hexagon> stack = new ArrayStack<Hexagon>();
    stack.push(pond.getStart());
    pond.getStart().markInStack();
    int fliesEaten = 0;

    String s = "";
    
    while (!stack.isEmpty()) {
      Hexagon curr = stack.peek();
      s += curr.getID() + " ";
      if (curr.isEnd()) {
        break;
      }
      if (curr instanceof FoodHexagon) {
        fliesEaten += ((FoodHexagon) curr).getNumFlies();
        ((FoodHexagon)curr).removeFlies();
      }

      Hexagon next = findBest(curr);
      if (next == null) {
        stack.pop();
        curr.markOutStack();
      } else {
        stack.push(next);
        next.markInStack();
      }
    }

    if (stack.isEmpty()) {
      s = "No solution";
    }
    else {
      s += "ate " + fliesEaten + " flies";
    }

    return s;
  }
}
