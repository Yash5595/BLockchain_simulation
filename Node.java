import java.util.*;

public class Node{
    int id;
    ArrayList<Double> receivedStamps;				//to store transactions in file ?
    Random rn = new Random();
    ArrayList<Node> peers;     //pointers to Nodes are stored here
    boolean fast;
    double lambda;
    HashMap<Integer,Double> balances;
    HashMap<Block,HashSet<Block>> pendingBlock;
    HashMap<Block,HashSet<Block>> blocks;
    Block lastBlock;
	HashSet<Transaction> transactions;
	HashMap<Integer,Transaction> mapping;
	int num;
	HashMap<Block,Boolean> inChain;
    Node(int id, boolean fast, double lambda, Block b,int n){
        this.id = id;
        this.fast = fast;
        this.lambda = lambda;
        this.balances=new HashMap<Integer,Double>();
        receivedStamps = new ArrayList<Double>();
        peers = new ArrayList<Node>();
        lastBlock=b;
        for (int i=0;i<n;i++){
        	balances.put(i,0.0);
        }
        this.num=n;
        inChain=new HashMap<Block,Boolean>();
    	transactions=new HashSet<Transaction>();
    	mapping=new HashMap<Integer,Transaction>();
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
    	for (Block c: pendingBlock.keySet()){
    		if ((c.previous_block).equals(b)){
    			blocks.get(b).add(c);
    			HashSet<Block> child=new HashSet<Block>();
    			blocks.put(c,child);
    			pendingBlock.remove(c);
    			findLongest(c);
    			checkPending(c);
    		}
    	}
    }
    public void changeChain(Block b){
    	Block b1=lastBlock;
    	while (b1.id!=0){
    		inChain.put(b1,false);
    		b1=b1.previous_block;
    	}
    	b1=b;
    	while (b1.id!=0){
    		inChain.put(b1,true);
    		b1=b1.previous_block;
    	}
    	b1=lastBlock;
    	while (inChain.get(b1)==false){
    		undo(b1);
    		b1=b1.previous_block;
    	}
    	Block b2=b;
    	while (b2.id!=b1.id){
    		exec(b2);
    		b2=b2.previous_block;
    	}
    	lastBlock=b;
    }
    public void exec(Block b){
    	HashSet<Transaction> ts=b.transactions;
    	for( Transaction t : ts){
    		Transaction t1=mapping.get(t.tID);
    		if (t1.spent==false){
	    		t1.spent=true;
	    		balances.put(t1.fromID,balances.get(t1.fromID)-t1.amount);
	    		balances.put(t1.toID,balances.get(t1.toID)+t1.amount);
    		}
    	}
    }
    public void undo(Block b){
    	HashSet<Transaction> ts=b.transactions;
    	for( Transaction t : ts){
    		Transaction t1=mapping.get(t.tID);
    		if (t1.spent==true){
	    		t1.spent=false;
	    		balances.put(t1.fromID,balances.get(t1.fromID)+t1.amount);
	    		balances.put(t1.toID,balances.get(t1.toID)-t1.amount);
    		}
    	}
    }

    public void findLongest(Block b){
    	inChain.put(b,false);
    	if ((b.previous_block).equals(lastBlock)){
    		lastBlock =b;
    		inChain.put(b,true);
    		exec(b);		//execute transactions in this block
    	}
    	else{
    		if ((b.length>lastBlock.length)|| ((b.length==lastBlock.length) && (b.timestamp<lastBlock.timestamp))){
    			changeChain(b);
    		}
    	}
    }
    public void receiveBlock(Block b,Simulator s){		//generate new block
    	Block parent=b.previous_block;

		if (blocks.containsKey(parent)){
			HashSet<Block> childs=blocks.get(parent);
			if (!childs.contains(b)){
				childs.add(b);
				HashSet<Block> child=new HashSet<Block>();
				blocks.put(b,child);
				Block b1=lastBlock;
				findLongest(b);
				// check if its child is in pending block
				checkPending(b);

				if (!b1.equals(lastBlock)){
					System.out.println("received block "+ b.id +" changed last block from "+ b1.id +"to"+ lastBlock.id);
					int id1=s.getNextBlockId();
					generate(s,id1);
				}
				else{
					System.out.println("received block "+ b.id +" but still last block "+ lastBlock.id +" is same");
				}
			}
		}
		else{
			if (pendingBlock.containsKey(parent)){
				if (!pendingBlock.containsKey(b)){
					HashSet<Block> child=new HashSet<Block>();
					pendingBlock.put(b,child);
					pendingBlock.get(parent).add(b);
				}
			}
			if (!pendingBlock.containsKey(b)){
				HashSet<Block> child=new HashSet<Block>();
				pendingBlock.put(b,child);
			}
		}
	}

	public void receiveTransaction(Transaction t,Simulator s){
		boolean check=false;
		for (Transaction t1: transactions){
			if (t1.tID==t.tID){
				check=true;
				break;
			}
		}
		if (!check){
			Transaction t2=new Transaction(t);
			transactions.add(t2);
			mapping.put(t2.tID,t2);
			broadcastTransaction(t2,s);
		}
		receivedStamps.add(s.curr_time);
	}

	public void broadcastTransaction(Transaction t,Simulator s){
		double time=s.curr_time;
		System.out.println("broadcasting transaction "+ t.tID +" from "+ this.id);
		for (int i=0;i<peers.size();i++){
			Node id1=peers.get(i);
			double latency=s.latency(this.id,id1.id,0);
			Task task=new Task(Task.RECEIVE_TRANS,time+latency,id1,s,0,null,t,null);			//type,time,node
			s.MainQueue.add(task);
		}
	}
																							///////transactions ko mapping se replace krdio lodu
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
		Transaction t=new Transaction(Tid,this.id,i,money,time);
		transactions.add(t);
		mapping.put(t.tID,t);
		Task task=new Task(Task.RECEIVE_TRANS,time+c,this,s,s.getNextTransId(),null,null,null);		//type of generate next transaction,time,id
		s.MainQueue.add(task);
		broadcastTransaction(t,s);
		System.out.println("generated Transaction "+ Tid +" from "+ this.id +" to"+ i);
	}

	public void generateBlock(Simulator s,int id1,Block b){
		if (lastBlock.id==b.id){
			generate(s,id1);
		}
	}

	public void generate(Simulator s,int id1){

		double time=s.curr_time;
		double c=Math.log(1-Math.random())/(-s.LAMBDA_TK);
		Block block=new Block(id1,time,lastBlock,this.id,lastBlock.length+1);
		HashSet<Transaction> trans=new HashSet<Transaction>();
		for (Transaction t:transactions){		
			if (balances.get(t.fromID)>=t.amount && t.spent==false){
				trans.add(t);
				balances.put(t.fromID,balances.get(t.fromID)-t.amount);
				balances.put(t.toID,balances.get(t.toID)+t.amount);
				t.spent=true;
			}
		}
		block.transactions=trans;
		lastBlock=block;
		inChain.put(lastBlock,true);
		Task task=new Task(Task.GENERATE_BLOCK,time+c,this,s,s.getNextBlockId(),lastBlock,null,null);
		s.MainQueue.add(task);
		System.out.println("generated Block "+ id1 +" by "+ this.id );
		broadcastBlock(s,block);
	}

	public void broadcastBlock(Simulator s,Block b){
		double time=s.curr_time;
		System.out.println("broadcasting block "+ b.id +" from "+ this.id);
		for (int i=0;i<peers.size();i++){
			Node id1=peers.get(i);
			double latency=s.latency(this.id,id1.id,1);

			Task task=new Task(Task.RECEIVE_BLOCK,time+latency,id1,s,0,null,null,b);			//type,time,node
			s.MainQueue.add(task);
		}
	}
}
