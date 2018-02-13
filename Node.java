import java.util.*;

public class Node{
    int id;
    ArrayList<Double> receivedStamps;
    Random rn = new Random();
    ArrayList<Node> peers;     //pointers to Nodes are stored here
    boolean fast;
    double lambda;
    boolean type;           // True => Fast CPU
    HashMap<int,double> balances;
    float coins ;
    Block lastBlock;
	ArrayList<Transaction> transactions;
    Node(int id, boolean fast, double lambda, Block b){
        this.id = id;
        this.fast = fast;
        this.lambda = lambda;
        this.balances=new HashMap<int,double>();
        if(lambda > 0.5 ){                                                              //////// check for lambda value
            this.type = true;
        }
        else{
            this.type = false;
        }
        receivedStamps = new ArrayList<Double>();
        peers = new ArrayList<Node>();
        lastBlock=b;
    	ArrayList<Transaction> transactions=new ArrayList<Transaction>();
    }

    public void setPeers(int n, ArrayList<Node> all){
        Collections.shuffle(all);
        int numPeers = rn.nextInt(n-1) + 1;

        for(int i=0; i<numPeers; i++){
            if(id != all.get(i).id)
                peers.add(all.get(i));
        }
    }

    public void receiveBlock(Node n,Block b){
		if (lastBlock.length<b.length || ((lastBlock.length==b.length)&& (lastBlock.timestamp>b.timestamp)){
			this.balances=new HashMap<int,double>(n.balances);	//deep copy of balances
			this.lastBlock=n.lastBlock;
		}
	}
}
