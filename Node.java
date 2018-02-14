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
    HashMap<Block,HashSet<Block>> pendingBlock;
    HashMap<Block,HashSet<Block>> blocks;
    Block lastBlock;
	ArrayList<Transaction> transactions;
	int num;

    Node(int id, boolean fast, double lambda, Block b,int n){
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
        for (int i=0;i<n;i++){
        	balances.put(i,0.0);
        }
        this.num=n;
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
    public void checkPending(Block b){
    	if (!pendingBlock.containsKey(b)){
    		HashSet<Block> child=new HashSet<Block>();
			blocks.put(b,child);
    	}
    	else{
    		HashSet<Block> childs=pendingBlock.get(b);
    		blocks.put(b,chils);
    		for (Block child : )
    	}
    }
    public void receiveBlock(Node n,Block b,Simulator s){
    	Block parent=b.previous_block;
    	if (!blocks.containsKey(b)){
			if (lastBlock.length<b.length || ((lastBlock.length==b.length)&& (lastBlock.timestamp>b.timestamp)){
				// this.balances=new HashMap<int,double>(n.balances);	//deep copy of balances
				// this.lastBlock=n.lastBlock;
				
				if (blocks.containsKey(parent)){
					HashSet<Block> childs=blocks.get(parent);
					childs.add(b);
					checkPending(b);
					// HashSet<Block> child=new HashSet<Block>();
					// blocks.put(b,child);

					// check for pending blocks lastBlock=b;
				}
				else{
					if (!pendingBlock.containsKey(parent)){
						HashSet<Block> child=new HashSet<Block>();
						child.add(b);
						pendingBlock.put(parent,child);
					}
					else{
						pendingBlock.get(parent).add(b);
					}
				}
			}
		}
	}

	public void receiveTransaction(Transaction t,Simulator s){
		boolean check=false;
		for (int i=0;i<transactions.size();i++){
			Transaction t1=transactions.get(i);
			if (t1.tID==t.tID){
				check=true;
				break;
			}
		}
		if (!check){
			Transaction t2=new Transaction(t);
			transactions.add(t2);
			broadcastTransaction(t2);
		}
	}

	public void broadcastTransaction(Transaction t,Simulator s){
		double time=s.curr_time;
		for (int i=0;i<peers.size();i++){
			int id1=peers.get(i);
			double latency=s.latency(this.id,id1,0);
			Task task=new Task(3,curr_time+latency,id1);			//type,time,node
			s.MainQueue.add(task);
		}
	}

	public void generateTransaction(Simulator s,int Tid){
		double time=s.curr_time;
		double c=Math.log(1-Math.random())/(-s.LAMBDA_TRANS);
		int n=this.peers.size();
		int i=this.id;
		while (i==id){
			i=rn.nextInt(n);
		}
		double money=0.0;
		while (money==0.0){
			money=(rn.nextDouble())*balances.get(id);
		}
		Transaction t=new Transaction(Tid,this.id,i,money);
		transactions.add(t);

		Task task=new Task(2,curr_time+c,this.id);		//type of generate next transaction,time,id
		s.MainQueue.add(task);

		broadcastTransaction(t,s);
	}

	public void generateBlock(Simulator s,Block last){

	}
}
