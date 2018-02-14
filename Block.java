import java.util.*;

public class Block{
    int id;
    double timestamp;
    Block previous_block;
    int creator_ID;
    int length;
    HashSet<Transaction> transactions;


    Block(int block_ID, double ts, Block prev_block, int creator_ID, int length){
        this.id = block_ID;
        timestamp = ts;
        previous_block = prev_block;
        this.creator_ID = creator_ID;
        this.length = length;
        this.transactions = new HashSet<Transaction>();
}
	Block(block b){
		this.id= b.id;
		this.timestamp = b.timestamp;
        this.previous_block = b.previous_block;
        this.creator_ID = b.creator_ID;
        this.length = b.length;
        this.transactions=new HashSet<Transaction>(b.transactions);
	}
}