package ann;

public class NetworkRunner {
	
	// Variables about the network
	int nrInputs;
	int nrOutputs;
	int nrLayerPerceptrons;
	int nrHiddenLayers;
	
	// Variables about the run
	int nrEpochs;
	
	Network network;
	
	public NetworkRunner(){
		setSettings();		
		Network network = new Network(nrInputs, nrOutputs, nrHiddenLayers, nrLayerPerceptrons);
	}
	
	public void run(){
		// @TODO import file		
		
		for(int epoch = 0; epoch < nrEpochs; epoch++){
			
			
			
		}
	}
	
	private void setSettings(){
		nrInputs = 10;
		nrOutputs = 7;
		nrLayerPerceptrons = 20;
		nrHiddenLayers = 1;
	}
}
