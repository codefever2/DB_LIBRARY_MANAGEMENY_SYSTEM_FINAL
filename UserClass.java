import com.sun.net.httpserver.Authenticator;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;
public class UserClass extends LibraryClass
{
    private String userName;
    private long userMobileNumber;
    private String userPassword;
    private int userBooksBorrowedCount;
    private double userLateFee;
    private String userLibraryId;
    private Scanner sc = new Scanner(System.in);

    protected void startOption()
    {
        boolean temp = true;
        while (temp) {
            System.out.println("""
                    Enter
                    1.To login to the system
                    2.To view registered userID
                    3.To go back to main menu
                    4.To reset the password
                    5.To register as a new user
                    6.To exit the application
                    """);
            switch (sc.nextInt()) {
                case 1 -> {
                    getUserOrAdminDetailsAtEntry();
                    temp = false;
                }
                case 2 -> viewRegisteredId();
                case 3 -> {
                    new LoginClass().welcomeMessage();
                    temp = false;
                }
                case 4 -> resetPassword();
                case 5 -> newUserRegistration();
                case 6 -> {
                    System.out.println("Exiting the application");
                    temp = false;
                }
                default -> {
                    System.out.println("Wrong option is chosen..kindly try again");
                }
            }
        }
    }

    @Override
    protected void getUserOrAdminDetailsAtEntry() {
        int tempId = 0, tempPassword = 0;
        System.out.println("Welcome to User Login page ");
        System.out.println("Enter the User Library ID :");
        String userIdInput = sc.next();
        System.out.println("Enter the password :");
        String userInputPassword = sc.next();
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select UserId,UserPassword from userinfo";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (Objects.equals(rs.getString(1), userIdInput)) {
                    tempId = 1;
                    if (Objects.equals(rs.getString(2), userInputPassword)) {
                        tempPassword = 1;
                        System.out.println("User Login Done Successfully");
                        new AvailableUserServices().servicesAvailableToUser(userIdInput);
                        break;
                    }
                }
            }
            if (tempId == 0) {
                System.out.println("User ID entered is not registered in the system...kindly check again");
                startOption();
            }
            else if (tempPassword == 0) {
                System.out.println("Password entered is incorrect....Kindly try again");
                startOption();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Not Connected at getUserOrAdminDetailsAtEntry");
            ex.printStackTrace();
        }
    }

    protected void viewRegisteredId() {
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select UserID,UserMobileNumber from userinfo";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("To view the UserID , enter the registered mobile NUmber");
            long userInputMobileNumber = sc.nextLong();
            while (rs.next())
            {
                if (rs.getLong(2) == userInputMobileNumber)
                {
                    System.out.println("your registered userID : " + rs.getString(1));
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Not Connected at getUserOrAdminDetailsAtEntry");
            ex.printStackTrace();
        }
    }
    public void resetPassword() {
        System.out.println("Enter the user Id : ");
        String userIdInput = sc.next();
        System.out.println("Enter the registered mobile number : ");
        long userInputMobileNumber = sc.nextLong();
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "update userInfo set userPassword=? where userid=?";
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.println("enter the new password :");
            String newPassword = sc.next();
            ps.setString(1, newPassword);
            ps.setString(2, userIdInput);
            int success = ps.executeUpdate();
            if (success != 0)
            {
                System.out.println("password changed successfully");
            }
            else {
                System.out.println("userId and mobile number entered doesn't match");
            }

        }
        catch (Exception ex)
        {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }

    protected void newUserRegistration()
    {
        try {
            long newNumber = generateNewUserLibraryId()+1;
            System.out.println("new number :"+newNumber);
            String newUserId = 'U'+String.valueOf(newNumber);
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Enter the below details to register yourself ");
            System.out.println("Enter the name,mobile number in respective order");
            String sql="insert into userInfo values(?,?,?,?,?,?)";
            PreparedStatement smt=con.prepareStatement(sql);
            int dummy=0;
            String password="";
            while(dummy == 0)
            {
                boolean update=false;
                System.out.println("Enter the password " +
                        "(Password should contain a minimum of 8 characters,a uppercase letter,a lowercase letter,a special character and a number):");
                password = sc.next();
                if(passwordValidation(password))
                {
                    break;
                }
                else
                {
                    System.out.println("Password not satisfying the conditions");
                }
            }
            sc.nextLine();
            System.out.println("Enter the name,mobile number in respective order");
            String name = sc.nextLine();
            long mobileNumber = sc.nextLong();
            smt.setString(1,newUserId);
            smt.setString(2,password);
            smt.setString(3,name);
            smt.setLong(4,mobileNumber);
            smt.setInt(5,0);
            smt.setFloat(6,0);
            if(smt.executeUpdate()!=0)
            {
                System.out.println("User Registration done successfully...Your UserId is" +newUserId);
                String sql2 = "update TotalUSER_ADMIN_BOOK_BASECount set count = count +1 where ID=2";
                PreparedStatement psd = con.prepareStatement(sql2);
                psd.executeUpdate();
            }
            else
            {

                System.out.println("Please check your input");
            }
        }
        catch(Exception e)
        {
            System.out.println("Please check your input");
        }

    }
    protected long generateNewUserLibraryId()
    {
        long count=0;
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from TotalUSER_ADMIN_BOOK_BASECount where ID = 2";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                count = rs.getLong(2);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("");
        }
        return count;
    }

}
