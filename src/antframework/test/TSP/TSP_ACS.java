
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.test.TSP;
import antframework.common.Matrix;
import antframework.common.Node;
import antframework.common.Graph;
import antframework.common.Enviroment;
import antframework.algorithms.*;
import java.util.*;

/**
 *
 * @author enriqueareyan
 */
public class TSP_ACS extends antframework.algorithms.AntColonySystem {
    public TSP_ACS(Enviroment E) {
        super(E);
    }
    public TSP_ACS(Graph G, int problemInitialNode, int problemDestinationNode, int parametersSet) throws Exception{
       /* Set enviroment */
        Enviroment Env = new Enviroment( G /* Graph */ , true /* random pheromone trail */);
        this.setParam(TSP_ACS.initialNode  , problemInitialNode);
        this.setParam(TSP_ACS.destinationNode , problemDestinationNode);
        
        switch(parametersSet){
            case 0:
                this.setParam(TSP_ACS.maxNumIterations , 10000);
                this.setParam(TSP_ACS.beta , 1);
                this.setParam(TSP_ACS.maxCandidates , Math.floor(G.getM().getColumns() / 2));
                this.setParam(TSP_ACS.r_0 , 0.35);
                this.setParam(TSP_ACS.ro_1 , 0.25);
                this.setParam(TSP_ACS.ro_2 , 0.25);
                this.setParam(TSP_ACS.tau_0 , 0.1);
                Env.setAnts(10);
                break;
            case 1:
                this.setParam(TSP_ACS.maxNumIterations , 100);
                this.setParam(TSP_ACS.beta , 1.25);
                this.setParam(TSP_ACS.maxCandidates , Math.floor(G.getM().getColumns() / 2));
                this.setParam(TSP_ACS.r_0 , 0.3);
                this.setParam(TSP_ACS.ro_1 , 0.2);
                this.setParam(TSP_ACS.ro_2 , 0.2);
                this.setParam(TSP_ACS.tau_0 , 0.1);
                Env.setAnts(1000);
                break;
            case 2:
                this.setParam(TSP_ACS.maxNumIterations , 10000);
                this.setParam(TSP_ACS.beta , 1.75);
                this.setParam(TSP_ACS.maxCandidates , Math.floor(G.getM().getColumns() / 3));
                this.setParam(TSP_ACS.r_0 , 0.25);
                this.setParam(TSP_ACS.ro_1 , 0.15);
                this.setParam(TSP_ACS.ro_2 , 0.15);
                this.setParam(TSP_ACS.tau_0 , 0.1);
                Env.setAnts(1000);
                break;
            case 3:
                this.setParam(TSP_ACS.maxNumIterations , 250);
                this.setParam(TSP_ACS.beta , 2);
                this.setParam(TSP_ACS.maxCandidates , Math.floor(G.getM().getColumns() / 3));
                this.setParam(TSP_ACS.r_0 , 0.2);
                this.setParam(TSP_ACS.ro_1 , 0.1);
                this.setParam(TSP_ACS.ro_2 , 0.1);
                this.setParam(TSP_ACS.tau_0 , 0.1);
                Env.setAnts(25);
                break;
        }
        /* set enviroment*/
        this.setE(Env);

    }
    public Vector<Integer> constrains(int i, Vector<Integer> currentSolution){
        int cols = this.Graph.getM().getColumns();
        Vector<Integer> adjacents = new Vector<Integer>();
        //Calculate adjancent nodes
        for(int j=0; j < cols;j++){
            if(this.Graph.getM().position(i, j) < Integer.MAX_VALUE){
                //Is adyacent
                adjacents.add(j);
            }
        }
        //System.out.println("adjacents("+ i +") = "+adjacents.toString());
        Vector<Integer> posibleNodes = new Vector<Integer>();
        //Verify that the adjacents nodes are not in the solution
        for(int j=0; j<adjacents.size(); j++){
            if(!currentSolution.contains(adjacents.get(j))){
                posibleNodes.add(adjacents.get(j));
            }
        }
        //Check if list is empty
        if(posibleNodes.isEmpty()){
            //The ant has visited al nodes, check to see if this node has connection with the origin
            //and the current solution is as large as the number of nodes in the graph
            if(adjacents.indexOf(this.Parameters.get(AntSystem.initialNode).intValue()) >= 0 &&
                currentSolution.size() == this.Graph.getM().getColumns()){
                //There is a connection with the inital node, add it as the next node to visit
                posibleNodes.add(this.Parameters.get(AntSystem.initialNode).intValue());
            }
        }
        //System.out.println("candidate("+ i +") = "+posibleNodes.toString());
        return posibleNodes;
    }
    public void candidateList(int max){
        Matrix G = this.Graph.getM();
        int rows = G.getRows() , cols = G.getColumns() , cont;

        for(int i = 0; i<rows ; i++){
            cont = 0;
            Vector<Node> nodes = new Vector<Node>();
            for(int j = 0; j<cols; j++){
                if((G.position(i, j) < Integer.MAX_VALUE)){
                    Node nodeTmp = new Node(j,G.position(i, j),1/G.position(i, j));
                    nodes.add(nodeTmp);
                }
            }
            /* Order candidates */
            Collections.sort(nodes);
            /* Truncate vector by max candidates parameter */
            if(nodes.size() > max){
                nodes.setSize(max);
            }
            /* Add this node i candidates */
            this.candidates.put(i, nodes);
        }
    }
    public double heuristicInfo(double number){
        return 1/number;
    }
    @Override
    public double f(Vector<Integer> Solution){
        if(Solution.size()-1 != this.Graph.getM().getColumns()){
            return Double.MAX_VALUE;
        }else{
            return super.f(Solution);
        }
    }
}
