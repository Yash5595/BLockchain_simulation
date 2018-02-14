import java.util.*;
public class Task{
	//Types of tasks
	public static final int GENERATE_BLOCK=0;
	public static final int RECEIVE_BLOCK=1;
	public static final int GENERATE_TRANS=2;
	public static final int RECEIVE_TRANS=3;

	double scTime;
	int type;
	Node node;
	Simulator sim;
	int id;
	Block prev_Block;
	Transaction sent_trans;
	Block sent_block;
	

	Task(int type,double scTime,Node node,Simulator simulator,int id,Block prev_Block,Transaction sent_trans,Block sent_block){
		this.type=type;
		this.scTime=scTime;
		this.node=node;
		this.sim=simulator;
		this.id=id;
		this.prev_Block=prev_Block;
		this.sent_trans=sent_trans;
		this.sent_block=sent_block;
	}
	static void simulate(Task t,Simulator s){
		Node n=t.node;
		switch(t.type){
			case(GENERATE_BLOCK):
				n.generateBlock(s, t.id, t.prev_Block);
				break;
			case(RECEIVE_BLOCK):
				n.receiveBlock(t.sent_block, s);
				break;
			case(GENERATE_TRANS):
				n.generateTransaction(s, t.id);
				break;
			case(RECEIVE_TRANS):
				n.receiveTransaction(t.sent_trans, s);
				break;
		}
	}

}