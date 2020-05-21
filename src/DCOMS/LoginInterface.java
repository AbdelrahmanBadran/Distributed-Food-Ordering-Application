package DCOMS;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface LoginInterface extends Remote {            
    public boolean Register(String firstname, String lastname, String username, String password, String passport, String phone, String userType) throws RemoteException;
    public String Login(String username, String password) throws RemoteException;
    public String hashInput(String input)throws RemoteException;
        
    public List<menu> getMenu() throws RemoteException;
    public int getItemQuantity(int itemID )throws RemoteException;
    public void insertOrders(int orderid, String itemid, String itemname, String customerid, int quantity, double total) throws RemoteException;   
    public ArrayList<Order> getOrderList(String condition) throws RemoteException;
    
    public void deleteOrderMade(String value) throws RemoteException;
    public void approveOrder(String value) throws RemoteException;
    public void rejectOrder(String value) throws RemoteException;
}
