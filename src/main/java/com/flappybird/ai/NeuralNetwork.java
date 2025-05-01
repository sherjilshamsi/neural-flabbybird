package com.flappybird.ai;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NeuralNetwork {
    private MultiLayerNetwork network;
    
    public NeuralNetwork() {
        // Create a simple neural network with 2 input neurons, 4 hidden neurons, and 1 output neuron
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(123)
            .weightInit(WeightInit.XAVIER)
            .updater(new Adam(0.01))
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(2)
                .nOut(4)
                .activation(Activation.RELU)
                .build())
            .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.XENT)
                .nIn(4)
                .nOut(1)
                .activation(Activation.SIGMOID)
                .build())
            .build();
        
        network = new MultiLayerNetwork(conf);
        network.init();
    }
    
    public double[] predict(double[] inputs) {
        INDArray input = Nd4j.create(inputs);
        INDArray output = network.output(input);
        return output.toDoubleVector();
    }
    
    public void train(double[] inputs, double[] targets) {
        INDArray input = Nd4j.create(inputs);
        INDArray target = Nd4j.create(targets);
        network.fit(input, target);
    }
} 