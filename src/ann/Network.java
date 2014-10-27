package ann;

import java.util.ArrayList;
import java.util.Random;

public class Network {
	
	private ArrayList<Perceptron> inputLayer = new ArrayList<Perceptron>();
	private ArrayList<ArrayList<Perceptron>> hiddenLayers = new ArrayList<ArrayList<Perceptron>>();
	private ArrayList<Perceptron> outputLayer = new ArrayList<Perceptron>();
	// Unsure about the true randomness of this object.
	private static final Random random = new Random();
	
	/**
	 * Constructor mostly makes a lot of perceptrons.
	 * @param nrInputs
	 * @param nrOutputs
	 * @param nrHiddenLayers
	 * @param nrLayerPerceptrons
	 */
	public Network(int nrInputs, int nrOutputs, int nrHiddenLayers, int nrLayerPerceptrons){
		inputLayer = makePerceptrons(nrInputs);
		outputLayer = makePerceptrons(nrOutputs);
		
		for(int i = 0; i < nrHiddenLayers; i++){
			hiddenLayers.add(makePerceptrons(nrLayerPerceptrons));
		}
		
		// Simply connects all the Layers
		// Separated for clarities sake.
		connect();
	}

	public double[] feedForward(double[] inputValues){
		double[] outputValues = new double[outputLayer.size()];
		
		for(int i = 0; i < inputLayer.size(); i++){
			inputLayer.get(i).setOutputValue(inputValues[i]);
		}
		
		for(int i = 0; i < hiddenLayers.size(); i++){
			for(int j = 0; j < hiddenLayers.get(i).size(); j++){
				hiddenLayers.get(i).get(j).activateFunction();
			}
		}
		
		for(int i = 0; i < outputLayer.size(); i++){
			outputLayer.get(i).activateFunction();
			outputValues[i] = outputLayer.get(i).getOutputValue();
		}
		
		return outputValues;
	}
	
	/**
	 * Makes an ArrayList<Perceptron> and fills in with the number of perceptrons.
	 * Including random threshold values [-1,1>
	 * @param numberOfPerceptrons
	 * @return
	 */
	private ArrayList<Perceptron> makePerceptrons(int numberOfPerceptrons){
		ArrayList<Perceptron> newList = new ArrayList<Perceptron>();
		for( int i = 0; i < numberOfPerceptrons; i++)
				newList.add(new Perceptron( 2*random.nextDouble()-1 ));
		return newList;
	}
	
	/**
	 * Connects the layers
	 * Only used in constructor, mainly for clarity of code.
	 */
	private void connect(){
		connectLayers(inputLayer, hiddenLayers.get(0));
		
		for(int i = 0; i < hiddenLayers.size() -1; i++){
			connectLayers(hiddenLayers.get(i),hiddenLayers.get(i+1));
		}
		connectLayers(hiddenLayers.get(hiddenLayers.size()-1) , outputLayer);
	}
	/**
	 * Connects the perceptrons in the given lists
	 * Only used in constructor
	 * Mainly for clarity of code.
	 * @param connectFrom
	 * @param connectTo
	 */
	private void connectLayers(ArrayList<Perceptron> connectFrom, ArrayList<Perceptron> connectTo){
		for(int i = 0; i < connectTo.size(); i++){
			for(int j = 0; j < connectFrom.size(); j++){
				// An output from connectFrom towards connectTo is added automatically.
				// See Perceptron.addInput();
				// We chose to activate this coupling from an input perspective
				// So we can reuse the random object in this class.
				connectTo.get(i).addInput(connectFrom.get(j), 2*random.nextDouble()-1 );
			}
		}
	}
}
