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
                
        try(Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Users" , "DCOMS", "DCOMS1973");
            Statement stmt = conn.createStatement();)
        {            
            String sqlValidateLogin = "SELECT CASE PASSWORD WHEN " + password + " then 1 else 0 End as RESULT from DCOMS.USERS where USERNAME = " + username;
            System.out.println(sqlValidateLogin);
            
            ResultSet rset = stmt.executeQuery(sqlValidateLogin);
            System.out.println(rset);            
            
            while(rset.next()){
                int isAthorizedUser = rset.getInt("RESULT");
            
                System.out.println(isAthorizedUser);

                if(isAthorizedUser == 1){
                    isLoginSuccessful = true;
                }
            }            
                        
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }           
        
        return isLoginSuccessful;        
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