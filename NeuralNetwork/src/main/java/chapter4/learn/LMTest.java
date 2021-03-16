/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter4.learn;

import chapter4.NeuralException;
import chapter4.NeuralNet;
import chapter4.data.NeuralDataSet;
import chapter4.init.UniformInitialization;
import chapter4.math.IActivationFunction;
import chapter4.math.Linear;
import chapter4.math.RandomNumberGenerator;
import chapter4.math.Sigmoid;

/**
 *
 * @author fab
 */
public class LMTest {
    public static void main(String[] args){
        //NeuralNet nn = new NeuralNet
        RandomNumberGenerator.seed=0;
        
        int numberOfInputs=3;
        int numberOfOutputs=4;
        int[] numberOfHiddenNeurons={4,3,2};
        
        
        Linear outputAcFnc = new Linear(1.0);
        Sigmoid hl0Fnc = new Sigmoid(1.0);
        Sigmoid hl1Fnc = new Sigmoid(1.0);
        Sigmoid hl2Fnc = new Sigmoid(1.0);
        IActivationFunction[] hiddenAcFnc={hl0Fnc
                ,hl1Fnc
                ,hl2Fnc
        };
        System.out.println("Creating Neural Network...");
        NeuralNet nn = new NeuralNet(numberOfInputs, numberOfOutputs
                , numberOfHiddenNeurons, hiddenAcFnc, outputAcFnc
                ,new UniformInitialization(-1.0,1.0));
        System.out.println("Neural Network created!");
        nn.print();
        
        double[][] _neuralDataSet = {
            {-1.0,-1.0,-1.0,-1.0,1.0,-3.0,1.0}
        ,   {-1.0,-1.0,1.0,1.0,-1.0,-1.0,-1.0}
        ,   {-1.0,1.0,-1.0,1.0,-1.0,-1.0,-1.0}
        ,   {-1.0,1.0,1.0,-1.0,-1.0,1.0,-3.0}
        ,   {1.0,-1.0,-1.0,1.0,-1.0,-1.0,3.0}                
        ,   {1.0,-1.0,1.0,-1.0,-1.0,1.0,1.0}
        ,   {1.0,1.0,-1.0,-1.0,1.0,1.0,1.0}
        ,   {1.0,1.0,1.0,1.0,-1.0,3.0,-1.0}
        };
        
        int[] inputColumns = {0,1,2};
        int[] outputColumns = {3,4,5,6};
        
        NeuralDataSet neuralDataSet = new NeuralDataSet(_neuralDataSet,inputColumns,outputColumns);
        
        System.out.println("Dataset created");
        neuralDataSet.printInput();
        neuralDataSet.printTargetOutput();
        
        System.out.println("Getting the first output of the neural network");
        
        LevenbergMarquardt lma = new LevenbergMarquardt(nn,neuralDataSet,LearningAlgorithm.LearningMode.BATCH);
        lma.setDamping(0.001);
        lma.setMaxEpochs(100);
        lma.setGeneralErrorMeasurement(Backpropagation.ErrorMeasurement.SimpleError);
        lma.setOverallErrorMeasurement(Backpropagation.ErrorMeasurement.MSE);
        lma.setMinOverallError(0.0001);
        lma.printTraining=true;
        
        
        try{
            lma.forward();
            neuralDataSet.printNeuralOutput();
            
            lma.train();
            System.out.println("End of training");
            if(lma.getMinOverallError()>=lma.getOverallGeneralError()){
                System.out.println("Training successful!");
            }
            else{
                System.out.println("Training was unsuccessful");
            }
            System.out.println("Overall Error:"
                        +String.valueOf(lma.getOverallGeneralError()));
            System.out.println("Min Overall Error:"
                        +String.valueOf(lma.getMinOverallError()));
            System.out.println("Epochs of training:"
                        +String.valueOf(lma.getEpoch()));
            
            System.out.println("Target Outputs:");
            neuralDataSet.printTargetOutput();
            
            System.out.println("Neural Output after training:");
            lma.forward();
            neuralDataSet.printNeuralOutput();
            

        }
        catch(NeuralException ne){
            
        }
    }
    
}
