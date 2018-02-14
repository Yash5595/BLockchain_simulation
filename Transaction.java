public class Transaction{
  int tID;
  int fromID;
  int toID;
  double amount;
  boolean spent;
  //how to generate transaction ID?
  Transaction(int tID, int fromID, int toID, double amount){
    this.tID = tID;
    this.fromID = fromID;
    this.toID = toID;
    this.amount = amount;
    this.spent=false;
  }

  Transaction(Transaction tr){
    tID = tr.tID;
    fromID = tr.fromID;
    toID = tr.toID;
    amount = tr.amount;
    this.spent=false;
  }
}