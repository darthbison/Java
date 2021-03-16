package chapter1;

import chapter1.math.IActivationFunction;
import chapter1.math.Linear;
import chapter1.math.RandomNumberGenerator;
import chapter1.math.Sigmoid;

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
        NeuralNet nn = new NeuralNet(numberOfInputs,numberOfOutputs,
                numberOfHiddenNeurons,hiddenAcFnc,outputAcFnc);
        
        double [] neuralInput = { 1.5 , 0.5 };
        
        double [] neuralOutput;
        nn.setInputs(neuralInput);
        nn.calc();
        neuralOutput=nn.getOutputs();
        
        neuralInput[0] = 1.0;
        neuralInput[1] = 2.1;
        
        nn.setInputs(neuralInput);
        nn.calc();
        neuralOutput=nn.getOutputs();
        
    }
}
