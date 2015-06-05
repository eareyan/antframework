/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antframework.test.SP;
import antframework.common.Graph;
import antframework.common.Enviroment;
import java.util.Vector;
/**
 *
 * @author enriqueareyan
 */
public class SP_EAS extends antframework.algorithms.ElitistAntSystem{
    public SP_EAS(){}
    public SP_EAS(Enviroment E) {
        super(E);
    }
    public SP_EAS(String graphLocation, int problemInitialNode, int problemDestinationNode, int parametersSet) throws Exception{
        /* Graph loaded from file*/
        //Graph G = new Graph("/Users/enriqueareyan/Documents/Universidad/Tesis/Framework/Graphs/SP/Problem1.txt");
        Graph G = new Graph(graphLocation);
        /* Set enviroment */
        Enviroment Env = new Enviroment( G /* Graph */ , true /* random pheromone trail */ );
        /* Set parameters */
        this.setParam(SP_EAS.initialNode  , problemInitialNode);
        this.setParam(SP_EAS.destinationNode , problemDestinationNode);
        if(parametersSet<0 && parametersSet>3){
            throw new Exception("parametersSet invalid, must be either 0, 1, 2 or 3");
        }
        switch(parametersSet){
            case 0:
                this.setParam(SP_EAS.maxNumIterations , 5);
                this.setParam(SP_EAS.pheromonesEvaporationRate, 0.8);
                this.setParam(SP_EAS.alpha, 0.5);
                this.setParam(SP_EAS.beta, 0.5);
                this.setParam(SP_EAS.epsilon, 2);
                Env.setAnts(5);
                break;
            case 1:
                this.setParam(SP_EAS.maxNumIterations , 50);
                this.setParam(SP_EAS.pheromonesEvaporationRate, 0.75);
                this.setParam(SP_EAS.alpha, 0.5);
                this.setParam(SP_EAS.beta, 0.25);
                this.setParam(SP_EAS.epsilon, 2);
                Env.setAnts(5);
                break;
            case 2:
                this.setParam(SP_EAS.maxNumIterations , 100);
                this.setParam(SP_EAS.pheromonesEvaporationRate, 0.70);
                this.setParam(SP_EAS.alpha, 0.5);
                this.setParam(SP_EAS.beta, 0.25);
                this.setParam(SP_EAS.epsilon, 2);
                Env.setAnts(10);
                break;
            case 3:
                this.setParam(SP_EAS.maxNumIterations , 200);
                this.setParam(SP_EAS.pheromonesEvaporationRate, 0.65);
                this.setParam(SP_EAS.alpha, 0.5);
                this.setParam(SP_EAS.beta, 0.25);
                this.setParam(SP_EAS.epsilon, 2);
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
