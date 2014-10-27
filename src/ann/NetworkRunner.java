package ann;

public class NetworkRunner {
	
	// Variables about the network
	int nrInputs;
	int nrOutputs;
	int nrLayerPerceptrons;
	int nrHiddenLayers;
	
	// Variables about the run
	int nrEpochs;
	double minMSE;
	
	
	Network network;
	
	public NetworkRunner(){
		setSettings();		
		Network network = new Network(nrInputs, nrOutputs, nrHiddenLayers, nrLayerPerceptrons);
	}
	
	public void run(){
		// @TODO import file
		
		// @TODO loop over Epochs.
		// @TODO loop per Epoch.
	}
	
	private void setSettings(){
		nrInputs = 10;
		nrOutputs = 7;
		nrLayerPerceptrons = 20;
		nrHiddenLayers = 1;
	}
}
