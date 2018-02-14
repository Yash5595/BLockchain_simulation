import java.util.*;
public class taskComparator implements comparator<Task>{
	@Override
	pubilc int compare(task t1,task t2){
		
	}
}

public class Simulator{
	//inputs
	static int n;
	static double z;
	static double endTime;


	//Parameters
	double LAMBDA_TRANS;
	double D_IJ_MEAN =96000;
	double LAMBDA_TK;
	double[][] P_IJ=new double[n][n];

	//priority queue for scheduling tasks
	PriorityQueue<Task> MainQueue;
	ArrayList<Node> nodes;
	Random random;
	double curr_time;

	
	Simulator(){
		nodes = new ArrayList<Node>();
		random = new Random();
		curr_time = 0.0;
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
		for(int i=0;i<n;i++){
			Float random_float=random.nextFloat()*100;
			if(random_float<z){
				new_node=new Node(i,false,LAMBDA_TRANS,n);
			}else{
				new_node=new Node(i,true,LAMBDA_TRANS,n);
			}
			nodes.add(new_node);
		}

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
			Task t = MainQueue.remove();
			curr_time = t.scTime();
			Task.Simulate(t,s);
		}
	}

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter Integer value for required number of nodes");
		int n = reader.nextInt();

		System.out.println("Enter Double value for required percentage of slow nodes");
		double z = reader.nextDouble();

		System.out.println("Enter Double value for the end time of simulation");
		double endTime = reader.nextDouble();
		reader.close();

		try{
			Simulator s = new Simulator();
			s.doAll(endTime);
		}
	}

}