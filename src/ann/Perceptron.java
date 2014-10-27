package ann;

import java.util.ArrayList;

public class Perceptron {
		private ArrayList<Perceptron> input = new ArrayList<Perceptron>();
		private ArrayList<Perceptron> output = new ArrayList<Perceptron>();
		
		private ArrayList<Double> weights = new ArrayList<Double>();
		private double threshold = 0;
		private double outputValue = 0;
		
		public Perceptron(double newThreshold){
			threshold = newThreshold;
		}
		
		public void activateFunction(){
			double inputSum = 0;
			for(int i = 0; i < input.size(); i ++){
				inputSum += input.get(i).getOutputValue()*weights.get(i);
			}
			outputValue = 1 / (1+Math.exp( threshold - inputSum ));
		}
		
		public void addInput(Perceptron perceptron, double weight){
			input.add(perceptron);
			weights.add(weight);
			perceptron.addOutput(this);
		}
		
		public void addOutput(Perceptron perceptron){
			output.add(perceptron);
		}
		
		public void setOutputValue(double newOutputValue){
			outputValue = newOutputValue;
		}
		
		public double getOutputValue(){
			return outputValue;
		}
		
		public ArrayList<Perceptron> getInput(){
			return input;
		}
		
		public ArrayList<Perceptron> getOutput(){
			return output;
		}
		
		public void setThreshold(double newThreshold){
			threshold = newThreshold;
		}
		
		public double getThreshold(){
			return threshold;
		}
}