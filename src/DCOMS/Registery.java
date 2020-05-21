package DCOMS;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Registery {        
    public static void main(String[] args)throws RemoteException {                
        Registry reg = LocateRegistry.createRegistry(1044);        
        reg.rebind("sub", new Server());        
    }
}