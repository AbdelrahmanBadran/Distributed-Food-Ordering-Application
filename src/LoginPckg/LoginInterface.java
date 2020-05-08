package LoginPckg;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginInterface extends Remote{        
    public String Login(String username, String password) throws RemoteException;
    public boolean Register(String firstname, String lastname, String username, String password, String passport, String phone, String userType) throws RemoteException;
}