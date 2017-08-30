import AntShares.SmartContract.Framework.*;
import AntShares.SmartContract.Framework.Services.AntShares.*;


public class Domain extends FunctionCode {

    public static Object Main(String operation, Object... args){

        if (operation == "query") return Query((String) args[0]);
        if (operation == "register") return Register((String)args[0], (byte[])args[1]);
        if (operation == "transfer") return Transfer((String) args[0], (byte[])args[1]);
        if (operation == "delete") return Delete((String) args[0]);
        return false;

    }

    private static byte[] Query(String domain){
        return Storage.Get(Storage.getCurrentContext(),domain);
    }

    private static boolean Register(String domain, byte[] owner) {
        // if(!Runtime.CheckWitness(owner)) return false;
        byte[] value = Storage.Get(Storage.getCurrentContext(), domain);
        if (value!= null) return false;
        Storage.Put(Storage.getCurrentContext(),domain,owner);
        return true;
    }

    private static boolean Transfer(String domain, byte[] to)
    {
        //if (!Runtime.CheckWitness(to)) return false;
        byte[] from = Storage.Get(Storage.getCurrentContext(), domain);
        if (from == null) return false;
        //if (!Runtime.CheckWitness(from)) return false;
        Storage.Put(Storage.getCurrentContext(), domain, to);
        return true;
    }

    private static boolean Delete(String domain)
    {
        byte[] owner = Storage.Get(Storage.getCurrentContext(), domain);
        if (owner == null) return false;
        //if (!Runtime.CheckWitness(owner)) return false;
        Storage.Delete(Storage.getCurrentContext(), domain);
        return true;
    }
}




