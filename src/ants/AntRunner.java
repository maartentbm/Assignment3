package ants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import maze.Maze;
import maze.ResultSet;

public class AntRunner extends Thread {

	private static final Brain ExplorerBrain = new Explorer();

	private Maze maze;
	private int maxIterations;
	private AntSetup antSetup;
	private float pheromoneAmount;
	private float pheromoneEvaporation;
	private LinkedList<ArrayList<Ant>> ants;
	private int[] startLocation, goalLocation;

	public AntRunner(Maze m, int[] startLocation, int[] goalLocation) {

		// Set Maze
		setMaze(m);

		// Set Defaults
		this._setDefaults();

		// Set start / goal location
		this.startLocation = startLocation;
		this.goalLocation = goalLocation;

	}

	/**
	 * Set default parameters.
	 */
	private void _setDefaults() {

		// Create antSetup
		AntSetup defaultAntSetup = new AntSetup();

		// Init waves
		ConcurrentHashMap<Brain, Integer> wave_01, wave_02;

		for (int i = 100; i > 0; i--) {
			wave_01 = new ConcurrentHashMap<Brain, Integer>();
			wave_01.put(new Explorer(), 50);
			defaultAntSetup.add(wave_01);
		}
		

		// Highscores to guess an number of iterations.
		//    Easy: 38
		//  Medium: 133
		//    Hard: 869

		// Set default parameters
		setMaxIterations(2000);
		setAntSetup(defaultAntSetup);
		setPheromoneAmount(10f);
		setPheromoneEvaporation(.15f);

		// Create ants
		this._createAnts(getAntSetup());

	}

	/**
	 * Setup antRunner by creating ants.
	 * 
	 * @param antMap Map containing the amount of ants which should be created with the associated brain. Example: (10 => Explorer), (25 => Follower) will create 10 explorers and 25 followers.
	 */
	private void _createAnts(AntSetup antMap) {

		// Init ant ArrayList
		this.ants = new LinkedList<ArrayList<Ant>>();

		// Iterate over antMap
		Iterator<ConcurrentHashMap<Brain, Integer>> it = antMap.iterator();
		while (it.hasNext()) {

			// Init list for current wave
			ArrayList<Ant> wave = new ArrayList<Ant>();

			// Iterate over HashMap
			for (Map.Entry<Brain, Integer> cursor : it.next().entrySet()) {

				// Create new ants
				for (int i = 0; i < cursor.getValue(); i++) {
					wave.add(new Ant(cursor.getKey(), getMaxIterations(), getPheromoneAmount()));
					// System.out.println(cursor.getKey());
				}
			}

			this.ants.add(wave);
		}
	}

	/**
	 * Returns true if both start and goal locations are valid.
	 * 
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 * @return
	 */
	private boolean _checkMaze(Maze maze, int[] startLocation, int[] goalLocation) {

//		System.out.println(maze.getNode(startLocation));
//		System.out.println(maze.getNode(goalLocation));

		return (maze.getNode(startLocation).isAccessible() && maze.getNode(goalLocation).isAccessible());
	}

	/**
	 * Run ants!
	 */
	public void run() {
		if (_checkMaze(maze, startLocation, goalLocation) == false) {
			System.out.println("Start or goal location invalid!");
			return;
		}

		// Loop ant waves
		LinkedList<ArrayList<Ant>> antlist = getAnts();
		ArrayList<Ant> alist;
		for (int wave = 0; wave < antlist.size(); wave++) {

			alist = antlist.get(wave);
			
			// Create new thread pool
			ExecutorService executorService = Executors.newFixedThreadPool(25);

			// Create new callable tasks collection
			List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();

			// Add ants to task list
			for (final Ant a : alist) {
				tasks.add(new Callable<Void>() {
					public Void call() {
						//System.out.println("Asynchronous task");
						a.run(getMaze(), getStartLocation(), getGoalLocation());
						return null;
					}
				});
			}

			try {
				// Run ants!
				executorService.invokeAll(tasks);

				// Display maze once its finished
//				System.out.println("==========\nAnts done!\n==========");
//				System.out.println();
				
				executorService.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
				executorService.shutdown();
			}

			getMaze().evaporate(getPheromoneEvaporation(), this, wave);

		}

	}

	/**
	 * Retrieves result of all ants after Ant run.
	 * 
	 * @return ArrayList of paths (path in Node[] form)
	 */
	public ResultSet getResults() {

		// Prepare return array
		ResultSet result = new ResultSet();

		// Loop ant list
		for (ArrayList<Ant> alist : this.getAnts()) {

			// Loop ants
			for (Ant a : alist) {

				// Get path
				if (a.path != null && a.path.size() > 0) {

					// Verify that the Ant reached the endpoint
					int[] lastLoc = a.path.get(a.path.size() - 1).getLocation();
					int[] goalLoc = getGoalLocation();

					if (lastLoc[0] == goalLoc[0] && lastLoc[1] == goalLoc[1]) {

						// Add path to result
						result.add(a.path);

					}
				}

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
	 * @param maxIterations the maxIterations to set
	 */
	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	/**
	 * @return the antSetup
	 */
	public AntSetup getAntSetup() {
		return antSetup;
	}

	/**
	 * @param antSetup the antSetup to set
	 */
	public void setAntSetup(AntSetup antSetup) {
		this.antSetup = antSetup;

		// Create ants
		this._createAnts(antSetup);
	}

	/**
	 * @return the pheromoneAmount
	 */
	public float getPheromoneAmount() {
		return pheromoneAmount;
	}

	/**
	 * @param pheromoneAmount the pheromoneAmount to set
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
	 * @param pheromoneEvaporation the pheromoneEvaporation to set
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
	 * @param maze the maze to set
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	/**
	 * @return the ants
	 */
	public LinkedList<ArrayList<Ant>> getAnts() {
		return ants;
	}

	/**
	 * @return the startLocation
	 */
	public int[] getStartLocation() {
		return startLocation;
	}

	/**
	 * @param startLocation the startLocation to set
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
	 * @param goalLocation the goalLocation to set
	 */
	public void setGoalLocation(int[] goalLocation) {
		this.goalLocation = goalLocation;
	}
}
