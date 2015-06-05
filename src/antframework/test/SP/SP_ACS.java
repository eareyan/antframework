
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package antframework.test.SP;
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
public class SP_ACS extends antframework.algorithms.AntColonySystem {

    public SP_ACS(){}

    public SP_ACS(Enviroment E) {
        super(E);
    }
    public SP_ACS(String graphLocation, int problemInitialNode, int problemDestinationNode, int parametersSet) throws Exception{
        /* Graph loaded from file*/
        //Graph G = new Graph("/Users/enriqueareyan/Documents/Universidad/Tesis/Framework/Graphs/SP/Problem1.txt");
        Graph G = new Graph(graphLocation);
        /* Set enviroment */
        Enviroment Env = new Enviroment(G /* Graph */ , true /* random pheromone trail */);
        this.setParam(SP_ACS.initialNode  , problemInitialNode);
        this.setParam(SP_ACS.destinationNode , problemDestinationNode);
        switch(parametersSet){
            case 0:
                this.setParam(SP_ACS.maxNumIterations , 5);
                this.setParam(SP_ACS.beta , 0.25);
                this.setParam(SP_ACS.maxCandidates , Integer.MAX_VALUE);
                //this.setParam(SP_ACS.maxCandidates , 15);
                this.setParam(SP_ACS.r_0 , 0.5);
                this.setParam(SP_ACS.ro_1 , 0.1);
                this.setParam(SP_ACS.ro_2 , 0.9);
                this.setParam(SP_ACS.tau_0 , 0.1);
                Env.setAnts(5);
                break;
            case 1:
                this.setParam(SP_ACS.maxNumIterations , 50);
                this.setParam(SP_ACS.beta , 1);
                //this.setParam(SP_ACS.maxCandidates , Integer.MAX_VALUE);
                this.setParam(SP_ACS.maxCandidates , 50);
                this.setParam(SP_ACS.r_0 , 0.4);
                this.setParam(SP_ACS.ro_1 , 0.1);
                this.setParam(SP_ACS.ro_2 , 0.9);
                this.setParam(SP_ACS.tau_0 , 0.1);
                Env.setAnts(5);
                break;
            case 2:
                this.setParam(SP_ACS.maxNumIterations , 100);
                this.setParam(SP_ACS.beta , 0.8);
                this.setParam(SP_ACS.maxCandidates , Integer.MAX_VALUE);
                this.setParam(SP_ACS.r_0 , 0.8);
                this.setParam(SP_ACS.ro_1 , 0.25);
                this.setParam(SP_ACS.ro_2 , 0.9);
                this.setParam(SP_ACS.tau_0 , 0.1);
                Env.setAnts(10);
                break;
            case 3:
                this.setParam(SP_ACS.maxNumIterations , 200);
                this.setParam(SP_ACS.beta , 0.5);
                this.setParam(SP_ACS.maxCandidates , 50);
                this.setParam(SP_ACS.r_0 , 0.5);
                this.setParam(SP_ACS.ro_1 , 0.1);
                this.setParam(SP_ACS.ro_2 , 0.9);
                this.setParam(SP_ACS.tau_0 , 0.1);
                Env.setAnts(15);
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
        return adjacents;
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
        int numberSolutionNodes = Solution.size();
        if(numberSolutionNodes != 0 && Solution.elementAt((numberSolutionNodes - 1)) != this.Parameters.get(AntColonySystem.destinationNode).intValue()){
            return Double.MAX_VALUE;
        }else{
            return super.f(Solution);
        }
    }
}
