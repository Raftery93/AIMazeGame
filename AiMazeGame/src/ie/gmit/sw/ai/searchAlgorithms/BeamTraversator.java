package ie.gmit.sw.ai.searchAlgorithms;

import java.util.*;

import javax.swing.JOptionPane;

import ie.gmit.sw.ai.Node;


public class BeamTraversator extends ResetTraversal implements Traversator {
	
	private Node goal;
	private int beamWidth = 1; 
	private int stepsToExit;
	private boolean quitWhile = true;
	private boolean foundGoal;
	private Node pathGoal;

	
	public BeamTraversator(Node goal, int beamWidth){
		this.goal = goal;
		this.beamWidth = beamWidth;
	}
	
	public void traverse(Node[][] maze, Node node){
		System.out.println("Using Beam Traversator - Looking for Maze Exit Goal!\n");
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		while(!queue.isEmpty() && quitWhile == true){
			node = queue.poll();
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode() && node.nodeType != 'P'){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        setStepsToExit(TraversatorStats.printStats(node, time, visitCount, false));
		        setFoundGoal(true);
				setPathGoal(node);
				System.out.println("Found goal node at: "+ node);
				JOptionPane.showMessageDialog(null, "I found the exit, this is the path I took", "InfoBox: " + "Success!",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			
			// Sleep for x amount of seconds
			//sleep(1);
			
			Node[] children = node.children(maze);
			Collections.sort(Arrays.asList(children),(Node current, Node next) -> current.getHeuristic(goal) - next.getHeuristic(goal));
			
			int bound = 0;
			if (children.length < beamWidth){
				bound = children.length;
			}else{
				bound = beamWidth;
			}
			
			for (int i = 0; i < bound; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}
			if(visitCount > 100) {
				setStepsToExit(TraversatorStats.printStats(node, time, visitCount, false));
				setFoundGoal(true);
				setPathGoal(node);
				JOptionPane.showMessageDialog(null, "I did not find the exit, this is the path I took", "InfoBox: " + "Unsuccessful...",
						JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Path is too long");
				quitWhile = false;
			}
		}
	}

	public int getStepsToExit() {
		return stepsToExit;
	}

	public void setStepsToExit(int stepsToExit) {
		this.stepsToExit = stepsToExit;
	}
	
	public boolean isFoundGoal() {
		return foundGoal;
	}

	public void setFoundGoal(boolean foundGoal) {
		this.foundGoal = foundGoal;
	}

	public Node getPathGoal() {
		return pathGoal;
	}

	public void setPathGoal(Node pathGoal) {
		this.pathGoal = pathGoal;
	}

}