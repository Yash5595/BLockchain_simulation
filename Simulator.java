//import java.util.*;
//class taskComparator implements Comparator<Task>{
//	@Override
//	public int compare(Task t1,Task t2){
//		if(t1.scTime<t2.scTime){
//			return -1;
//		}
//		if(t1.scTime>t2.scTime){
//			return 1;
//		}
//		return 0;
//	}
//}
//
//public class Simulator{
//	//inputs
//	static int n;
//	static int z;
//
//
//	//Parameters
//	double LAMBDA_TRANS;
//	double D_IJ_MEAN;
//	double LAMBDA_TK;
//	double[][] P_IJ=new double[n][n];
//
//	//priority queue for scheduling tasks
//	PriorityQueue<Task> MainQueue;
//	Comparator<Task> comparator;
//	ArrayList<Node> nodes;
//	Random random;
//	double curr_time;
//	int next_trans_id;
//	int next_block_id;
//	Simulator(){
//		nodes = new ArrayList<Node>();
//		random = new Random();
//		curr_time = 0.0;
//		comparator=new taskComparator();
//		next_trans_id=0;
//		next_block_id=0;
//		MainQueue=new PriorityQueue(comparator);
//		Float random_float;
//		for(int i=0;i<n;i++){
//			for(int j=0;j<n;j++){
//				random_float=random.nextFloat();
//				P_IJ[i][j]=10.0+random_float*490.0;
//			}
//		}
//	}
//
//	void init(){
//		Node new_node;
//		Block genesis = new Block(getNextBlockId(),this.curr_time,null,-1,0);
//		for(int i=0;i<n;i++){
//			Float random_float=random.nextFloat()*100;
//			if(random_float<z){
//				new_node=new Node(i,false,LAMBDA_TRANS,genesis,n);
//			}else{
//				new_node=new Node(i,true,LAMBDA_TRANS,genesis,n);
//			}
//			nodes.add(new_node);
//		}
//		Task t;
//		double t_k;
//		for(int i=0;i<n;i++){
//			t_k=Math.log(1-random.nextFloat())/(-LAMBDA_TK);
//			t=new Task(Task.GENERATE_BLOCK,curr_time+t_k,nodes.get(i),this,getNextBlockId(),genesis,null,null);
//			MainQueue.add(t);
//		}
//		double t_trans;
//		for(int i=0;i<n;i++){
//			t_trans=Math.log(1-random.nextFloat())/(-LAMBDA_TRANS);
//			t=new Task(Task.GENERATE_TRANS,curr_time+t_trans,nodes.get(i),this,getNextTransId(),null,null,null);
//			MainQueue.add(t);
//		}
//		
//	}
//	
//	int getNextTransId() {
//		int h =next_trans_id;
//		next_trans_id++;
//		return h;
//	}
//
//	int getNextBlockId() {
//		int h =next_block_id;
//		next_block_id++;
//		return h;
//	}
//
//	double latency(int i, int j,int size){
//
//		Node p_i = nodes.get(i);
//		Node p_j = nodes.get(j);
//		double c_ij;
//		if(p_i.fast && p_j.fast ==true){
//			c_ij = 100000000;
//		}
//		else{
//			c_ij = 5000000;
//		}
//
//		double b = (size*8.0*1000000)/c_ij; 							////// size= 0 when transaction & 1 when block
//		double lambda = (c_ij/96000);
//		double d = Math.log(1- Math.random())/(-lambda);
//		double laten = b + P_IJ[i][j] + d;
//		return laten;
//	}
//	
//
//
//}
import java.util.*;
class taskComparator implements Comparator<Task>{
	@Override
	public int compare(Task t1,Task t2){
		if(t1.scTime<t2.scTime){
			return -1;
		}
		if(t1.scTime>t2.scTime){
			return 1;
		}
		return 0;
	}
}

public class Simulator{
	//inputs
	static int n;
	static double z;
	static double endTime;


	//Parameters
	double LAMBDA_TRANS;
	double D_IJ_MEAN;
	double LAMBDA_TK;
	double[][] P_IJ;

	//priority queue for scheduling tasks
	PriorityQueue<Task> MainQueue;
	Comparator<Task> comparator;
	ArrayList<Node> nodes;
	Random random;
	double curr_time;
	int next_trans_id;
	int next_block_id;

	
	Simulator(int n,double z){
		D_IJ_MEAN=96000;
		LAMBDA_TRANS=2.0;
		LAMBDA_TK=0.25;
		nodes = new ArrayList<Node>();
		random = new Random();
		curr_time = 0.0;
		comparator=new taskComparator();
		next_trans_id=0;
		next_block_id=0;
		this.n=n;
		this.z=z;
		P_IJ=new double[n][n];
		System.out.println("here1");
		MainQueue=new PriorityQueue<Task>(comparator);
		Float random_float;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				random_float=random.nextFloat();
				P_IJ[i][j]=10.0+random_float*490.0;
			}
		}		
	}

	void init(){
		Node new_node;
		System.out.println("here____________________________________________");
		Block genesis = new Block(getNextBlockId(),this.curr_time,null,-1,0);
		for(int i=0;i<n;i++){
			Float random_float=random.nextFloat()*100;
			if(random_float<z){
				new_node=new Node(i,false,LAMBDA_TRANS,genesis,n);
			}else{
				new_node=new Node(i,true,LAMBDA_TRANS,genesis,n);
			}
			nodes.add(new_node);
		}
		for(int i=0;i<n;i++){
			new_node=nodes.get(i);
			new_node.setPeers(n,nodes);
		}
		Task t;
		double t_k;
		for(int i=0;i<n;i++){
			t_k=Math.log(1-random.nextFloat())/(-LAMBDA_TK);
			t=new Task(Task.GENERATE_BLOCK,curr_time+t_k,nodes.get(i),this,getNextBlockId(),genesis,null,null);
			MainQueue.add(t);
		}
		double t_trans;
		for(int i=0;i<n;i++){
			t_trans=Math.log(1-random.nextFloat())/(-LAMBDA_TRANS);
			t=new Task(Task.GENERATE_TRANS,curr_time+t_trans,nodes.get(i),this,getNextTransId(),null,null,null);
			MainQueue.add(t);
		}
		System.out.println(MainQueue.size());
	}
	
	int getNextTransId() {
		int h =next_trans_id;
		next_trans_id++;
		return h;
	}

	int getNextBlockId() {
		int h =next_block_id;
		next_block_id++;
		return h;
	}

	double latency(int i, int j,int size){

		Node p_i = nodes.get(i);
		Node p_j = nodes.get(j);
		double c_ij;

		if(p_i.fast && p_j.fast ==true){
			c_ij = 100000000;
		}
		else{
			c_ij = 5000000;
		}

		double b = (size*8.0*1000000)/c_ij;	 							//// size= 0 when transaction & 1 when block
		double lambda = (c_ij/D_IJ_MEAN);
		double d = Math.log(1- Math.random())/(-lambda);
		return (b + P_IJ[i][j] + d);
	}

	void doAll(double end_time){
		while(curr_time< end_time){
			//System.out.println(MainQueue.size());
			Task t = MainQueue.remove();
			curr_time = t.scTime;
			System.out.println("time : "+ curr_time+"type of task : "+t.type);
			Task.simulate(t,this);
		}
		printbalancesheets(nodes);
	}

	private void printbalancesheets(ArrayList<Node> nodes2) {
		// TODO Auto-generated method stub
		Node node_t;
		HashMap<Integer,Double> balanceSheet;
		for(int i=0;i<nodes2.size();i++){
			node_t=nodes.get(i);
			balanceSheet = node_t.balances;
			for(int j=0;j<n;j++){
				System.out.println(balanceSheet.get(j)+" "+j+" for node "+node_t.id);
			}
		}
	}

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter Integer value for required number of nodes");
		int x = reader.nextInt();

		System.out.println("Enter Double value for required percentage of slow nodes");
		double y = reader.nextDouble();

		System.out.println("Enter Double value for the end time of simulation");
		double endTime = reader.nextDouble();
		reader.close();

		Simulator s = new Simulator(x,y);
		s.init();
		s.doAll(endTime);
		
	}

}