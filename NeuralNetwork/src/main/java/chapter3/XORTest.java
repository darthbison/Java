/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter3;

import chapter3.data.NeuralDataSet;
import chapter3.learn.Backpropagation;
import chapter3.learn.DeltaRule;
import chapter3.learn.LearningAlgorithm;
import chapter3.math.IActivationFunction;
import chapter3.math.Linear;
import chapter3.math.RandomNumberGenerator;
import chapter3.math.Sigmoid;

/**
 *
 * @author fab
 */
public class XORTest {
    public static void main(String[] args){
        RandomNumberGenerator.seed=0;
        
        int numberOfInputs=2;
        int numberOfOutputs=1;
        
        int[] numberOfHiddenNeurons={2};
        
        Linear outputAcFnc = new Linear(1.0);
        Sigmoid hdAcFnc = new Sigmoid(1.0);
        IActivationFunction[] hiddenAcFnc={hdAcFnc
        };
        
        NeuralNet perceptron = new NeuralNet(numberOfInputs,numberOfOutputs,outputAcFnc);

        NeuralNet mlp = new NeuralNet(numberOfInputs,numberOfOutputs
                ,numberOfHiddenNeurons,hiddenAcFnc,outputAcFnc);
        
        Double[][] _neuralDataSet = {
            {0.0 , 0.0 , 1.0 }
        ,   {0.0 , 1.0 , 0.0 }
        ,   {1.0 , 0.0 , 0.0 }
        ,   {1.0 , 1.0 , 1.0 }
        };
        
        int[] inputColumns = {0,1};
        int[] outputColumns = {2};
        
        NeuralDataSet neuralDataSet = new NeuralDataSet(_neuralDataSet,inputColumns,outputColumns);
        
        DeltaRule deltaRule=new DeltaRule(perceptron,neuralDataSet
                ,LearningAlgorithm.LearningMode.ONLINE);
        
        deltaRule.printTraining=true;
        deltaRule.setLearningRate(0.1);
        deltaRule.setMaxEpochs(4000);
        deltaRule.setGeneralErrorMeasurement(DeltaRule.ErrorMeasurement.SimpleError);
        deltaRule.setOverallErrorMeasurement(DeltaRule.ErrorMeasurement.MSE);
        deltaRule.setMinOverallError(0.1);
        
        Backpropagation backprop = new Backpropagation(mlp,neuralDataSet
                ,LearningAlgorithm.LearningMode.ONLINE);
        
        backprop.printTraining=true;
        backprop.setLearningRate(0.3);
        backprop.setMaxEpochs(4000);
        backprop.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        backprop.setOverallErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        backprop.setMinOverallError(0.01);
        backprop.setMomentumRate(0.6);
        
        try{
            deltaRule.forward();
            neuralDataSet.printNeuralOutput();
            
            System.out.println("Beginning Delta Rule training");
            
            deltaRule.train();
            
            System.out.println("End of Delta Rule training");
            if(deltaRule.getMinOverallError()>=deltaRule.getOverallGeneralError()){
                System.out.println("Training succesful!");
            }
            else{
                System.out.println("Training was unsuccesful");
            }
            System.out.println("Overall Error:"
                        +String.valueOf(deltaRule.getOverallGeneralError()));
            System.out.println("Min Overall Error:"
                        +String.valueOf(deltaRule.getMinOverallError()));
            System.out.println("Epochs of training:"
                        +String.valueOf(deltaRule.getEpoch()));
            
            System.out.println("Target Outputs:");
            neuralDataSet.printTargetOutput();
            
            System.out.println("Neural Output after training:");
            deltaRule.forward();
            neuralDataSet.printNeuralOutput();
            
            
            System.out.println("Beginning Backpropagation training");
            
            backprop.forward();
            neuralDataSet.printNeuralOutput();
            
            backprop.train();
            System.out.println("End of training");
            if(backprop.getMinOverallError()>=backprop.getOverallGeneralError()){
                System.out.println("Training successful!");
            }
            else{
                System.out.println("Training was unsuccessful");
            }
            System.out.println("Overall Error:"
                        +String.valueOf(backprop.getOverallGeneralError()));
            System.out.println("Min Overall Error:"
                        +String.valueOf(backprop.getMinOverallError()));
            System.out.println("Epochs of training:"
                        +String.valueOf(backprop.getEpoch()));
            
            System.out.println("Target Outputs:");
            neuralDataSet.printTargetOutput();
            
            System.out.println("Neural Output after training:");
            backprop.forward();
            neuralDataSet.printNeuralOutput();

            
        }
        catch(NeuralException ne){
            
        }
        
    }
    
}
