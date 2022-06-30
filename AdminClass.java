import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Scanner;

public class AdminClass extends LibraryClass
{
    private String adminName;
    private String adminPassword;
    private long adminMobileNumber;
    private String adminId;
    private boolean isSuperAdmin;
   private Scanner sc = new Scanner(System.in);

    protected void startOption() {
        boolean temp = true;
        while (temp) {
            System.out.println("""
                    Enter
                    1.To login to the system
                    2.To view registered adminID
                    3.To go back to main menu
                    4.To reset the password
                    5.To exit the application
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
                case 5 -> {
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
        System.out.println("Welcome to Admin Login page ");
        System.out.println("Enter the Admin Library ID :");
        String adminIdInput = sc.next();
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from adminInfo where passwordChange=0";
            ResultSet rs =stmt.executeQuery(sql);
            while(rs.next())
            {
                if(rs.getString(1).equals(adminIdInput))
                {
                    if (!rs.getBoolean(6)) {
                        boolean update = false;
                        while (!update) {
                            System.out.println("First time login done , kindly reset your admin password");
                            update = compulsoryAdminPasswordChange(adminIdInput);
                        }
                    }
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


        System.out.println("Enter the password :");
        String adminInputPassword = sc.next();
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select AdminId,AdminPassword,superAdmin from AdminInfo";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                if (Objects.equals(rs.getString(1), adminIdInput)) {
                    tempId = 1;
                    if (Objects.equals(rs.getString(2), adminInputPassword)) {
                        tempPassword = 1;
                        System.out.println("Admin Login Done Successfully");
                        if(rs.getBoolean(3))
                        {
                            new SuperAdmin().servicesAvailableToAdmin(adminIdInput);
                        }
                        else {
                            new AvailableAdminServices().servicesAvailableToAdmin(adminIdInput);
                        }
                        break;
                    }
                }
            }
            if (tempId == 0) {
                System.out.println("Admin ID entered is not registered in the system..kindly check again");
                startOption();
            }
            else if (tempPassword == 0) {
                System.out.println("Password entered is incorrect....Kindly try again");
                startOption();
            }
        } catch (Exception ex) {
            System.out.println("Not Connected at getUserOrAdminDetailsAtEntry");
            ex.printStackTrace();
        }
    }
    protected boolean compulsoryAdminPasswordChange(String adminIdInput)
    {
        boolean update = false;
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "update adminInfo set adminPassword=? where adminId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            int dummy=0;
            String password="";
            while(dummy == 0)
            {
                System.out.println("Enter the password " +
                        "(Password should contain a minimum of 8 characters,a uppercase letter,a lowercase letter,a special character and a number):");
                password = sc.next();
                if(passwordValidation(password))
                {
                    dummy=1;
                }
                else
                {
                    System.out.println("Password not satisfying the conditions");
                }
            }
            ps.setString(1, password);
            ps.setString(2, adminIdInput);
            int success = ps.executeUpdate();
            if (success ==1) {
                update=true;
                System.out.println("password changed successfully");
                try {
                    String sql1 = "update adminInfo set passwordChange=1 where adminId=?";
                    PreparedStatement stmt2 = con.prepareStatement(sql1);
                    stmt2.setString(1,adminIdInput);
                    if(stmt2.executeUpdate()==1)
                    {
                        System.out.println("update done");
                    }
                    stmt2.close();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            } else {
                update=false;
                System.out.println("password update unsuccessful");
            }

        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
        return update;
    }
    protected void viewRegisteredId() {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select AdminID,AdminMobileNumber from AdminInfo";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("To view the AdminID , enter the registered mobile NUmber");
            long adminInputMobileNumber = sc.nextLong();
            while (rs.next()) {
                if (rs.getLong(2) == adminInputMobileNumber) {
                    System.out.println("your registered Admin ID : " + rs.getString(1));
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Not Connected at getUserOrAdminDetailsAtEntry");
            ex.printStackTrace();
        }
    }

    protected void resetPassword()
    {
        System.out.println("Enter the user Id : ");
        String adminIdInput = sc.next();
        System.out.println("Enter the registered mobile number : ");
        long adminInputMobileNumber = sc.nextLong();
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "update adminInfo set adminPassword=? where adminId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.println("enter the new password :");
            String newPassword = sc.next();
            ps.setString(1, newPassword);
            ps.setString(2, adminIdInput);
            int success = ps.executeUpdate();
            if (success != 0) {
                System.out.println("password changed successfully");
            } else {
                System.out.println("Admin Id and mobile number entered doesn't match");
            }

        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }
}

