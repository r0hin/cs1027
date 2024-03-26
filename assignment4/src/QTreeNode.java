public class QTreeNode {
  private int x, y;
  private int size;
  private int color;

  private QTreeNode parent;
  private QTreeNode[] children;

  public QTreeNode() {
    parent = null;
    children = new QTreeNode[4];
    for (int i = 0; i < 4; i++) {
      children[i] = null;
    }
    size = 0;
    x = 0;
    y = 0;
    color = 0;
  }

  public QTreeNode(QTreeNode[] children, int xcoord, int ycoord, int theSize, int theColor) {
    this.children = children;
    this.x = xcoord;
    this.y = ycoord;
    this.size = theSize;
    this.color = theColor;
  }

  public boolean contains(int xcoord, int ycoord) {
    // coordinates of the vertices of the quadrant represented by this object are (x,y), (x+size-1,y), (x,y+size-1), and (x+size-1,y+size-1).
    return x <= xcoord && x + size > xcoord && y <= ycoord && y + size > ycoord;
  }

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

  public QTreeNode getChild(int index) {
    if (index < 0 || index >= 4) {
      throw new QTreeException("Invalid index");
    }

    if (children == null) {
      return null;  
    }

    return children[index];
  }

  public void setParent(QTreeNode theParent) {
    parent = theParent;
  }

  public void setChild(QTreeNode theChild, int index) {
    if (index < 0 || index >= 4) {
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

  public boolean isLeaf() {
    if (children == null) {
      return true;
    }

    return children[0] == null && children[1] == null && children[2] == null && children[3] == null;
  }

}