package ants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import maze.Maze;

public class AntRunner extends Thread {

	private static final Brain ExplorerBrain = new Explorer();

	private Maze maze;
	private int maxIterations;
	private int antsPatrolling;
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
		this._createAnts(ExplorerBrain);

	}

	/**
	 * Set default parameters.
	 */
	private void _setDefaults() {

		// Set default parameters
		setMaxIterations(100);
		setAntsPatrolling(1);
		setPheromoneAmount(10f);
		setPheromoneEvaporation(.1f);

		// Init ants arraylist
		this.ants = new ArrayList<Ant>(this.maxIterations);

	}

	/**
	 * Setup antRunner by creating ants.
	 */
	private void _createAnts(Brain brain) {

		this.ants = new ArrayList<Ant>(getAntsPatrolling());
		for (int i = 0; i < getAntsPatrolling(); i++) {
			ants.add(new Ant(brain, getMaxIterations(), getPheromoneAmount()));
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
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>(getAntsPatrolling());
		
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
		
		Ant remcoDeMier = new Ant(new Follower(), getMaxIterations(), getPheromoneAmount() );
		remcoDeMier.run(getMaze(), getStartLocation(), getGoalLocation());
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
	 * @return the antsPatrolling
	 */
	public int getAntsPatrolling() {
		return antsPatrolling;
	}

	/**
	 * @param antsPatrolling
	 *            the antsPatrolling to set
	 */
	public void setAntsPatrolling(int antsPatrolling) {
		this.antsPatrolling = antsPatrolling;
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
