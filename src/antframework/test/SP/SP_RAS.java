/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antframework.test.SP;
import antframework.common.Graph;
import antframework.common.Enviroment;
import antframework.algorithms.*;
import java.util.Vector;

/**
 *
 * @author enriqueareyan
 */
public class SP_RAS extends AntSystemRank {
    public SP_RAS(){}
    public SP_RAS(Enviroment E) {
        super(E);
    }
    public SP_RAS(String graphLocation, int problemInitialNode, int problemDestinationNode, int parametersSet) throws Exception{
        /* Graph loaded from file*/
        //Graph G = new Graph("/Users/enriqueareyan/Documents/Universidad/Tesis/Framework/Graphs/SP/Problem1.txt");
        Graph G = new Graph(graphLocation);
        /* Set enviroment */
        Enviroment Env = new Enviroment( 20 /*number of ants*/ , G /* Graph */ , true /* random pheromone trail */ );
        /* Set parameters */
        this.setParam(SP_RAS.initialNode  , problemInitialNode);
        this.setParam(SP_RAS.destinationNode , problemDestinationNode);
        if(parametersSet<0 && parametersSet>3){
            throw new Exception("parametersSet invalid, must be either 0, 1, 2 or 3");
        }
        switch(parametersSet){
            case 0:
                this.setParam(SP_RAS.maxNumIterations , 5);
                this.setParam(SP_RAS.pheromonesEvaporationRate, 0.8);
                this.setParam(SP_RAS.alpha, 0.5);
                this.setParam(SP_RAS.beta, 0.5);
                this.setParam(SP_RAS.epsilon, 2);
                Env.setAnts(5);
                break;
            case 1:
                this.setParam(SP_RAS.maxNumIterations , 50);
                this.setParam(SP_RAS.pheromonesEvaporationRate, 0.75);
                this.setParam(SP_RAS.alpha, 0.5);
                this.setParam(SP_RAS.beta, 0.75);
                this.setParam(SP_RAS.epsilon, 5);
                Env.setAnts(5);
                break;
            case 2:
                this.setParam(SP_RAS.maxNumIterations , 100);
                this.setParam(SP_RAS.pheromonesEvaporationRate, 0.70);
                this.setParam(SP_RAS.alpha, 0.5);
                this.setParam(SP_RAS.beta, 0.5);
                this.setParam(SP_RAS.epsilon, 7);
                Env.setAnts(10);
                break;
            case 3:
                this.setParam(SP_RAS.maxNumIterations , 200);
                this.setParam(SP_RAS.pheromonesEvaporationRate, 0.65);
                this.setParam(SP_RAS.alpha, 0.5);
                this.setParam(SP_RAS.beta, 0.5);
                this.setParam(SP_RAS.epsilon, 10);
                Env.setAnts(15);
                break;
        }
        /* set enviroment*/
        this.setE(Env);
    }
    public double heuristicInfo(double number){
        return 1/number;
    }
    @Override
    public double f(Vector<Integer> Solution){
        int numberSolutionNodes = Solution.size();
        if(numberSolutionNodes != 0 && Solution.elementAt((numberSolutionNodes - 1)) != this.Parameters.get(antframework.algorithms.ElitistAntSystem.destinationNode).intValue()){
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
        return adjacents;
    }
}