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




}