package LoginPckg;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends UnicastRemoteObject implements LoginInterface{
    public Server()throws RemoteException{
        //To call the function in this super class
        super();
    }
    
    @Override
    public boolean Login(String username, String password) throws RemoteException{                
        boolean isLoginSuccessful = false;               
                
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");            
            Statement stmt = conn.createStatement();)
        {            
            String sqlValidateLogin = "SELECT CASE WHEN PASSWORD = '" + password + "' then 1 else 0 End as RESULT from USERS WHERE USERNAME = '" + username + "'";            
            ResultSet rset = stmt.executeQuery(sqlValidateLogin);
            
            while(rset.next()){
                int isAthorizedUser = rset.getInt("RESULT");          

                if(isAthorizedUser == 1){
                    isLoginSuccessful = true;
                }
            }            
                        
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }           
        
        return isLoginSuccessful;        
    }   


    public boolean Register(String firstname, String lastname, String passport, String username, String password){
        boolean isSignupSuccessful = true;               
        char usertype = '3';
        
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Users", "Users", "123");            
            Statement stmt = conn.createStatement();)
        {            
            stmt.executeUpdate("Insert into Users values('" + firstname +"', '" + lastname+ "' , '" + passport+ "', '" + username+ "', '" + password+ "', '" + usertype + "')");                              
                        
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }           
        
        return isSignupSuccessful;                    
    }           
}   

//   @Override
//    public boolean Login(String username, char[] password) throws RemoteException{
//        boolean isLoginSuccessful = false;
//        
//        try{
//            if(username.equals("Badran") && isPasswordCorrect(password)){
//                isLoginSuccessful = true;                
//            }
//        }
//        catch(HeadlessException e){
//            System.out.println(e.getMessage());
//        }    
//        return isLoginSuccessful;        
//    }
//    
//    private static boolean isPasswordCorrect(char[] input) throws RemoteException{
//        boolean isCorrect = true;
//        char[] correctPassword = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
//
//        if(input.length != correctPassword.length){
//            isCorrect = false;
//        }
//        else{
//            isCorrect = Arrays.equals(input, correctPassword);
//        }
//        Arrays.fill(correctPassword, '0');
//
//        return isCorrect;  
//    }