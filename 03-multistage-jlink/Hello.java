public class Hello {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please provide a name.");
    } else {
      System.out.printf("Hello %s!%n", args[0]);
    }
  }
}
