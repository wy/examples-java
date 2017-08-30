import AntShares.SmartContract.Framework.FunctionCode;
import AntShares.SmartContract.Framework.Services.AntShares.Blockchain;
import AntShares.SmartContract.Framework.Services.AntShares.Header;

public class lock extends FunctionCode {
    public static Boolean Main(int timestamp, byte[] pubkey, byte[] signature)
    {
        Header header = Blockchain.GetHeader(Blockchain.GetHeight());
        if (timestamp > header.getTimestamp()) return false;
        return VerifySignature(pubkey, signature);
    }
}