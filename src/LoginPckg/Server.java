package LoginPckg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public boolean Register(String firstname, String lastname, String username, String password, String passport, String phone, String userType){                   
                      
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");            
            Statement stmt = conn.createStatement();)
        {                               
            String isUsernameFree = "SELECT CASE WHEN COUNT(*) > 0 then 1 else 0 End as RESULT from USERS WHERE USERNAME = '" + username + "'";        
            ResultSet rset = stmt.executeQuery(isUsernameFree);
            
            while(rset.next()){
                int isUsernameTaken = rset.getInt("RESULT");          
                if(isUsernameTaken == 1){ return false; }
            }        
            
            stmt.executeUpdate("Insert into Users values('" + firstname +"', '" + lastname+ "' , '" + username+ "', '" + password+ "', '" + passport+ "', '" + phone + "', '" + userType + "')");                              
            
        } catch (SQLException ex) {ex.printStackTrace();}                
        return true;          
    }           
}