package br.com.involves.prospectIndicator.helper;

import br.com.involves.prospectIndicator.dto.BestRouteDTO;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;

import java.io.*;
import java.util.LinkedList;

/**
 * Baseado/copiado de https://github.com/Sinclert/Heuristics-TSP/blob/master/HK_Optimal.java
 * Feito ajustes para funcionar com Double e não com distancias inteiras, como no original.
 */

public class TravellerSalesmanHelper {

    private static double optimalDistance = Double.MAX_VALUE;
    private static String optimalPath = "";
    private double[][] distances;
    private LinkedList<GeoLocatedObject> points;

    public BestRouteDTO calculate(LinkedList<GeoLocatedObject> points) throws IOException {

        int size = points.size();
        this.points = points;
        distances = new double[size][size];

        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {
                distances[row][col] = GeoMathHelper.calcHaversine(points.get(row), points.get(col));
            }
        }

        String path = "";
        int[] vertices = new int[size - 1];

        for (int i = 1; i < size; i++) {
            vertices[i - 1] = i;
        }

        double bestRouteDistance = procedure(0, vertices, path, 0);

        System.out.println("Path: " + optimalPath + ". Distance = " + optimalDistance);


        return BestRouteDTO.builder().distance(bestRouteDistance).points(getOptimalSequence(optimalPath)).build();
    }

    private double procedure(int initial, int vertices[], String path, double costUntilHere) {

        // We concatenate the current path and the vertex taken as initial
        path = path + Integer.toString(initial) + " - ";
        int length = vertices.length;
        double newCostUntilHere;


        // Exit case, if there are no more options to evaluate (last node)
        if (length == 0) {
            newCostUntilHere = costUntilHere + distances[initial][0];

            // If its cost is lower than the stored one
            if (newCostUntilHere < optimalDistance) {
                optimalDistance = newCostUntilHere;
                optimalPath = path + "0";
            }

            return (distances[initial][0]);
        } else if (costUntilHere > optimalDistance) {
            return 0;
        } else {

            int[][] newVertices = new int[length][(length - 1)];
            double costCurrentNode, costChild;
            double bestCost = Integer.MAX_VALUE;

            // For each of the nodes of the list
            for (int i = 0; i < length; i++) {

                // Each recursion new vertices list is constructed
                for (int j = 0, k = 0; j < length; j++, k++) {

                    // The current child is not stored in the new vertices array
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newVertices[i][k] = vertices[j];
                }

                // Cost of arriving the current node from its parent
                costCurrentNode = distances[initial][vertices[i]];

                // Here the cost to be passed to the recursive function is computed
                newCostUntilHere = costCurrentNode + costUntilHere;

                // RECURSIVE CALLS TO THE FUNCTION IN ORDER TO COMPUTE THE COSTS
                costChild = procedure(vertices[i], newVertices[i], path, newCostUntilHere);

                // The cost of every child + the current node cost is computed
                double totalCost = costChild + costCurrentNode;

                // Finally we select from the minimum from all possible children costs
                if (totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }

            return (bestCost);
        }
    }

    private LinkedList<GeoLocatedObject> getOptimalSequence(String optimalPath) {
        LinkedList<GeoLocatedObject> optimalSequence = new LinkedList<>();
        String[] splitedString = optimalPath.split(" - ");
        for (int i = 0; i < splitedString.length; i++) {
            optimalSequence.add(points.get(Integer.parseInt(splitedString[i])));
        }
        return optimalSequence;
    }
}