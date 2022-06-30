import java.security.SecureRandom;
import java.sql.*;

public class SuperAdmin extends AvailableAdminServices
{
    private boolean isSuperAdmin=true;
   @Override
   public void servicesAvailableToAdmin(String adminId)
   {
       boolean loopElement = true;
       while (loopElement) {
           System.out.println("""
                        Enter any of the below services
                        1.To add books to the list
                        2.To remove books from the list
                        3.To view all user details
                        4.To view particular user details
                        5.To view users with pending late fee
                        6.Make entry if user borrows a book(s)
                        7.Make entry if user paying late fee
                        8.Make entry if user returning a book(s)
                        9.To register/add a new user
                        10.To view all books in the system
                        11.To view only currently available books
                        12.To view particular language books
                        13.To view particular author books
                        14.To view books based on book name
                        15.To view a availability based on book ID
                        16.To remove user from the database
                        17.To remove admin from database
                        18. Add a new Admin to database
                        19.To view all admin details in the database
                        20.To view particular admin details
                        21.To got back
                        22.To exit the application
                    """);
           switch (sc.nextInt())
           {
               case 1 -> addNewBooksToTheList();
               case 2 -> removeBooksFromBookList();
               case 3 -> viewAllUserDetails();
               case 4 -> viewParticularUserDetails();
               case 5 -> viewPendingFeeUsers();
               case 6 -> helpUserBorrowingABook();
               case 7 -> helpUserPayLateFee();
               case 8 -> helpUserReturningABook();
               case 9 -> new UserClass().newUserRegistration();
               case 10 -> viewAllTheBooks();
               case 11 -> viewOnlyAvailableBooksToBorrow();
               case 12 ->viewParticularLanguageBooks();
               case 13 ->viewParticularAuthorBooks();
               case 14 -> viewParticularBooksBasedOnName();
               case 15 -> viewIfABookIsAvailableToBorrowOrNOtBasedOnId();
               case 16 -> removeUserFromDatabase();
               case 17 -> removeAdminFromDatabase();
               case 18 -> addAdminToDatabase();
               case 19 -> viewAllAdminDetails();
               case 20 -> viewParticularAdminDetails();
               case 21 -> {
                   loopElement = false;
                   new AdminClass().startOption();
               }
               case 22 -> {
                   loopElement = false;
                   System.out.println("Exiting the application");
               }
               default -> {
                   System.out.println("wrong option is entered..returning main service menu");
                   servicesAvailableToAdmin(adminId);
               }
           }
       }
   }
   private void removeAdminFromDatabase()
   {
      System.out.println("Enter number of  admin to remove : ");
      int loopCount = sc.nextInt();
      for(int i=0;i<loopCount;i++)
     {
        try {
            System.out.println("Enter the AdminID to remove admin from database :");
            String adminIDToRemove = sc.next();
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            String sql = "select * from adminInfo";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int dummy=0;
            while (rs.next())
            {
                if(rs.getString(1).equals(adminIDToRemove))
                {
                    String sql1 = "delete from adminInfo where adminId=?";
                    PreparedStatement ps = con.prepareStatement(sql1);
                    ps.setString(1,adminIDToRemove);
                    if(ps.executeUpdate()==1)
                    {
                        dummy =1 ;
                        System.out.println("DELETION OF ADMIN DONE SUCCESSFULLY");
                    }
                    ps.close();
                    break;
                }
            }
            if(dummy==0)
            {
                System.out.println("ADMIN ID ENTERED IS NOT FOUND IN THE SYSTEM");
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
    private void addAdminToDatabase()
    {
        try
        {
            long newNumber = generateNewAdminLibraryId()+1;
            String newAdminId = 'A'+String.valueOf(newNumber);
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Enter the below details to create a new admin account  ");
            System.out.println("Enter the password,name,mobile number,super admin or not in respective order");
            String sql="insert into adminInfo values(?,?,?,?,?)";
            PreparedStatement smt=con.prepareStatement(sql);

            String password = generateRandomPassword();
            sc.nextLine();
            String name = sc.nextLine();
            long mobileNumber= sc.nextLong();
            int number = sc.nextInt();
            boolean superAdminChoice;
            if(number==1)
            {
                superAdminChoice = true;
            }
            else
            {
                superAdminChoice=false;
            }
            smt.setString(1,newAdminId);
            smt.setString(2,password);
            smt.setString(3,name);
            smt.setLong(4,mobileNumber);
            smt.setBoolean(5,superAdminChoice);
            if(smt.executeUpdate()!=0)
            {
                System.out.println("Admin Registration done successfully...New Generated Admin Id is : " +newAdminId+"   Password:"+password);
                String sql2 = "update TotalUSER_ADMIN_BOOK_BASECount set count = count +1 where ID=3";
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
            System.out.println("Please check your input from catch");
        }
    }
    private long generateNewAdminLibraryId()
    {
        long count=0;
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from TotalUSER_ADMIN_BOOK_BASECount where ID = 3 ";
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
    private void viewAllAdminDetails()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from adminInfo";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                System.out.println(rs.getString(1)+"  "+rs.getString(3)+"  "+rs.getLong(4)+"  "+rs.getBoolean(5));
            }
            stmt.close();
            con.close();
        }
        catch (Exception ex)
        {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }
    private void viewParticularAdminDetails()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from adminInfo";
            ResultSet rs = stmt.executeQuery(sql);
            int dummy=0;
            System.out.println("Enter the adminId to view the details :");
            String userId = sc.next();
            while(rs.next())
            {

                if(rs.getString(1).equals(userId))
                {
                    dummy=1;
                    System.out.println(rs.getString(1) + "  " + rs.getString(3) + "  " + rs.getLong(4) + "  " + rs.getBoolean(5) );
                }
            }
            if(dummy == 0)
            {
                System.out.println("admin ID entered is not found in the search");
            }
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }
        private static String generateRandomPassword()
        {
            final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#!$%^&*_";

            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 10; i++)
            {
                int randomIndex = random.nextInt(chars.length());
                sb.append(chars.charAt(randomIndex));
            }
            return sb.toString();
        }
}
