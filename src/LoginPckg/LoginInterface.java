package LoginPckg;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginInterface extends Remote{        
    public boolean Login(String username, String password) throws RemoteException;
    public boolean Register(String firstname, String lastname, String passport, String username, String password) throws RemoteException;
}