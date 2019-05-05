/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import ui.*;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author kuhail
 */
public class DBManager {

    Connection connection;

    public class Record {

        public String ID;
        public String Name;

        public Record(String ID, String Name) {
            this.ID = ID;
            this.Name = Name;
        }

        public String toString() {
            return Name;
        }
    }

    public LinkedList<Record> getAccountTypes() {
        LinkedList<Record> records = new LinkedList();
        PreparedStatement stmt = null;

        String query = "select * FROM Account_Types";

        try {
            stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String account_type_id = rs.getString("Account_Type_ID");
                String account_type_name = rs.getString("Account_Type_Name");
                Record record = new Record(account_type_id, account_type_name);
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
        this.connection = conn;
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
    //Gets all query results
    public Object[][] getallAccounts() {
        PreparedStatement stmt = null;
        Object[][] result = new Object[][]{};
        String query_all = "select AdvertisementID, AdvTitle,AdvDetails,AdvDateTime,Price,CategoryID,UserID,ModeratorID,StatusID\n"
                + "from advertisements";
        try {
            stmt = connection.prepareStatement(query_all);
            ResultSet rs = stmt.executeQuery();
            int count = getResultSetSize(rs);
            result = getAccountData(count, rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
        return result;
    }

    public Object[][] getAccounts(String UserID) {
        PreparedStatement stmt = null;
        Object[][] result = new Object[][]{};

        String query = "select AdvertisementID, AdvTitle,AdvDetails,AdvDateTime,Price,CategoryID,users.UserID,ModeratorID,StatusID "
                + "from advertisements inner join users on users.UserID = advertisements.UserID where advertisements.UserID=?";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, UserID); //binding the parameter with the given string
            ResultSet rs = stmt.executeQuery();
            int count = getResultSetSize(rs);
            result = getMyAccountData(count, rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
        return result;
    }

    //new getMyAccountData for my_advertisement
    private Object[][] getMyAccountData(int count, ResultSet rs) throws SQLException {
        Object[][] result = new Object[count][9];
        int index = 0;
        do {
            //Include ID Maybe? For delete? Search?
            //Grabs info from SQL QUERY
            int AdvertisementID = rs.getInt("AdvertisementID");
            String AdvTitle = rs.getString("AdvTitle");
            String AdvDetails = rs.getString("AdvDetails");
            String AdvDateTime = rs.getString("AdvDateTime");
            float Price = rs.getFloat("Price");
            String UserID = rs.getString("UserID");
            String ModeratorID = rs.getString("ModeratorID");
            String CategoryID = rs.getString("CategoryID");
            String StatusID = rs.getString("StatusID");

            Account account = new Account(AdvertisementID, AdvTitle, AdvDetails, AdvDateTime, Price, UserID, ModeratorID, CategoryID, StatusID);
            result[index++] = account.mytoArray(); //add mytoArray();
        } while (rs.next());
        return result;
    }

    public boolean checkUser(String UserID) {
        PreparedStatement stmt = null;

        String query = "select * FROM Users WHERE Users.UserID=?";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, UserID); //binding the parameter with the given string
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

    private Object[][] getAccountData(int count, ResultSet rs) throws SQLException {
        Object[][] result = new Object[count][9];
        int index = 0;
        do {
            //Include ID Maybe? For delete? Search?
            //Grabs info from SQL QUERY
            int AdvertisementID = rs.getInt("AdvertisementID");
            String AdvTitle = rs.getString("AdvTitle");
            String AdvDetails = rs.getString("AdvDetails");
            String AdvDateTime = rs.getString("AdvDateTime");
            float Price = rs.getFloat("Price");
            String UserID = rs.getString("UserID");
            String ModeratorID = rs.getString("ModeratorID");
            String CategoryID = rs.getString("CategoryID");
            String StatusID = rs.getString("StatusID");
            if (StatusID.equals("PN") || StatusID.equals("DI")) {
                continue;
            } else {
                Account account = new Account(AdvertisementID, AdvTitle, AdvDetails, AdvDateTime, Price, UserID, ModeratorID, CategoryID, StatusID);
                result[index++] = account.toArray();
                //Check function & if(StatusID.equals("PN")){Code}
            }
        } while (rs.next());
        return result;
    }

    //Delete ADs?
    public boolean deleteAccount(String account_id, String employee_id) {
        PreparedStatement stmt = null;

        String query = "DELETE FROM Accounts  WHERE Account_ID=? AND Employee_ID=?";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, account_id);
            stmt.setString(2, employee_id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Modify to Change PN to AC and ETC
    public boolean changeAccountStatus(String account_id, String employee_id, String status) {
        PreparedStatement stmt = null;

        String query = "UPDATE Accounts SET AcctStatus=? WHERE Account_ID=? AND Employee_ID=?";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, status); //binding the parameter with the given string
            stmt.setString(2, account_id);
            stmt.setString(3, employee_id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addAccount(String AdvTitle, String AdvDetails, float Price, String UserID, String Category) {
        PreparedStatement stmt = null;

        String query = "insert into advertisements(AdvTitle, AdvDetails, AdvDateTime, Price, UserID, ModeratorID, CategoryID,"
                + "StatusID) VALUES (?,?,CURRENT_DATE(),?,?,null,?,'PN')";

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, AdvTitle); //binding the parameter with the given string
            stmt.setString(2, AdvDetails);
            stmt.setFloat(3, Price);
            stmt.setString(4, UserID);
            stmt.setString(5, Category);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Object[][] filter(String Period, String Category) {
        PreparedStatement stmt = null;
        Object[][] result = new Object[][]{};
        if (Period == "%%") {
            String query = "select a.AdvertisementID,a.AdvTitle,a.AdvDetails,a.AdvDateTime,a.Price,a.CategoryID,UserID,a.ModeratorID,a.StatusID\n"
                    + "from advertisements a where a.CategoryID like ?";
            try {
                stmt = connection.prepareStatement(query);
                stmt.setString(1, Category);
                ResultSet rs = stmt.executeQuery();
                int count = getResultSetSize(rs);
                result = getAccountData(count, rs);
                return result;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return result;
            }
        } else {
            String query = "select a.AdvertisementID,a.AdvTitle,a.AdvDetails,a.AdvDateTime,a.Price,a.CategoryID,UserID,a.ModeratorID,a.StatusID\n"
                    + "from advertisements a where a.AdvDateTime >= DATE(NOW()-INTERVAL ? MONTH ) and CategoryID like ?";
            try {
                stmt = connection.prepareStatement(query);
                stmt.setString(1, Period);
                stmt.setString(2, Category);
                ResultSet rs = stmt.executeQuery();
                int count = getResultSetSize(rs);
                result = getAccountData(count, rs);
                return result;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return result;
            }
        }
    }

    public Object[][] title_description_search(String full_text) {
        PreparedStatement stmt = null;
        Object[][] result = new Object[][]{};
        if (full_text.contains(",")) {
            List<String> searchList = Arrays.asList(full_text.split(","));
            String search_0 = searchList.get(0) + "%";
            String search_1 = searchList.get(1) + "%";
            String query = "select a.AdvertisementID,a.AdvTitle,a.AdvDetails,a.AdvDateTime,a.Price,a.CategoryID,UserID,a.ModeratorID,a.StatusID\n"
                    + "from advertisements a where a.AdvTitle like ? and a.AdvDetails like ?";
            try {
                stmt = connection.prepareStatement(query);
                stmt.setString(1, search_0);
                stmt.setString(2, search_1);
                ResultSet rs = stmt.executeQuery();
                int count = getResultSetSize(rs);
                result = getAccountData(count, rs);
                return result;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return result;
            }
        } else {
            String search = full_text + "%";
            String query = "select a.AdvertisementID,a.AdvTitle,a.AdvDetails,a.AdvDateTime,a.Price,a.CategoryID,UserID,a.ModeratorID,a.StatusID\n"
                    + "from advertisements a where a.AdvTitle like ? or a.AdvDetails like ?";
            try {
                stmt = connection.prepareStatement(query);
                stmt.setString(1, search);
                stmt.setString(2, search);
                ResultSet rs = stmt.executeQuery();
                int count = getResultSetSize(rs);
                result = getAccountData(count, rs);
                return result;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return result;
            }
        }
    }

    public boolean my_update(int ID, String Title, String Description, float Price, String Status, String Date) {
        PreparedStatement stmt = null;
        Object[][] result = new Object[][]{};
        String query = "UPDATE advertisements SET AdvTitle= ?,AdvDetails = ?, Price = ?,StatusID = ?, AdvDateTime = ? WHERE AdvertisementID = ?";
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, Title);
            stmt.setString(2, Description);
            stmt.setFloat(3, Price);
            stmt.setString(4, Status);
            stmt.setString(5, Date);
            stmt.setInt(6, ID);
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
