//import java.sql.*;
//import java.util.Scanner;
//
//public class DB_Creation {
//    public static void main(String[] args) {
//        try
//        {
//            Scanner sc = new Scanner(System.in);
//            String url="jdbc:mysql://localhost:3306/Library_Management_system";
//            String username="root";
//            String pwd="password";
//            Connection con= DriverManager.getConnection(url,username,pwd);
//            System.out.println("Connected Successfully");
//            Statement stmt=con.createStatement();
//            String sql = "show tables";
//            ResultSet rs = stmt.executeQuery(sql);
//            String s = sc.next();
//            while(rs.next())
//            {
//                System.out.println(rs.getString(1));
//                if(rs.getString(1).equals(s))
//                {
//                    System.out.println("match found");
//                }
//            }
//
////            String sql="create table AdminInfo(AdminID varchar(100),AdminPassword varchar(100) not null,AdminName varchar(50) not null,AdminMobileNumber int not null,SuperAdmin bool not null,primary key (AdminID))";
////            stmt.executeUpdate(sql);
////            String sql="create table Book_list(BookID bigint,BookName varchar(10),BookEdition float,BookAuthorName varchar(10),BookLanguageID varchar(50) not null,PublisherID bigint not null,BookBorrowedByUserID varchar(50),BookCategoryID Varchar(50) not null,BookAvailability bool not null,BookIssuedDate date ,BookDueDate date,primary key (BookID))";
////            stmt.executeUpdate(sql);
////            String sql="create table Publisher_list(PublisherId varchar(100),PublisherName varchar(100),primary key (PublisherID))";
////            stmt.executeUpdate(sql);
////            String sql="create table Category_list(CategoryId varchar(100),CategoryName varchar(100),primary key (CategoryID))";
////            stmt.executeUpdate(sql);
////            String sql="create table UserInfo(UserID varchar(100) not null ,UserPassword varchar(100) not null,UserName varchar(50) not null,UserMobileNumber bigint not null,UserBookBorrowedCount int not null,UserLateFee float not null,primary key (UserID))";
////            stmt.executeUpdate(sql);
//            System.out.println("Table Created");
//            stmt.close();
//            con.close();
//        }
//        catch(Exception ex)
//        {
//            System.out.println("Not Connected");
//            ex.printStackTrace();
//        }
//    }
//}