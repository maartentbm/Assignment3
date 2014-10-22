package ants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import maze.Maze;
import node.Node;

public class AntRunner extends Thread {

	private static final Brain ExplorerBrain = new Explorer();

	private Maze maze;
	private int maxIterations;
	private ConcurrentHashMap<Integer, Brain> antSetup;
	private float pheromoneAmount;
	private float pheromoneEvaporation;
	private ArrayList<Ant> ants;
	private int[] startLocation, goalLocation;

	public AntRunner(Maze m, int[] startLocation, int[] goalLocation) {

		// Set Maze
		setMaze(m);

		// Set Defaults
		this._setDefaults();

		// Set start / goal location
		this.startLocation = startLocation;
		this.goalLocation = goalLocation;

		// Create ants
		this._createAnts(getantSetup());

	}

	/**
	 * Set default parameters.
	 */
	private void _setDefaults() {

		// Default ant setup
		ConcurrentHashMap<Integer, Brain> defaultAntSetup = new ConcurrentHashMap<Integer, Brain>();
		defaultAntSetup.put(10, new Explorer());
		defaultAntSetup.put(10, new Follower());
		defaultAntSetup.put(10, new Rebel());
		
		// Set default parameters
		setMaxIterations(100);
		setantSetup(defaultAntSetup);
		setPheromoneAmount(10f);
		setPheromoneEvaporation(.1f);

		// Init ants arraylist
		this.ants = new ArrayList<Ant>(this.maxIterations);

	}

	/**
	 * Setup antRunner by creating ants.
	 * 
	 * @param antMap Map containing the amount of ants which should be created with the associated brain. Example: (10 => Explorer), (25 => Follower) will create 10 explorers and 25 followers.
	 */
	private void _createAnts(ConcurrentHashMap<Integer, Brain> antMap) {

		// Init ant ArrayList
		this.ants = new ArrayList<Ant>();
		
		// Iterate over HashMap
		for(Map.Entry<Integer, Brain> cursor : antMap.entrySet()) {
			
			// Create new ants
			for (int i = 0; i < cursor.getKey(); i++) {
				ants.add(new Ant(cursor.getValue(), getMaxIterations(), getPheromoneAmount()));
			}
			
		}
		
	}

	/**
	 * Returns true if both start and goal locations are valid.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 * @return
	 */
	private boolean _checkMaze(Maze maze, int[] startLocation, int[] goalLocation){
		return (maze.getNode(startLocation).isAccessible() && maze.getNode(goalLocation).isAccessible());		
	}
	
	/**
	 * Run ants!
	 */
	public void run() {
		if(_checkMaze(maze, startLocation, goalLocation)==false){
			System.out.println("Start or goal location invalid!");
			return;
		}
		
		// Create new thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		// Create new callable tasks collection
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		
		// Add ants to task list
		for (final Ant a : getAnts()) {
			tasks.add(new Callable<Void>() {
				public Void call() {
					System.out.println("Asynchronous task");
					a.run(getMaze(), getStartLocation(), getGoalLocation());
					return null;
				}
			});
		}

		try {
			// Run ants!
			executorService.invokeAll(tasks);
			
			// Display maze once its finished
			System.out.println("Ants done!");
			
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
			executorService.shutdown();
		}
		
	}

	/**
	 * Retrieves result of all ants after Ant run.
	 * @return ArrayList of paths (path in Node[] form)
	 */
	public ArrayList<Node[]> getResults() {
		
		// Prepare return array
		ArrayList<Node[]> result = new ArrayList<Node[]>();
		
		// Loop ants
		for(Ant a : this.getAnts()) {
			
			// Get path
			if(a.path != null) {
				Node[] path = (Node[]) a.path.toArray();
				result.add(path);
			}
			
		}
		
		return result;
		
	}
	
	/**
	 * @return the maxIterations
	 */
	public int getMaxIterations() {
		return maxIterations;
	}

	/**
	 * @param maxIterations
	 *            the maxIterations to set
	 */
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	/**
	 * @return the antSetup
	 */
	public ConcurrentHashMap<Integer, Brain> getantSetup() {
		return antSetup;
	}

	/**
	 * @param antSetup
	 *            the antSetup to set
	 */
	public void setantSetup(ConcurrentHashMap<Integer, Brain> antSetup) {
		this.antSetup = antSetup;
	}

	/**
	 * @return the pheromoneAmount
	 */
	public float getPheromoneAmount() {
		return pheromoneAmount;
	}

	/**
	 * @param pheromoneAmount
	 *            the pheromoneAmount to set
	 */
	public void setPheromoneAmount(float pheromoneAmount) {
		this.pheromoneAmount = pheromoneAmount;
	}

	/**
	 * @return the pheromoneEvaporation
	 */
	public float getPheromoneEvaporation() {
		return pheromoneEvaporation;
	}

	/**
	 * @param pheromoneEvaporation
	 *            the pheromoneEvaporation to set
	 */
	public void setPheromoneEvaporation(float pheromoneEvaporation) {
		this.pheromoneEvaporation = pheromoneEvaporation;
	}

	/**
	 * @return the maze
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * @param maze
	 *            the maze to set
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	/**
	 * @return the ants
	 */
	public ArrayList<Ant> getAnts() {
		return ants;
	}

	/**
	 * @return the startLocation
	 */
	public int[] getStartLocation() {
		return startLocation;
	}

	/**
	 * @param startLocation
	 *            the startLocation to set
	 */
	public void setStartLocation(int[] startLocation) {
		this.startLocation = startLocation;
	}

	/**
	 * @return the goalLocation
	 */
	public int[] getGoalLocation() {
		return goalLocation;
	}

	/**
	 * @param goalLocation
	 *            the goalLocation to set
	 */
	public void setGoalLocation(int[] goalLocation) {
		this.goalLocation = goalLocation;
	}
}
