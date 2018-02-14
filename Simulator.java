public class Simulator{

	ArrayList<Node> nodes;
	ArrayList<ArrayList<Block>> blocks;
	ArrayList<ArrayList<Transaction>> trans;
	Random rand;
	double curr_time;

	Simulator(){

		nodes = new ArrayList<Node>();
		blocks = new ArrayList<ArrayList<Block>>(n);
		trans = new ArrayList<ArrayList<Transaction>>(n);
		rand = new Random();
		curr_time = 0.0;

		for(int i=0;i<n;i++){
			blocks.add(i,ArrayList<Block>());
			trans.add(i,ArrayList<Transaction>());
		}

		
	}

	void init(){

		double mean = 2.0;   								///////// take input

		for(int i=0;i<n;i++){
			int rand2 = rand.nextInt(98) +1;
				Node new_node = new Node(i,true,mean,)
			}
		}

	}


	double latency(int i, int j,int size){

		Node p_i = nodes.get(i);
		Node p_j = nodes.get(j);

		double c_ij;
		if(p_i.fast && p_j.fast){
			c_ij = 100000;
		}
		else{
			c_ij = 5000;
		}

		double b = (size*8.0*1000000)/c_ij; 							////// size= 0 when transaction & 1 when block
		double lambda = (c_ij/96000);
		double d = Math.log(1- Math.random())/(-lambda);
		double laten = b + P_IJ[i][j] + d;
		return laten;
	}




}