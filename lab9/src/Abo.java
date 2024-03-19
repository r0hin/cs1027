public class Abo {
  public static int rabo(int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;
    }

    if (n % 2 == 0) { // even
      return 1 + rabo(n/2);
    } else { // odd
      return 2 + rabo((n+1)/2);
    }
  }

  public static void main(String[] args) {
    for (int i = 0; i < 20; i++) {
      System.out.println(rabo(i));
    }
  }  
}
