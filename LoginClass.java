import java.util.Scanner;
public class LoginClass {
    Scanner sc = new Scanner(System.in);
    public  void welcomeMessage()
    {
        System.out.println("""
                Enter
                1.User
                2.Admin
                3.To exit the application
                """);
        int userOrAdminChoice = sc.nextInt();
        switch (userOrAdminChoice)
        {
            case 1 -> new UserClass().startOption();
            case 2 -> new AdminClass().startOption();
            case 3 -> System.out.println("Exiting the application.....");
            default ->{
                        System.out.println("Wrong choice is chosen ,please try again");
                        welcomeMessage();
                      }
        }
    }

}
