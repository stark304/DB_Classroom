/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

/**
 *
 * @author kuhail
 */
public class DBManager {
    Connection connection;
    
        public class Record{
        public String ID;
        public String Name;
        
        public Record(String ID,String Name){
            this.ID=ID;
            this.Name=Name;
        }
        
        public String toString(){
            return Name;
        }
    }
        
 public LinkedList<Record> getAccountTypes(){
     LinkedList<Record> records=new LinkedList();
         PreparedStatement stmt = null;
        
        String query = "select * FROM Account_Types";

        try {
            stmt=connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
            String account_type_id = rs.getString("Account_Type_ID");
            String account_type_name = rs.getString("Account_Type_Name");
            Record record=new Record(account_type_id,account_type_name);
            records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return records;
        }
        return records;
 }
 
    public void connect(String userName, String password, String serverName, String portNumber, String dbName) throws SQLException, InstantiationException, IllegalAccessException {
        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        conn = DriverManager.getConnection(
                "jdbc:mysql://"
                + serverName
                + ":" + portNumber + "/" + dbName,
                connectionProps);

        System.out.println("Connected to database");
        this.connection=conn;
    }
    
   /* public Object[][] getAccounts(String UserID,String text) {
        PreparedStatement stmt = null;
        Object[][] result=new Object[][]{};
        //Gets the information
        String query = "select AdvTitle,AdvDetails,AdvDateTime,Price,CategoryID,users.UserID,ModeratorID,StatusID " +
                "from advertisements inner join users on users.UserID = advertisements.UserID where advertisements.UserID=?";

        try {
            stmt=connection.prepareStatement(query);
            stmt.setString(1,UserID); //binding the parameter with the given string
            stmt.setString(2,"%"+text+"%");
            stmt.setString(3,"%"+text+"%");
            ResultSet rs = stmt.executeQuery();
            int count = getResultSetSize(rs);
            result=getAccountData(count,rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
        return result;
    }   */
    
   public Object[][] getAccounts(String UserID) {
        PreparedStatement stmt = null;
        Object[][] result=new Object[][]{};
        
        String query = "select AdvTitle,AdvDetails,AdvDateTime,Price,CategoryID,users.UserID,ModeratorID,StatusID " +
                "from advertisements inner join users on users.UserID = advertisements.UserID where advertisements.UserID=?";

        try {
            stmt=connection.prepareStatement(query);
            stmt.setString(1,UserID); //binding the parameter with the given string
            ResultSet rs = stmt.executeQuery();
            int count = getResultSetSize(rs);
            result=getAccountData(count,rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
       return result;
    }
   
       private Object[][] getAccountData(int count,ResultSet rs) throws SQLException {
        Object[][] result=new Object[count][8];
        int index=0;
       do {
            //Include ID Maybe? For delete? Search?
           //Grabs info from SQL QUERY
            String AdvTitle = rs.getString("AdvTitle");
            String AdvDetails = rs.getString("AdvDetails");
            String AdvDateTime = rs.getString("AdvDateTime");
            float Price = rs.getFloat("Price");
            String UserID = rs.getString("UserID");
            String ModeratorID = rs.getString("ModeratorID");
            String CategoryID = rs.getString("CategoryID");
            String StatusID = rs.getString("StatusID");

            Account account=new Account(AdvTitle,AdvDetails,AdvDateTime,Price,UserID,ModeratorID,CategoryID,StatusID);
            result[index++]=account.toArray(); //Check function & if(StatusID.equals("PN")){Code}
        }
       while(rs.next());
        return result;
    } 
    
  public boolean checkUser(String UserID) {
        PreparedStatement stmt = null;
        
        String query = "select * FROM Users WHERE Users.UserID=?";

        try {
            stmt=connection.prepareStatement(query);
            stmt.setString(1,UserID); //binding the parameter with the given string
            ResultSet rs = stmt.executeQuery();
            int count = getResultSetSize(rs);
            if (count == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
  //Delete ADs?
     public boolean deleteAccount(String account_id, String employee_id) {
        PreparedStatement stmt = null;
        
        String query = "DELETE FROM Accounts  WHERE Account_ID=? AND Employee_ID=?";

        try {
            stmt=connection.prepareStatement(query);
            stmt.setString(1,account_id);
            stmt.setString(2,employee_id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    } 
  //Modify to Change PN to AC and ETC
   public boolean changeAccountStatus(String account_id, String employee_id,String status) {
        PreparedStatement stmt = null;
        
        String query = "UPDATE Accounts SET AcctStatus=? WHERE Account_ID=? AND Employee_ID=?";

        try {
            stmt=connection.prepareStatement(query);
            stmt.setString(1,status); //binding the parameter with the given string
            stmt.setString(2,account_id);
            stmt.setString(3,employee_id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
   //Modify to add Ads?
     public boolean addAccount(String customer_id,String status,String branch_id,String employee_id,String account_type_id) {
        PreparedStatement stmt = null;
        
        String query = "insert into Accounts (AcctOpen_Date,Customer_ID,AcctStatus,Branch_ID,Employee_ID,Account_Type_ID) VALUES (CURRENT_DATE(),?,?,?,?,?)";

        try {
            stmt=connection.prepareStatement(query);
            stmt.setString(1,customer_id); //binding the parameter with the given string
            stmt.setString(2,status);
            stmt.setString(3,branch_id);
            stmt.setString(4,employee_id);
            stmt.setString(5,account_type_id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
  
  private int getResultSetSize(ResultSet rs) {
        int count = 0;
        try {
            while (rs.next()) {
                count++;
            }
            rs.first();
        } catch (SQLException e) {

        }
        return count;
    }
}
