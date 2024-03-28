public class QTreeNode {
  private int x, y; // coordinates of the top left corner of the quadrant
  private int size; // size of the quadrant
  private int color; // color of the quadrant

  // parent and children array of the quadrant
  private QTreeNode parent;
  private QTreeNode[] children;

  // Constructor
  public QTreeNode() {
    // Null values
    parent = null;
    children = new QTreeNode[4];
    for (int i = 0; i < 4; i++) {
      children[i] = null;
    }
    // Zero values
    size = 0;
    x = 0;
    y = 0;
    color = 0;
  }

  // Overloaded constructor
  public QTreeNode(QTreeNode[] children, int xcoord, int ycoord, int theSize, int theColor) {
    // Assign the parameters to the instance variables
    this.children = children; 
    this.x = xcoord;
    this.y = ycoord;
    this.size = theSize;
    this.color = theColor;
  }

  // Check if the quadrant contains the given coordinates
  public boolean contains(int xcoord, int ycoord) {
    // coordinates of the vertices of the quadrant represented by this object are (x,y), (x+size-1,y), (x,y+size-1), and (x+size-1,y+size-1).
    return x <= xcoord && x + size > xcoord && y <= ycoord && y + size > ycoord;
  }

  // Getters and setters
  public int getx() {
    return x;
  }

  public int gety() {
    return y;
  }

  public int getSize() {
    return size;
  }

  public int getColor() {
    return color;
  }

  public QTreeNode getParent() {
    return parent;
  }

  // Get the child at the given index
  public QTreeNode getChild(int index) {
    if (index < 0 || index > 3) { // Check if the index is valid
      throw new QTreeException("Invalid index");
    }

    if (children == null) { // Check if the children array is null
      return null;  
    }

    // Return the child at the given index
    return children[index];
  }

  // Setters
  public void setParent(QTreeNode theParent) {
    parent = theParent;
  }

  // Set the child at the given index
  public void setChild(QTreeNode theChild, int index) {
    if (index < 0 || index > 3) { // Check if the index is valid
      throw new QTreeException("Invalid index");
    }

    children[index] = theChild;
  }

  public void setx(int xcoord) {
    x = xcoord;
  }

  public void sety(int ycoord) {
    y = ycoord;
  }

  public void setSize(int theSize) {
    size = theSize;
  }

  public void setColor(int theColor) {
    color = theColor;
  }

  // Check if the quadrant is a leaf
  public boolean isLeaf() {
    if (children == null) {
      return true; // If the children array is null, the quadrant is a leaf
    }

    // If not, then check if all the children are null
    return children[0] == null && children[1] == null && children[2] == null && children[3] == null;
  }

}