package ie.gmit.sw.ai.searchAlgorithms;

import ie.gmit.sw.ai.Node;

//Interface for traversers
public interface Traversator {
	public void traverse(Node[][] maze, Node start);
	public int getStepsToExit();
}
