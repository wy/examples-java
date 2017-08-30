import AntShares.SmartContract.Framework.Services.AntShares.Storage;

public class HelloWorld {

  public static void Main(String[] args){
    Storage.Put(Storage.getCurrentContext(), "Greeting to the World", "Hello World!");
  }

}

