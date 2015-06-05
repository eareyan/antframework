/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.test.TSP;
import antframework.common.Graph;
import antframework.common.Enviroment;
import java.util.Vector;
import antframework.algorithms.MMAS;

/**
 *
 * @author enriqueareyan
 */
public class TSP_MMAS extends antframework.algorithms.MMAS{
    public TSP_MMAS(Enviroment E) {
        super(E);
    }
    public TSP_MMAS(Graph G, int problemInitialNode, int problemDestinationNode, int parametersSet) throws Exception{
        /* Set enviroment */
        Enviroment Env = new Enviroment( G /* Graph */ , true /* random pheromone trail */);
        /* Set parameters */
        this.setParam(TSP_MMAS.initialNode  , problemInitialNode);
        this.setParam(TSP_MMAS.destinationNode , problemDestinationNode);
        if(parametersSet<0 && parametersSet>3){
            throw new Exception("parametersSet invalid, must be either 0, 1, 2 or 3");
        }
        switch(parametersSet){
            case 0:
                this.setParam(TSP_MMAS.maxNumIterations , 10000);
                this.setParam(TSP_MMAS.pheromonesEvaporationRate, 0.25);
                this.setParam(TSP_MMAS.alpha, 0.5);
                this.setParam(TSP_MMAS.beta, 1);
                this.setParam(TSP_MMAS.tau_max, 1.75);
                this.setParam(TSP_MMAS.tau_min, 0.75);
                Env.setPheromones(this.Parameters.get(TSP_MMAS.tau_max));
                Env.setAnts(10);
                break;
            case 1:
                this.setParam(TSP_MMAS.maxNumIterations , 100);
                this.setParam(TSP_MMAS.pheromonesEvaporationRate, 0.2);
                this.setParam(TSP_MMAS.alpha, 0.25);
                this.setParam(TSP_MMAS.beta, 1.25);
                this.setParam(TSP_MMAS.tau_max, 1.75);
                this.setParam(TSP_MMAS.tau_min, 0.75);
                Env.setPheromones(this.Parameters.get(TSP_MMAS.tau_max));
                Env.setAnts(1000);
                break;
            case 2:
                this.setParam(TSP_MMAS.maxNumIterations , 10000);
                this.setParam(TSP_MMAS.pheromonesEvaporationRate, 0.15);
                this.setParam(TSP_MMAS.alpha, 0.15);
                this.setParam(TSP_MMAS.beta, 1.75);
                this.setParam(TSP_MMAS.tau_max, 1.75);
                this.setParam(TSP_MMAS.tau_min, 0.75);
                Env.setPheromones(this.Parameters.get(TSP_MMAS.tau_max));
                Env.setAnts(1000);
                break;
            case 3:
                this.setParam(TSP_MMAS.maxNumIterations , 250);
                this.setParam(TSP_MMAS.pheromonesEvaporationRate, 0.1);
                this.setParam(TSP_MMAS.alpha, 0.1);
                this.setParam(TSP_MMAS.beta, 2);
                this.setParam(TSP_MMAS.tau_max, 1.75);
                this.setParam(TSP_MMAS.tau_min, 0.75);
                Env.setPheromones(this.Parameters.get(TSP_MMAS.tau_max));
                Env.setAnts(25);
                break;
        }
        this.setE(Env);
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
            if(adjacents.indexOf(this.Parameters.get(MMAS.initialNode).intValue()) >= 0 &&
                currentSolution.size() == this.Graph.getM().getColumns()){
                //There is a connection with the inital node, add it as the next node to visit
                posibleNodes.add(this.Parameters.get(MMAS.initialNode).intValue());
            }
        }
        //System.out.println("candidate("+ i +") = "+posibleNodes.toString());
        return posibleNodes;
    }
}
