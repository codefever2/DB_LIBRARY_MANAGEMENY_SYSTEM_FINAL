import java.sql.*;
import java.sql.Date;
import java.util.*;
public class AvailableAdminServices extends AvailableUserServices {
    Scanner sc = new Scanner(System.in);

    protected void servicesAvailableToAdmin(String adminId) {
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
                        17.To got back
                        18.To exit the application
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
                case 12 -> viewParticularLanguageBooks();
                case 13 -> viewParticularAuthorBooks();
                case 14 -> viewParticularBooksBasedOnName();
                case 15 -> viewIfABookIsAvailableToBorrowOrNOtBasedOnId();
                case 16 -> removeUserFromDatabase();
                case 17 -> {
                    loopElement = false;
                    new AdminClass().startOption();
                }
                case 18 -> {
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

    protected void addNewBooksToTheList()
    {

        try
        {
            System.out.println("Enter the number of books to add :");
            int noOfBooksToAdd = sc.nextInt();
            for(int i=0;i<noOfBooksToAdd;i++)
            {
                long newBookId = generateNewBookId()+1;
                System.out.println("Id :"+newBookId);
                String url = "jdbc:mysql://localhost:3306/Library_Management_system";
                String username = "root";
                String pwd = "password";
                Connection con = DriverManager.getConnection(url, username, pwd);
                Statement stmt1 = con.createStatement();
                System.out.println("Enter the book name , edition,author name and ISBN in respective order");
                String sql = "insert into book_list values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement smt = con.prepareStatement(sql);
                smt.setLong(1, newBookId);
                sc.nextLine();
                smt.setString(2, sc.nextLine());
                smt.setFloat(3, sc.nextFloat());
                sc.nextLine();
                smt.setString(4, sc.nextLine());
                sql = "select * from language_list";
                ResultSet rs = stmt1.executeQuery(sql);
                while (rs.next())
                {
                    System.out.println(rs.getInt(1) + "--->" + rs.getString(2));
                }
                smt.setInt(5, sc.nextInt());
                sql = "select * from publisher_list";
                rs = stmt1.executeQuery(sql);
                while (rs.next())
                {
                    System.out.println(rs.getInt(1) + "--->" + rs.getString(2));
                }
                smt.setLong(6, sc.nextLong());
                smt.setString(7, null);
                sql = "select * from category_list";
                rs = stmt1.executeQuery(sql);
                while (rs.next())
                {
                    System.out.println(rs.getInt(1) + "--->" + rs.getString(2));
                }

                System.out.println("enter the category :");
                smt.setInt(8, sc.nextInt());
                smt.setInt(9, 1);
                smt.setDate(10, null);
                smt.setDate(11, null);
                System.out.println("enter the ISBN ID  :");
                smt.setString(12, sc.next());
                smt.setInt(13,0);

                if(smt.executeUpdate() == 1)
                {
                    System.out.println("Book added successfully");
                    String sql2 = "update TotalUSER_ADMIN_BOOK_BASECount set count = count +1 where ID=1";
                    PreparedStatement psd = con.prepareStatement(sql2);
                    psd.executeUpdate();
                }
                else
                {
                    System.out.println("Please try again.....input given is wrong");
                }
                stmt1.close();
                smt.close();
                con.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("Please check your input");
        }
    }

    protected long generateNewBookId()
    {
        long count=0;
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from TotalUSER_ADMIN_BOOK_BASECount where ID = 1";
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
    protected void removeBooksFromBookList()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Enter the number of books to remove :");
            int noOfBooksToRemove=sc.nextInt();
            for(int i=0;i<noOfBooksToRemove;i++)
            {
                String sql = "delete from book_list where bookID=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                System.out.println("Enter the Book ID to remove : ");
                stmt.setLong(1, sc.nextLong());
                if (stmt.executeUpdate() != 0)
                {
                    System.out.println("Book removed successfully");
                }
                else
                {
                    System.out.println("Entered book ID is not found");
                }
                stmt.close();
            }
            con.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    protected void viewAllUserDetails()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from userinfo";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                System.out.println(rs.getString(1)+"  "+rs.getString(3)+"  "+rs.getLong(4)+"  "+rs.getInt(5)+"  "+rs.getFloat(6));
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
    protected void viewParticularUserDetails()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from userinfo";
            ResultSet rs = stmt.executeQuery(sql);
            int dummy=0;
            System.out.println("Enter the userId to view the details :");
            String userId = sc.next();
            while(rs.next())
            {

                if(rs.getString(1).equals(userId))
                {
                    dummy=1;
                    System.out.println(rs.getString(1) + "  " + rs.getString(3) + "  " + rs.getLong(4) + "  " + rs.getInt(5) + "  " + rs.getFloat(6));
                }
            }
            if(dummy == 0)
            {
                System.out.println("user ID entered is not found in the search");
            }
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }
    protected void viewPendingFeeUsers()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from userinfo";
            ResultSet rs = stmt.executeQuery(sql);
            int dummy=0;
            while(rs.next())
            {
                if(rs.getFloat(6) != 0)
                {
                    if(dummy==0)
                    {
                        System.out.println("user(s) with pending fee are listed below :");
                    }
                    dummy=1;
                    System.out.println(rs.getString(1) + "  " + rs.getString(3) + "  " + rs.getLong(4) + "  " + rs.getInt(5) + "  " + rs.getFloat(6));
                }
            }
            if(dummy==0)
            {
                System.out.println("No users with pending fee found");
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
    protected void helpUserBorrowingABook()
    {

        try
        {
            String userIdInput="";
            long bookID=0;
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Enter the userID :");
            userIdInput = sc.next();
            String sql = "select * from userinfo ";
            Statement stmt = con.createStatement();
            ResultSet rs =stmt.executeQuery(sql);
            int oldBorrowedBookCount=0;
            int bookDummyTemp=0;
            while(rs.next())
            {
                if(rs.getString(1).equals(userIdInput))
                {
                    oldBorrowedBookCount=rs.getInt(5);
                    break;
                }
            }
            if(oldBorrowedBookCount < 3)
            {
                String sql1 = "select * from book_list";
                Statement stmt1 = con.createStatement();
                System.out.println("Enter the book ID :");
                bookID = sc.nextLong();
                float userLateFeeFromDB = 0;
                ResultSet rs2 = stmt1.executeQuery(sql1);
                boolean  bookAvailability=true;
                while(rs2.next())
                {
                    if(rs2.getLong(1)==bookID)
                    {
                        bookAvailability = rs2.getBoolean(9);
                        userLateFeeFromDB = rs2.getFloat(6);
                        break;
                    }
                }
                if (bookAvailability)
                {
                    if(userLateFeeFromDB == 0) {
                        String sql7 = "update userinfo set UserBookBorrowedCount=? where userId=?";
                        PreparedStatement stmt2 = con.prepareStatement(sql7);
                        stmt2.setInt(1, oldBorrowedBookCount + 1);
                        stmt2.setString(2, userIdInput);
                        stmt2.executeUpdate();
                        String sql4 = "update book_list set BookAvailability=? where bookId=? ";
                        PreparedStatement stmt3 = con.prepareStatement(sql4);
                        stmt3.setBoolean(1, false);
                        stmt3.setLong(2, bookID);
                        stmt3.executeUpdate();
                        sql4 = "update book_list set BookBorrowedByUserId=? where bookId=? ";
                        stmt3 = con.prepareStatement(sql4);
                        stmt3.setString(1, userIdInput);
                        stmt3.setLong(2, bookID);
                        stmt3.executeUpdate();
                        sql4 = "update book_list set BookIssuedDate=? where bookId=? ";
                        stmt3 = con.prepareStatement(sql4);
                        stmt3.setDate(1, Date.valueOf(java.time.LocalDate.now()));
                        stmt3.setLong(2, bookID);
                        stmt3.executeUpdate();
                        sql4 = "update book_list set BookExpectedReturnDate=? where bookId=? ";
                        stmt3 = con.prepareStatement(sql4);
                        stmt3.setDate(1, Date.valueOf(java.time.LocalDate.now().plusDays(14)));
                        stmt3.setLong(2, bookID);
                        stmt3.executeUpdate();
                        stmt3.close();

                        if (stmt2.executeUpdate() != 0) {
                            System.out.println("Book borrowing done successfully");
                        } else {
                            System.out.println("please check the input");
                        }
                        stmt2.close();
                        stmt3.close();
                    }
                    else
                    {
                        System.out.println("Kindly inform user about the book Borrowing policy with respect to LateFee collection");
                        System.out.println("Late Fee to be paid by user :"+userLateFeeFromDB);
                    }
                }
                else
                {
                    System.out.println("Book ID entered is not available");
                }
                stmt1.close();
            }
            else
            {
                System.out.println("You have reached the maximum borrowing limit of 3");
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
    protected void helpUserPayLateFee() {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Enter the userID to pay late fee:");
            String userIdInput = sc.next();
            String sql = "select * from userinfo";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            float userLateFeeFromDB = 0;
            while (rs.next()) {
                if (rs.getString(1).equals(userIdInput)) {
                    userLateFeeFromDB = rs.getFloat(6);
                }
            }
            System.out.println("User Late fee balance : :" + userLateFeeFromDB);
            System.out.println("Enter the amount user has paid");
            float userPaidAmount = sc.nextFloat();
            float amountDiff = (userLateFeeFromDB - userPaidAmount);
            sql = "update userinfo set UserLateFee=? where userId=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setFloat(1, amountDiff);
            stmt.setString(2, userIdInput);
            if (stmt.executeUpdate() == 1) {
                System.out.println("Payment update done successfully");
            } else {
                System.out.println("Issue connecting.....Kindly try again");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }
    protected void helpUserReturningABook()
    {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Enter the userID who returns a book:");
            String userIdInput = sc.next();
            System.out.println("Enter the BOOK ID to be returned:");
            long userBookIDInput = sc.nextLong();
            String sql = "select * from book_list";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            float userLateFeeFromDB = 0;
            int dummy=0;
            while (rs.next())
            {
                    if (rs.getString(7).equals(userIdInput))
                    {
                        dummy = 1;
                        String x = "select * from userinfo";
                        Statement st1 = con.createStatement();
                        ResultSet rs2 = st1.executeQuery(x);
                        int count =0;
                        while(rs2.next())
                        {
                            if(rs2.getString(1).equals(userIdInput))
                            {
                                count=rs2.getInt(5);
                                userLateFeeFromDB=rs2.getFloat(6);
                                break;
                            }
                        }
                        if(userLateFeeFromDB != 0)
                        {
                            System.out.println("Kindly inform user about the book Borrowing policy with respect to LateFee collection");
                            System.out.println("Late Fee to be paid by user :"+userLateFeeFromDB);
                        }
                        st1.close();
                        rs2.close();
                        String sql3 = "update userinfo set UserBookBorrowedCount=? where userId=?";
                        PreparedStatement stmt = con.prepareStatement(sql3);
                        stmt.setInt(1, --count);
                        stmt.setString(2, userIdInput);
                        stmt.executeUpdate();
                        sql3 = "update book_list set BookBorrowedByUserId=? where bookId=?";
                        stmt = con.prepareStatement(sql3);
                        stmt.setString(1, "0");
                        stmt.setLong(2, userBookIDInput);
                        stmt.executeUpdate();
                        stmt.close();
                        sql3 = "update book_list set BookAvailability=? where bookId=?";
                        stmt = con.prepareStatement(sql3);
                        stmt.setBoolean(1, true);
                        stmt.setLong(2, userBookIDInput);
                        stmt.executeUpdate();
                        sql3="update book_list set BookIssuedDate=? where bookId=? ";
                        stmt = con.prepareStatement(sql3);
                        stmt.setDate(1, null);
                        stmt.setLong(2,userBookIDInput);
                        stmt.executeUpdate();
                        sql3="update book_list set BookExpectedReturnDate=? where bookId=? ";
                        stmt = con.prepareStatement(sql3);
                        stmt.setDate(1, null);
                        stmt.setLong(2,userBookIDInput);
                        stmt.executeUpdate();
                        stmt.close();
                        sql3="update book_list set Eventstatus=? where bookId=? ";
                        stmt = con.prepareStatement(sql3);
                        stmt.setInt(1, 0);
                        stmt.setLong(2,userBookIDInput);
                        stmt.executeUpdate();
                        stmt.close();
                        stmt.close();
                        break;
                    }
                }
            if(dummy==0)
            {
                    System.out.println("The user has not borrowed the mentioned book..Kindly check again");
            }
            else
            {
                System.out.println("Returning of book is acknowledged successfully");
            }
                stm.close();
            }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    protected void removeUserFromDatabase()
    {
        System.out.println("Enter number of users to remove : ");
        int loopCount = sc.nextInt();
        for(int i=0;i<loopCount;i++)
        {
            try {
                System.out.println("Enter the userID to remove user from database :");
                String userIDToRemove = sc.next();
                String url = "jdbc:mysql://localhost:3306/Library_Management_system";
                String username = "root";
                String pwd = "password";
                Connection con = DriverManager.getConnection(url, username, pwd);
                String sql = "select * from userInfo";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                int dummy=0;
                while (rs.next())
                {
                    if(rs.getString(1).equals(userIDToRemove))
                    {
                        String sql2 = "insert into user_history_table values(?,?,?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(sql2);
                        ps.setString(1,rs.getString(1));
                        ps.setString(2,rs.getString(2));
                        ps.setString(3,rs.getString(3));
                        ps.setLong(4,rs.getLong(4));
                        ps.setInt(5,rs.getInt(5));
                        ps.setFloat(6,rs.getFloat(6));
                        ps.executeUpdate();
                        String sql1 = "delete from userInfo where userId=?";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1,userIDToRemove);
                        if(ps.executeUpdate()==1)
                        {
                            dummy =1 ;
                            System.out.println("DELETION OF USER DONE SUCCESSFULLY");
                        }
                        ps.close();
                        break;
                    }
                }
                if(dummy==0)
                {
                    System.out.println("USER ID ENTERED IS NOT FOUND IN THE SYSTEM");
                }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }
    }
}


