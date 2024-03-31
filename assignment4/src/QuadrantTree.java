public class QuadrantTree {
  private QTreeNode root; // 

  public QuadrantTree(int[][] thePixels) {
    // Recursively build the tree
    root = buildTree(thePixels, 0, 0, thePixels.length);
  }

  private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
    if (size == 1) {
      // Color but its x left and y down
      return new QTreeNode(null, x, y, size, pixels[y][x]);
    }

    // Recursively build the tree
    QTreeNode[] children = new QTreeNode[4];
    int newSize = size / 2; // Half the size
    children[0] = buildTree(pixels, x, y, newSize); // top left
    children[1] = buildTree(pixels, x + newSize, y, newSize); // top right
    children[2] = buildTree(pixels, x, y + newSize, newSize); // bottom left
    children[3] = buildTree(pixels, x + newSize, y + newSize, newSize); // bottom right

    // Color the average color of the children
    int total = 0;
    for (int i = 0; i < 4; i++) {
      total += children[i].getColor();
    }

    // Return the node
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
      // base case
    }
    else if (theLevel == targetLevel) { // If the level is the same, add it to the list
      ListNode<QTreeNode> latestItem = theList;
      // Get the last item
      while (latestItem.getNext() != null) {
        latestItem = latestItem.getNext();
      }
      latestItem.setNext(new ListNode<QTreeNode>(r));
    }
    else {
      // Recursively go down the tree
      getPixelsHelper(r.getChild(0), theLevel+1, targetLevel, theList); // top left
      getPixelsHelper(r.getChild(1), theLevel+1, targetLevel, theList); // top right
      getPixelsHelper(r.getChild(2), theLevel+1, targetLevel, theList); // bottom left
      getPixelsHelper(r.getChild(3), theLevel+1, targetLevel, theList); // bottom right
    }
  }

  public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
    ListNode<QTreeNode> theList = new ListNode<QTreeNode>(null);
    
    // Calculate total depth
    int totalDepth = height(r);

    // Recursively find the matching nodes
    findMatchingHelper(r, theColor, theLevel, theList, 0, totalDepth);

    // Return the list and the length
    theList = theList.getNext();
    return new Duple(theList, length(theList));
  }

  private void findMatchingHelper(QTreeNode r, int theColor, int theLevel, ListNode<QTreeNode> theList, int currentDepth, int totalDepth) {
    // Traverse the tree and return a list.
    // Comparisons

    if (currentDepth == theLevel || (theLevel > totalDepth && currentDepth == totalDepth )) {
      // If the level is the same, add it to the list
      if (Gui.similarColor(r.getColor(), theColor)) {
        ListNode<QTreeNode> latestItem = theList;
        // Get the last item
        while (latestItem.getNext() != null) {
          latestItem = latestItem.getNext();
        }
        latestItem.setNext(new ListNode<QTreeNode>(r));
      }
    }      

    // Recursively go down the tree
    // This means itll go down to every child and not go further if its a leaf.
    if (!r.isLeaf()) {
      findMatchingHelper(r.getChild(0), theColor, theLevel, theList, currentDepth + 1, totalDepth); // top left
      findMatchingHelper(r.getChild(1), theColor, theLevel, theList, currentDepth + 1, totalDepth); // top right
      findMatchingHelper(r.getChild(2), theColor, theLevel, theList, currentDepth + 1, totalDepth); // bottom left
      findMatchingHelper(r.getChild(3), theColor, theLevel, theList, currentDepth + 1, totalDepth); // bottom right
    }
  }

  public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
    return findNodeHelper(r, theLevel, x, y); // rescurive
  }

  private QTreeNode findNodeHelper(QTreeNode r, int theLevel, int x, int y) {
    // If the level is at 0, return the node
    if (r.contains(x, y) && theLevel == 0) {
      return r;
    }

    // Recursively go down the tree
    if (!r.isLeaf()) {
      for (int i = 0; i < 4; i++) {
        QTreeNode result = findNodeHelper(r.getChild(i), theLevel - 1, x, y);
        if (result != null) return result;
      }
    }

    return null; // If nothing is found, return null
  }

  
  // Helper function to get the length of the list
  // Provided by assingnment in the TestQuadrant.java file
  private static int length(ListNode<QTreeNode> list) {
		int c = 0;
		while (list != null) {
			++c;
			list = list.getNext();
		}
		return c;
	}

  // Helper function to get the height of the tree
  // Provided by assingnment in the TestQuadrant.java file
  private static int height(QTreeNode r) {
		if (r == null) return 0;
		else if (r.isLeaf()) return 0;
		else {
			int h = 0, mh;
			mh = height(r.getChild(0));
			for (int i = 1; i < 4; ++i) {
				h = height(r.getChild(i));
				if (h > mh) mh = h;
			}
			return h + 1;
		}
	}
}
