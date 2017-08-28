import java.math.BigInteger;
import java.util.Arrays;

import AntShares.SmartContract.Framework.Contract;
import AntShares.SmartContract.Framework.FunctionCode;
import AntShares.SmartContract.Framework.Services.AntShares.Transaction;
import AntShares.SmartContract.Framework.Services.AntShares.TransactionOutput;
import AntShares.SmartContract.Framework.Services.System.ExecutionEngine;

public class AgencyTransaction extends FunctionCode{

  public static boolean Main(byte[] agent, byte[] assetId, byte[] valueId, byte[] client, boolean way, BigInteger price, byte[] signature)
  {
    if (Contract.VerifySignature(client, signature)) return true;
    if (!Contract.VerifySignature(agent, signature)) return false;
    byte[] inputId, outputId;
    if (way)
    {
      inputId = assetId;
      outputId = valueId;
    }
    else
    {
      inputId = valueId;
      outputId = assetId;
    }
    BigInteger inputSum = BigInteger.ZERO;
    BigInteger outputSum = BigInteger.ZERO;
    TransactionOutput[] references = ((Transaction) ExecutionEngine.getScriptContainer()).GetReferences();
    for (TransactionOutput reference : references) {
      if (Arrays.equals(reference.getScriptHash(), ExecutionEngine.getEntryScriptHash()))
      {
        if (!Arrays.equals(reference.getAssetId(), inputId)) {
          return false;
        }
        else {
          inputSum = inputSum.add(BigInteger.valueOf(reference.getValue()));
        }
      }
    }
    TransactionOutput[] outputs = ((Transaction)ExecutionEngine.getScriptContainer()).GetOutputs();
    for(TransactionOutput output : outputs) {
      if (Arrays.equals(output.getScriptHash(), ExecutionEngine.getEntryScriptHash()))
      {
        if (Arrays.equals(output.getAssetId(), inputId)) {
          inputSum = inputSum.subtract(BigInteger.valueOf(output.getValue()));
        }
        else if (Arrays.equals(output.getAssetId(), outputId)) {
          outputSum = outputSum.add(BigInteger.valueOf(output.getValue()));
        }
      }
    }
    if (inputSum.compareTo(BigInteger.ZERO) != 1) return true;
    if (way)
    {
      if (outputSum.multiply(BigInteger.valueOf(100000000)).compareTo(inputSum.multiply(price)) == -1) return false;
    }
    else
    {
      if (inputSum.multiply(BigInteger.valueOf(100000000)).compareTo(outputSum.multiply(price)) ==1) return false;
    }
    return true;
  }
}

