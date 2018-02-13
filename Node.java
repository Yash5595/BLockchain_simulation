import java.util.*;

public class Node{
    int id;
    ArrayList<Double> receivedStamps;
    Random rn = new Random();
    ArrayList<Node> peers;     //pointers to Nodes are stored here
    boolean fast;
    double lambda;
    float coins;
    boolean type;           // True => Fast CPU

    float coins = rn.nextFloat()*1000 +1;               ////////decide amount

    Node(int id, boolean fast, double lambda){
        this.id = id;
        this.fast = fast;
        this.lambda = lambda;
        if(lambda > 0.5 ){                                                              //////// check for lambda value
            this.type = true;
        }
        else{
            this.type = false;
        }
        receivedStamps = new ArrayList<Double>();
        peers = new ArrayList<Node>();
    }

    public void setPeers(int n, ArrayList<Node> all){
        Collections.shuffle(all);
        int numPeers = rn.nextInt(n-1) + 1;

        for(int i=0; i<numPeers; i++){
            if(id != all.get(i).id)
                peers.add(all.get(i));
        }
    }
}