package chapter3;

import chapter3.math.IActivationFunction;
import chapter3.math.Linear;
import chapter3.math.RandomNumberGenerator;
import chapter3.math.Sigmoid;

/**
 *
 * NeuralNetConsoleTest
 * This class is solely used for creating and testing your very first Neural 
 * Network in Java 
 *
 * @author Alan de Souza, Fábio Soares
 */
public class NeuralNetConsoleTest {
    public static void main(String[] args){
        
        RandomNumberGenerator.seed=0;
        
        int numberOfInputs=2;
        int numberOfOutputs=1;
        int[] numberOfHiddenNeurons= { 3 };
        IActivationFunction[] hiddenAcFnc = { new Sigmoid(1.0) } ;
        Linear outputAcFnc = new Linear(1.0);
        System.out.println("Creating Neural Network...");
        NeuralNet nn = new NeuralNet(numberOfInputs,numberOfOutputs,
                numberOfHiddenNeurons,hiddenAcFnc,outputAcFnc);
        System.out.println("Neural Network created!");
        nn.print();
        
        double [] neuralInput = { 1.5 , 0.5 };
        
        double [] neuralOutput;
        System.out.println("Feeding the values ["+
                String.valueOf(neuralInput[0])+" ; "+
                String.valueOf(neuralInput[1])+"] to the neural network");
        nn.setInputs(neuralInput);
        nn.calc();
        neuralOutput=nn.getOutputs();
        
        System.out.println("Output generated:"+String.valueOf(neuralOutput[0]));
        
        neuralInput[0] = 1.0;
        neuralInput[1] = 2.1;
        
        System.out.println("Feeding the values ["+
                String.valueOf(neuralInput[0])+" ; "+
                String.valueOf(neuralInput[1])+"] to the neural network");
        nn.setInputs(neuralInput);
        nn.calc();
        neuralOutput=nn.getOutputs();
        
        System.out.println("Output generated:"+String.valueOf(neuralOutput[0]));
        
    }
}
