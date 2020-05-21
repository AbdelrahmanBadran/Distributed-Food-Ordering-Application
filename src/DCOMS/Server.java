package DCOMS;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements LoginInterface{
    public Server()throws RemoteException{        
        super();
    }
    
    @Override
    public String Login(String username, String password) throws RemoteException{                
        
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");            
            Statement stmt = conn.createStatement();)
        {                                    
            String sqlValidateLogin = "SELECT USERTYPE, CASE WHEN PASSWORD = '" + password + "' then 1 else 0 End as RESULT from USERS WHERE USERNAME = '" + username + "'";            
            ResultSet rset = stmt.executeQuery(sqlValidateLogin);
            
            while(rset.next()){
                int isAthorizedUser = rset.getInt("RESULT");   
                String userType = rset.getString("USERTYPE");
                
                if(isAthorizedUser == 1){ return userType; }
            }                                    
        } catch (SQLException ex) {ex.printStackTrace();}                   
        return "F";        
    }   
    
    public boolean Register(String firstname, String lastname, String username, String password, String passport, String phone, String userType)throws RemoteException{                   
                      
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");            
            Statement stmt = conn.createStatement();)
        {                               
            String isUsernameFree = "SELECT CASE WHEN COUNT(*) > 0 then 1 else 0 End as RESULT from USERS WHERE USERNAME = '" + username + "'";        
            ResultSet rset = stmt.executeQuery(isUsernameFree);
            
            while(rset.next()){
                int isUsernameTaken = rset.getInt("RESULT");          
                if(isUsernameTaken == 1){ return false; }
            }             
                        
            stmt.executeUpdate("Insert into Users values('" + firstname +"', '" + lastname+ "' , '" + username+ "', '" + password + "', '" + passport+ "', '" + phone + "', '" + userType + "')");                                          
        } catch (SQLException ex) {ex.printStackTrace();}                
        return true;          
    }  
   
    
    //THIS METHOD TAKES ITEMS IN THE DATABASE AND PLACES THEM IN A LIST
    public List<menu> getMenu() throws RemoteException{ 
        List<menu> list = new ArrayList<menu>();
        
        try{Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");
                
        String sql = "select * from MENU";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            int itemID =rs.getInt("ITEM_NO");

            String name = rs.getString("NAME");
            String cat = rs.getString("CATEGORY");
            String des = rs.getString("DESCRIPTION");

            Double price = rs.getDouble("PRICE");

            //SETVALUES
            menu Menu = new menu();
            Menu.setCat(cat);
            Menu.setDes(des);
            Menu.setname(name);
            Menu.setitemNumber(itemID);
            Menu.setprice(price);
            list.add(Menu);
        }
        rs.close();                            
        }catch (SQLException ex) {ex.printStackTrace();}         
        return list;
    }
    
    // this method inserts an order into order table      
    @Override
    public void insertOrders(int orderid, String itemid, String itemname, String customerid, int quantity, double total)throws RemoteException{  
                      
        try      
        // Statement stmt = conn.createStatement();)                
        {Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");
        PreparedStatement st = con.prepareStatement("insert into Ordr(ORDERID,ITEMID,ITEMNAME,CUSTOMERID,QUANTITY,TOTAL)values(?,?,?,?,?,?)");
        con.setAutoCommit(false);
        
        st.setInt(1,orderid);
        st.setString(2,itemid);
        st.setString(3,itemname);
        st.setString(4,customerid);
        st.setInt(5,quantity);
        st.setDouble(6,total);
        st.executeUpdate();
        
        con.commit();
        // stmt.executeUpdate("Insert into Ordr values(" + orderid +", '" + itemid+ "' , '" + itemname+ "', '" + customerid+ "', " + quantity+ ", " + total + ")");                         
            
        } catch (SQLException ex) {ex.printStackTrace();}    
    }   

    public ArrayList<Order> getOrderList(String condition) throws RemoteException{
        ArrayList<Order> orderList = new ArrayList<>();

        try{Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");
            
            String sql = "SELECT * FROM ORDR WHERE " + condition;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Order order;
            
            while (rs.next()){
                order = new Order(rs.getInt("ORDERID"),rs.getString("ITEMID"),rs.getString("ITEMNAME"),rs.getString("CUSTOMERID"),rs.getInt("QUANTITY"),rs.getDouble("Total"),rs.getInt("STATUS"));
                orderList.add(order);
            }
            rs.close();
            
        }catch (SQLException ex) {ex.printStackTrace();} 
        
        return orderList;
    }
    
    public int getItemQuantity(int itemID )throws RemoteException{
        int quantity = 0;
        
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");            
            Statement stmt = conn.createStatement();)
        {                                          
            String quantityIter = "SELECT QUANTITY FROM MENU WHERE ITEM_NO = '" + itemID + "'";
            ResultSet rset = stmt.executeQuery(quantityIter);

            while(rset.next()){               
                quantity = rset.getInt("QUANTITY");    
                System.out.println("Quantity: " + quantity);
                return quantity;
            }
            
        } catch (SQLException ex) {}       
        
        return quantity;        
    }
    
    
    public void deleteOrderMade(String value) throws RemoteException{        
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");
            con.setAutoCommit(false);
            
            PreparedStatement st = con.prepareStatement("DELETE FROM ORDR WHERE ORDERID = " +value);            
            st.executeUpdate();
            
            con.commit();                        
        } catch (Exception e) {e.printStackTrace();}        
    }
    
        
    public void approveOrder(String value) throws RemoteException{
        
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");
            
            con.setAutoCommit(false);
            
            PreparedStatement st = con.prepareStatement(" UPDATE ORDR SET STATUS = 1 WHERE ORDERID=" + value);
            st.executeUpdate();                 
            
            con.commit();            
        } catch (Exception e) {e.printStackTrace();}        
    }
     
    
    public void rejectOrder(String value)throws RemoteException {
        
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");
            con.setAutoCommit(false);
            
            PreparedStatement st = con.prepareStatement(" UPDATE ORDR SET STATUS = 0 WHERE ORDERID=" + value);
            st.executeUpdate();    
            
            con.commit();            
        } catch (Exception e) {e.printStackTrace();}        
    }
              
    public String hashInput(String input)throws RemoteException{
           String hashedPassword = "";
            try {                
                MessageDigest md = MessageDigest.getInstance("MD5");                
                md.update(input.getBytes());
                
                byte[] bytes = md.digest();
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }                       
                hashedPassword = sb.toString();

            } catch (NoSuchAlgorithmException e) {}
            
        return hashedPassword;
    }
}