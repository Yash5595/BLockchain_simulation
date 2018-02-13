import java.util.*;

public class Block{
    int block_ID;
    double timestamp;
    Block previous_block;
    int creator_ID;
    int length;
    HashSet<Transaction> transactions;


    Block(int block_ID, double ts, Block prev_block, int creator_ID, int length){
        this.block_ID = block_ID;
        timestamp = ts;
        previous_block = prev_block;
        this.creator_ID = creator_ID;
        this.length = length;
        this.transactions = new HashSet<Transaction>();
}