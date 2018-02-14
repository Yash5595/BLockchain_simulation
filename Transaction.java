public class Transaction{
  int tID;
  int fromID;
  int toID;
  double amount;
  boolean spent;
  double time;
  //how to generate transaction ID?
  Transaction(int tID, int fromID, int toID, double amount,double t){
    this.tID = tID;
    this.fromID = fromID;
    this.toID = toID;
    this.amount = amount;
    this.spent=false;
    this.time=t;
  }

  Transaction(Transaction tr){
    tID = tr.tID;
    fromID = tr.fromID;
    toID = tr.toID;
    amount = tr.amount;
    this.spent=false;
    this.time=tr.time;
  }
  public int hashCode(){
  	return this.tID;
  }

  public boolean equals(Transaction t){
  	return (this.tID==t.tID);
  }
}