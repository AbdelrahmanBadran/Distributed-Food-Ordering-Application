package LoginPckg;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Registry1 {
        public static void main(String[] args)throws RemoteException {
        
        //Use same port number as client
        Registry reg = LocateRegistry.createRegistry(1044);
        
        reg.rebind("sub",new Server());        
    }
}