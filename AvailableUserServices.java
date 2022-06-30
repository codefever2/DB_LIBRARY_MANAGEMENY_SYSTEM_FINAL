import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.*;
public class AvailableUserServices {
    private Scanner sc = new Scanner(System.in).useDelimiter("\n");

    protected void servicesAvailableToUser(String userId)
    {
         boolean loopElement = true;
        while (loopElement) {
            System.out.println("""
                    Enter any of the below services
                    1.To view all the books in the list
                    2.To view only currently available books
                    3.To view particular language books
                    4.To search books based on book name
                    5.To view a book availability based on BOOK ID
                    6.To view the late fee If any
                    7.To view particular publisher book
                    8.To view books borrowed till date
                    9.To view available limit to borrow books
                    10.To update mobile number
                    11.To view particular category books;
                    12.To view books based on ISBN 
                    13.To view books based on author name
                    14.To go back
                    15.To exit the application
                    """);
            switch (sc.nextInt()) {
                case 1 -> viewAllTheBooks();
                case 2 -> viewOnlyAvailableBooksToBorrow();
                case 3 -> viewParticularLanguageBooks();
                case 4 -> viewParticularBooksBasedOnName();
                case 5 -> viewIfABookIsAvailableToBorrowOrNOtBasedOnId();
                case 6 -> viewLateFeeIfAny(userId);
                case 7 -> viewParticularPublisherBooks();
                case 8 -> viewBorrowedBooks(userId);
                case 9 -> viewBorrowLimit(userId);
                case 10 -> setMobileNumber(userId);
                case 11 -> viewParticularCategoryBooks();
                case 12 -> viewBooksBasedOnISBN();
                case 13 -> viewParticularAuthorBooks();
                case 14 -> {
                    loopElement = false;
                    new UserClass().startOption();
                }
                case 15 -> {
                    loopElement = false;
                    System.out.println("Exiting the application");
                }
                default -> {
                    System.out.println("wrong option is entered..returning main service menu");
                    servicesAvailableToUser(userId);
                }
            }
        }
    }

    protected void viewAllTheBooks() {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from book_list";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Statement stmt1 = con.createStatement();
                String sql1 = "select * from language_list";
                ResultSet rs1 = stmt1.executeQuery(sql1);
                String lang = "";
                while (rs1.next()) {
                    if (rs1.getInt(1) == rs.getInt(5)) {
                        lang = rs1.getString(2);
                        break;
                    }
                }
                sql1 = "select * from category_list";
                rs1 = stmt1.executeQuery(sql1);
                String category = "";
                while (rs1.next()) {
                    if (rs1.getInt(1) == rs.getInt(8)) {
                        category = rs1.getString(2);
                        break;
                    }
                }
                String availability = "Available";
                if (rs.getInt(9) == 0) {
                    availability = "Not Available";
                }
                sql1 = "select * from publisher_list";
                rs1 = stmt1.executeQuery(sql1);
                String publisher = "";
                while (rs1.next()) {
                    if (rs1.getInt(1) == rs.getInt(6)) {
                        publisher = rs1.getString(2);
                        break;
                    }
                }
                stmt1.close();
                System.out.println(rs.getLong(1) + "   " + rs.getString(2) + "  " + rs.getFloat(3) + "  " + rs.getString(4) + "  " + lang + "    " + publisher + "    " + rs.getString(7) + "   " + category + "  " + availability + "   " + rs.getDate(10) + "      " + rs.getDate(11) + "    " + rs.getString(12));
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }

    protected void viewOnlyAvailableBooksToBorrow() {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from book_list";
            ResultSet rs = stmt.executeQuery(sql);
            int dummy = 0;
            while (rs.next()) {
                if (rs.getInt(9) == 1) {
                    dummy = 1;
                    Statement stmt1 = con.createStatement();
                    String sql1 = "select * from language_list";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    String lang = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(5)) {
                            lang = rs1.getString(2);
                            break;
                        }
                    }
                    sql1 = "select * from category_list";
                    rs1 = stmt1.executeQuery(sql1);
                    String category = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(8)) {
                            category = rs1.getString(2);
                            break;
                        }
                    }
                    String availability = "Available";
                    if (rs.getInt(9) == 0) {
                        availability = "Not Available";
                    }
                    sql1 = "select * from publisher_list";
                    rs1 = stmt1.executeQuery(sql1);
                    String publisher = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(6)) {
                            publisher = rs1.getString(2);
                            break;
                        }
                    }
                    stmt1.close();
                    System.out.println(rs.getLong(1) + "   " + rs.getString(2) + "  " + rs.getFloat(3) + "  " + rs.getString(4) + "  " + lang + "    " + publisher + "    " + rs.getString(7) + "   " + category + "  " + availability + "   " + rs.getDate(10) + "      " + rs.getDate(11) + "    " + rs.getString(12));
                }
            }
            if (dummy == 0) {
                System.out.println("No available books to show");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }

    protected void viewParticularLanguageBooks() {
        System.out.println("CHOOSE THE OPTION TO VIEW LANGUAGE_SPECIFIC BOOKS ");
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from language_list";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "--->" + rs.getString(2));
            }
            int langChosenByUser = sc.nextInt();
            sql = "select * from book_list";
            rs = stmt.executeQuery(sql);
            int dummyTemp = 0;
            while (rs.next()) {
                if (rs.getInt(5) == langChosenByUser) {
                    dummyTemp = 1;
                    Statement stmt1 = con.createStatement();
                    String sql1 = "select * from category_list";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    String category = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(8)) {
                            category = rs1.getString(2);
                            break;
                        }
                    }
                    String availability = "Available";
                    if (rs.getInt(9) == 0) {
                        availability = "Not Available";
                    }
                    sql1 = "select * from publisher_list";
                    rs1 = stmt1.executeQuery(sql1);
                    String publisher = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(6)) {
                            publisher = rs1.getString(2);
                            break;
                        }
                    }
                    stmt1.close();
                    System.out.println(rs.getLong(1) + "   " + rs.getString(2) + "  " + rs.getFloat(3) + "  " + rs.getString(4) + "    " + publisher + "    " + rs.getString(7) + "   " + category + "  " + availability + "   " + rs.getDate(10) + "      " + rs.getDate(11) + "    " + rs.getString(12));
                }
            }
            if (dummyTemp == 0) {
                System.out.println("Wrong option is chosen ....Kindly try again");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }

    protected void viewParticularCategoryBooks() {
        System.out.println("CHOOSE THE OPTION TO VIEW CATEGORY_SPECIFIC BOOKS ");
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from category_list";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "--->" + rs.getString(2));
            }
            int categoryChosenByUser = sc.nextInt();
            sql = "select * from book_list";
            rs = stmt.executeQuery(sql);
            int dummyTemp = 0;
            while (rs.next()) {
                if (rs.getInt(8) == categoryChosenByUser) {
                    dummyTemp = 1;
                    Statement stmt1 = con.createStatement();
                    String sql1 = "select * from language_list";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    String language = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(5)) {
                            language = rs1.getString(2);
                            break;
                        }
                    }
                    String availability = "Available";
                    if (rs.getInt(9) == 0) {
                        availability = "Not Available";
                    }
                    sql1 = "select * from publisher_list";
                    rs1 = stmt1.executeQuery(sql1);
                    String publisher = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(6)) {
                            publisher = rs1.getString(2);
                            break;
                        }
                    }
                    stmt1.close();
                    System.out.println(rs.getLong(1) + "   " + rs.getString(2) + "  " + rs.getFloat(3) + "  " + rs.getString(4) + "    " + language + "  " + publisher + "    " + rs.getString(7) + "  " + availability + "   " + rs.getDate(10) + "      " + rs.getDate(11) + "    " + rs.getString(12));
                }
            }
            if (dummyTemp == 0) {
                System.out.println("Wrong option is chosen ....Kindly try again");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }

    protected void setMobileNumber(String userIdInput) {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "update userInfo set userMobileNumber=? where userid=?";
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.println("Enter the mobile number to update :");
            long userMobileNumber = sc.nextLong();
            ps.setLong(1, userMobileNumber);
            ps.setString(2, userIdInput);
            int success = ps.executeUpdate();
            if (success != 0) {
                System.out.println("Mobile number updated successfully");
            } else {
                System.out.println("userID entered is not registered");
            }

        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }

    protected void viewBorrowLimit(String userIdInput) {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from userinfo";
            ResultSet success = stmt.executeQuery(sql);
            while (success.next()) {
                if (success.getString(1).equals(userIdInput)) {
                    System.out.println("you have borrowed " + success.getInt(5) + " from the library");
                    break;
                }
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }

    protected void viewParticularPublisherBooks() {
        System.out.println("CHOOSE THE OPTION TO VIEW PUBLISHER_SPECIFIC BOOKS ");
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from publisher_list";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "--->" + rs.getString(2));
            }
            int publisherChosenByUser = sc.nextInt();
            sql = "select * from book_list";
            rs = stmt.executeQuery(sql);
            int dummyTemp = 0;
            while (rs.next()) {
                if (rs.getInt(6) == publisherChosenByUser) {
                    dummyTemp = 1;
                    Statement stmt1 = con.createStatement();
                    String sql1 = "select * from language_list";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    String language = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(5)) {
                            language = rs1.getString(2);
                            break;
                        }
                    }
                    String availability = "Available";
                    if (rs.getInt(9) == 0) {
                        availability = "Not Available";
                    }
                    sql1 = "select * from category_list";
                    rs1 = stmt1.executeQuery(sql1);
                    String category = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(8)) {
                            category = rs1.getString(2);
                            break;
                        }
                    }
                    stmt1.close();
                    System.out.println(rs.getLong(1) + "   " + rs.getString(2) + "  " + rs.getFloat(3) + "  " + rs.getString(4) + "    " + language + "    " + rs.getString(7) + "  " + category + "    " + availability + "   " + rs.getDate(10) + "      " + rs.getDate(11) + "    " + rs.getString(12));
                }
            }
            if (dummyTemp == 0) {
                System.out.println("wrong option is chosen..Please try again");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }

    protected void viewLateFeeIfAny(String userIdInput) {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from userinfo";
            ResultSet success = stmt.executeQuery(sql);
            while (success.next()) {
                if (success.getString(1).equals(userIdInput)) {
                    System.out.println("late fee to be paid : " + success.getFloat(6));
                    break;
                }
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }

    protected void viewIfABookIsAvailableToBorrowOrNOtBasedOnId() {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from book_list";
            ResultSet success;
            System.out.println("Enter the number of times you want to check :");
            int numOfTimesToCheckBookId = sc.nextInt();
            for (int i = 0; i < numOfTimesToCheckBookId; i++) {
                success = stmt.executeQuery(sql);
                System.out.println("Enter the book ID to know its availability :");
                int bookId = sc.nextInt();
                int dummy = 0;
                while (success.next()) {
                    if (success.getInt(1) == bookId) {
                        dummy = 1;
                        String availability = "not available";
                        if (success.getInt(9) == 1) {
                            availability = "available";
                        }
                        System.out.println("Book Id : " + bookId + " is " + availability);
                        break;
                    }
                }
                if (dummy == 0) {
                    System.out.println("BookID entered is not found in the system");
                }
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }

    protected void viewBorrowedBooks(String userInputId) {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from book_list";
            ResultSet success = stmt.executeQuery(sql);
            int dummy = 0;
            while (success.next()) {
                if (success.getString(7) == userInputId) {
                    dummy = 1;
                    if (dummy == 0) {
                        System.out.println("The book(s) borrowed by user are :");
                    }
                    System.out.println(success.getString(2));
                    break;
                }
            }
            if (dummy == 0) {
                System.out.println("User has not borrowed any book yet");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }

    protected void viewParticularBooksBasedOnName() {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            Statement stmt = con.createStatement();
            String sql = "select * from book_list";
            ResultSet success = stmt.executeQuery(sql);
            System.out.println("Enter the book name you want to search :");
            sc.nextLine();
            String userEnteredBookName = sc.nextLine();
            int dummy = 0;
            while (success.next()) {
                if (success.getString(2).equalsIgnoreCase(userEnteredBookName)) {
                    if (dummy == 0) {
                        System.out.println("Books matching the search are :");
                    }
                    dummy = 1;
                    System.out.println(success.getString(2));
                } else {
                    String[] arr = userEnteredBookName.split(" ");
                    String[] input = success.getString(2).split(" ");
                    for (String s : arr) {
                        int temp = 0;
                        for (String s1 : input) {
                            if (s1.equalsIgnoreCase(s)) {
                                if (dummy == 0) {
                                    System.out.println("Books matching the search are :");
                                }
                                dummy = 1;
                                System.out.println(success.getString(2));
                                temp = 1;
                            } else if (s1.toLowerCase().contains(s.toLowerCase())) {
                                if (dummy == 0) {
                                    System.out.println("Books matching the search are :");
                                }
                                dummy = 1;
                                System.out.println(success.getString(2));
                                temp = 1;
                            }
                            if (temp == 1) {
                                break;
                            }
                        }
                        if (temp == 1) {
                            break;
                        }
                    }
                }
            }
            if (dummy == 0) {
                System.out.println("search not found");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected at resetPassword");
            ex.printStackTrace();
        }
    }

    protected void viewBooksBasedOnISBN() {
        try {
            String url = "jdbc:mysql://localhost:3306/Library_Management_system";
            String username = "root";
            String pwd = "password";
            Connection con = DriverManager.getConnection(url, username, pwd);
            System.out.println("Connected Successfully");
            Statement stmt = con.createStatement();
            String sql = "select * from book_list";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Enter the ISBN number to view the book :");
            String userISBN = sc.next();
            int dummy = 0;
            while (rs.next()) {
                if (Objects.equals(rs.getString(12), userISBN)) {
                    dummy = 1;
                    Statement stmt1 = con.createStatement();
                    String sql1 = "select * from language_list";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    String lang = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(5)) {
                            lang = rs1.getString(2);
                            break;
                        }
                    }
                    sql1 = "select * from category_list";
                    rs1 = stmt1.executeQuery(sql1);
                    String category = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(8)) {
                            category = rs1.getString(2);
                            break;
                        }
                    }
                    String availability = "Available";
                    if (rs.getInt(9) == 0) {
                        availability = "Not Available";
                    }
                    sql1 = "select * from publisher_list";
                    rs1 = stmt1.executeQuery(sql1);
                    String publisher = "";
                    while (rs1.next()) {
                        if (rs1.getInt(1) == rs.getInt(6)) {
                            publisher = rs1.getString(2);
                            break;
                        }
                    }
                    stmt1.close();
                    System.out.println(rs.getLong(1) + "   " + rs.getString(2) + "  " + rs.getFloat(3) + "  " + rs.getString(4) + "  " + lang + "    " + publisher + "    " + rs.getString(7) + "   " + category + "  " + availability + "   " + rs.getDate(10) + "      " + rs.getDate(11) + "    " + rs.getString(12));
                }
            }
            if (dummy == 1) {
                System.out.println("ISBN number : " + userISBN + "is not found in the system");
            }
            stmt.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Not Connected");
            ex.printStackTrace();
        }
    }

    protected void viewParticularAuthorBooks() {
        {
            try {
                String url = "jdbc:mysql://localhost:3306/Library_Management_system";
                String username = "root";
                String pwd = "password";
                Connection con = DriverManager.getConnection(url, username, pwd);
                Statement stmt = con.createStatement();
                String sql = "select * from book_list";
                ResultSet success = stmt.executeQuery(sql);
                System.out.println("Enter the author name you want to search :");
                sc.nextLine();
                String userEnteredAuthorName = sc.nextLine();
                int dummy = 0;
                while (success.next()) {
                    if (success.getString(4).equalsIgnoreCase(userEnteredAuthorName)) {
                        if (dummy == 0) {
                            System.out.println(" search results found are :");
                        }
                        dummy = 1;
                        System.out.println(success.getString(2));
                    } else {
                        String[] arr = userEnteredAuthorName.split(" ");
                        String[] input = success.getString(4).split(" ");
                        for (String s : arr) {
                            int temp = 0;
                            for (String s1 : input) {
                                if (s1.equalsIgnoreCase(s)) {
                                    if (dummy == 0) {
                                        System.out.println("search results found are :");
                                    }
                                    dummy = 1;
                                    System.out.println(success.getString(2));
                                    temp = 1;
                                } else if (s1.toLowerCase().contains(s.toLowerCase())) {
                                    if (dummy == 0) {
                                        System.out.println("search results found are :");
                                    }
                                    dummy = 1;
                                    System.out.println(success.getString(2));
                                    temp = 1;
                                }
                                if (temp == 1) {
                                    break;
                                }
                            }
                            if (temp == 1) {
                                break;
                            }
                        }
                    }
                }
                if (dummy == 0) {
                    System.out.println("search not found");
                }
                stmt.close();
                con.close();
            } catch (Exception ex) {
                System.out.println("Not Connected at resetPassword");
                ex.printStackTrace();
            }
        }
    }
}
