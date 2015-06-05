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
public class SP_ANTQ extends antframework.algorithms.AntQ {

    public SP_ANTQ(){}

    public SP_ANTQ(String graphLocation, int problemInitialNode, int problemDestinationNode, int parametersSet) throws Exception{
        /* Graph loaded from file*/
        //Graph G = new Graph("/Users/enriqueareyan/Documents/Universidad/Tesis/Framework/Graphs/SP/Problem1.txt");
        Graph G = new Graph(graphLocation);
        /* Set enviroment */
        Enviroment Env = new Enviroment( G /* Graph */ , true /* random pheromone trail */);
        /* Set parameters */
        this.setParam(SP_ANTQ.initialNode  , problemInitialNode);
        this.setParam(SP_ANTQ.destinationNode , problemDestinationNode);
        switch(parametersSet){
            case 0:
                this.setParam(SP_ANTQ.maxNumIterations , 5);
                this.setParam(SP_ANTQ.alpha, 0.5);
                this.setParam(SP_ANTQ.beta , 0.5);
                this.setParam(SP_ANTQ.maxCandidates , Integer.MAX_VALUE);
                //this.setParam(SP_ACS.maxCandidates , 0);
                this.setParam(SP_ANTQ.r_0 , 0.5);
                this.setParam(SP_ANTQ.ro_1 , 0.1);
                this.setParam(SP_ANTQ.ro_2 , 0.9);
                this.setParam(SP_ANTQ.tau_0 , 0.1);
                this.setParam(SP_ANTQ.gamma , 0.5);
                Env.setAnts(5);
                break;
            case 1:
                this.setParam(SP_ANTQ.maxNumIterations , 50);
                this.setParam(SP_ANTQ.alpha, 0.5);
                this.setParam(SP_ANTQ.beta , 0.75);
                //this.setParam(SP_ANTQ.maxCandidates , Integer.MAX_VALUE);
                this.setParam(SP_ACS.maxCandidates , 40);
                this.setParam(SP_ANTQ.r_0 , 0.35);
                this.setParam(SP_ANTQ.ro_1 , 0.5);
                this.setParam(SP_ANTQ.ro_2 , 0.5);
                this.setParam(SP_ANTQ.tau_0 , 0.1);
                this.setParam(SP_ANTQ.gamma , 0.75);
                Env.setAnts(5);
                break;
            case 2:
                this.setParam(SP_ANTQ.maxNumIterations , 100);
                this.setParam(SP_ANTQ.alpha, 0.5);
                this.setParam(SP_ANTQ.beta , 0.75);
                //this.setParam(SP_ANTQ.maxCandidates , Integer.MAX_VALUE);
                this.setParam(SP_ACS.maxCandidates , 40);
                this.setParam(SP_ANTQ.r_0 , 0.35);
                this.setParam(SP_ANTQ.ro_1 , 0.5);
                this.setParam(SP_ANTQ.ro_2 , 0.5);
                this.setParam(SP_ANTQ.tau_0 , 0.1);
                this.setParam(SP_ANTQ.gamma , 0.75);
                Env.setAnts(10);
                break;
            case 3:
                this.setParam(SP_ANTQ.maxNumIterations , 200);
                this.setParam(SP_ANTQ.alpha, 0.5);
                this.setParam(SP_ANTQ.beta , 0.75);
                //this.setParam(SP_ANTQ.maxCandidates , Integer.MAX_VALUE);
                this.setParam(SP_ACS.maxCandidates , 40);
                this.setParam(SP_ANTQ.r_0 , 0.35);
                this.setParam(SP_ANTQ.ro_1 , 0.5);
                this.setParam(SP_ANTQ.ro_2 , 0.5);
                this.setParam(SP_ANTQ.tau_0 , 0.1);
                this.setParam(SP_ANTQ.gamma , 0.75);
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
        if(numberSolutionNodes != 0 && Solution.elementAt((numberSolutionNodes - 1)) != this.Parameters.get(AntSystem.destinationNode).intValue()){
            return Double.MAX_VALUE;
        }else{
            return super.f(Solution);
        }
    }
}
