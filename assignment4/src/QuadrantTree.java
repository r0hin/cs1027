public class QuadrantTree {
  private QTreeNode root;

  public QuadrantTree(int[][] thePixels) {
    // Recursively build the tree
    root = buildTree(thePixels, 0, 0, thePixels.length);
  }

  private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
    if (size == 1) {
      return new QTreeNode(null, x, y, size, pixels[x][y]);
    }

    QTreeNode[] children = new QTreeNode[4];
    int newSize = size / 2;
    children[0] = buildTree(pixels, x, y, newSize); // top left
    children[1] = buildTree(pixels, x + newSize, y, newSize); // top right
    children[2] = buildTree(pixels, x, y + newSize, newSize); // bottom left
    children[3] = buildTree(pixels, x + newSize, y + newSize, newSize); // bottom right

    // Color the average color of the children
    int total = 0;
    for (int i = 0; i < 4; i++) {
      total += children[i].getColor();
    }

    return new QTreeNode(children, x, y, size, total / 4);
  }

  public QTreeNode getRoot() {
    return root;
  }

  public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
    // Recursively get the pixels on the given level
    ListNode<QTreeNode> theList = new ListNode<QTreeNode>(null);
    getPixelsHelper(r, theLevel, 0, theList);
    return theList.getNext();
  }

  private void getPixelsHelper(QTreeNode r, int theLevel, int currentLevel, ListNode<QTreeNode> theList) {
    if (r == null) {
      return;
    }

    if (currentLevel == theLevel) {
      theList.setNext(new ListNode<QTreeNode>(r));
    } else {
      for (int i = 0; i < 4; i++) {
        getPixelsHelper(r.getChild(i), theLevel, currentLevel + 1, theList);
      }
    }
  }

  public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
    // Traversal of the tree: If r is a leaf or theLevel = 0 then (i) if the color of r is similar to theColor return a Duple object storing a list containing r and the value 1; (ii) what value do you return if the color of r is not similar to theColor? If r is not a leaf or theLevel > 0 then perform recursive calls on the children of r concatenating the lists inside the Duple objects that these calls return (what are the values of the parameters in these calls?); return the final Duple object (what is the value of the instance variable count of this Duple object?).
    return findMatchingHelper(r, theColor, theLevel);
  }

  private Duple findMatchingHelper(QTreeNode r, int theColor, int theLevel) {
    if (r == null) {
      return new Duple();
    }

    if (r.getSize() == 1 || theLevel == 0) {
      if (r.getColor() == theColor) {
        ListNode<QTreeNode> theList = new ListNode<QTreeNode>(r);
        return new Duple(theList, 1);
      } else {
        return new Duple();
      }
    }

    Duple[] children = new Duple[4];
    for (int i = 0; i < 4; i++) {
      children[i] = findMatchingHelper(r.getChild(i), theColor, theLevel - 1);
    }

    ListNode<QTreeNode> theList = new ListNode<QTreeNode>(null);
    int total = 0;
    for (int i = 0; i < 4; i++) {
      theList = concatenate(theList, children[i].getFront());
      total += children[i].getCount();
    }

    return new Duple(theList, total);
  }

  private ListNode<QTreeNode> concatenate(ListNode<QTreeNode> list1, ListNode<QTreeNode> list2) {
    ListNode<QTreeNode> current = list1;
    while (current.getNext() != null) {
      current = current.getNext();
    }

    current.setNext(list2);
    return list1;
  }

  public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
    // Returns a node in the subtree rooted at r and at level theLevel representing a quadrant containing the point (x,y); it returns null if such a node does not exist. Recursive
    return findNodeHelper(r, theLevel, x, y);
  }

  private QTreeNode findNodeHelper(QTreeNode r, int theLevel, int x, int y) {
    if (r == null) {
      return null;
    }

    if (r.contains(x, y) && theLevel == 0) {
      return r;
    }

    for (int i = 0; i < 4; i++) {
      QTreeNode result = findNodeHelper(r.getChild(i), theLevel - 1, x, y);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

  public static void main(String[] args) {
    // Test cases

    // 2 2 8 8
    // 4 4 4 4
    // 2 8 1 1
    // 8 6 1 1
    int[][] pixels = new int[][] {
      {2, 2, 8, 8},
      {4, 4, 4, 4},
      {2, 8, 1, 1},
      {8, 6, 1, 1}
    };

    QuadrantTree tree = new QuadrantTree(pixels);
    QTreeNode root = tree.getRoot();
    ListNode<QTreeNode> pixels1 = tree.getPixels(root, 1);

    // Print
    ListNode<QTreeNode> current = pixels1;
    while (current != null) {
      QTreeNode node = current.getData();
      System.out.println(node.getColor());
      current = current.getNext();
    }
  }
}


