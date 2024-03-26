public class QuadrantTree {
  private QTreeNode root;

  public QuadrantTree(int[][] thePixels) {
    // Recursively build the tree
    root = buildTree(thePixels, 0, 0, thePixels.length);
  }

  private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
    if (size == 1) {
      // Color but its x left and y down
      return new QTreeNode(null, x, y, size, pixels[y][x]);
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
    getPixelsHelper(r, 0, theLevel, theList);
    return theList.getNext();
  }

  // Level 1 should return 4 nodes, go down the levels and if its the same, set next on the list otherwise dont add it
  private void getPixelsHelper(QTreeNode r, int theLevel, int targetLevel, ListNode<QTreeNode> theList) {
    if (r == null) {
    }
    else if (theLevel == targetLevel) {
      // ListNode<QTreeNode> theList = new ListNode<QTreeNode>(r);
      // return theList;
      ListNode<QTreeNode> latestItem = theList;
      while (latestItem.getNext() != null) {
        latestItem = latestItem.getNext();
      }
      latestItem.setNext(new ListNode<QTreeNode>(r));
    }
    else {
      getPixelsHelper(r.getChild(0), theLevel+1, targetLevel, theList);
      getPixelsHelper(r.getChild(1), theLevel+1, targetLevel, theList);
      getPixelsHelper(r.getChild(2), theLevel+1, targetLevel, theList);
      getPixelsHelper(r.getChild(3), theLevel+1, targetLevel, theList);
    }
  }

  public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
    ListNode<QTreeNode> theList = new ListNode<QTreeNode>(null);
    
    // Calculate total depth
    int totalDepth = -1;
    QTreeNode current = r;
    while (current != null) {
      totalDepth++;
      current = current.getChild(0);
    }

    findMatchingHelper(r, theColor, theLevel, theList, 0, totalDepth);
    return new Duple(theList.getNext(), length(theList.getNext()));
  }

  private void findMatchingHelper(QTreeNode r, int theColor, int theLevel, ListNode<QTreeNode> theList, int currentDepth, int totalDepth) {
    // Traverse the tree and return a list.
    if (r == null) {
    }
    else {
      // Comparisons
      if (currentDepth == theLevel || (theLevel > totalDepth && currentDepth == totalDepth )) {
        if (Gui.similarColor(r.getColor(), theColor)) {
          ListNode<QTreeNode> latestItem = theList;
          while (latestItem.getNext() != null) {
            latestItem = latestItem.getNext();
          }
          latestItem.setNext(new ListNode<QTreeNode>(r));
        }
      }      

      findMatchingHelper(r.getChild(0), theColor, theLevel, theList, currentDepth + 1, totalDepth);
      findMatchingHelper(r.getChild(1), theColor, theLevel, theList, currentDepth + 1, totalDepth);
      findMatchingHelper(r.getChild(2), theColor, theLevel, theList, currentDepth + 1, totalDepth);
      findMatchingHelper(r.getChild(3), theColor, theLevel, theList, currentDepth + 1, totalDepth);
    }
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
    // int[][] pixels = new int[][] {
    //   {2, 2, 8, 8},
    //   {4, 4, 4, 4},
    //   {2, 8, 1, 1},
    //   {8, 6, 1, 1}
    // };

    int[][] pixels = new int[32][32];
		for (int i = 0; i < 32; ++i)
			for (int j = 0; j < 32; ++j)
				pixels[i][j] = i;

    QuadrantTree tree = new QuadrantTree(pixels);
    QTreeNode root = tree.getRoot();

    ListNode<QTreeNode> pixels1 = tree.getPixels(root, 5);

    int thelength = length(pixels1);
    System.out.println(thelength);

    // Print it out
    // ListNode<QTreeNode> current = pixels1;
    // while (current != null) {
    //   System.out.println(current.getData().getColor());
    //   current = current.getNext();
    // }

  }
  
  private static int length(ListNode<QTreeNode> list) {
		int c = 0;
		while (list != null) {
			++c;
			list = list.getNext();
		}
		return c;
	}
}


