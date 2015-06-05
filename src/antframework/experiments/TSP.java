package antframework.experiments;

import java.util.*;
import antframework.common.*;
import antframework.test.TSP.*;
import antframework.experiments.common.*;
import net.sf.libai.genetics.*;
import net.sf.libai.genetics.chromosomes.*;

/**
 *
 * @author kronenthaler
 */
public class TSP implements Fitness{
	
	public static double MIN = Double.MAX_VALUE;
	public static int visited[];
	public static Matrix m;

        public static double calError(double error1,double error2){
            //return (error1 - error2) * (error1 - error2);//Error cuadratico
            return Math.abs(error1 - error2);//Error absoluto
        }

	public static void main(String[] args) throws Exception{
		int iterations = 3;
		int setparametros = 2;

		String algorithms[] = new String[]{"Backtraking","Genetic","AS","EAS","RAS","ACS","ANTQ","MMAS"};

		String resultsDir = "results/TSP/"; //rutas relativas por cristo!

		/* Buscar todos los grafos */
                Files Graphs = new Files("graph/TSP/",Files.TSPFilter);
                System.out.println(Graphs+"---");



		long times[] = new long[algorithms.length];//numero de algoritmos
		double error[] = new double[algorithms.length];//numero de algoritmos
		double results[] = new double[algorithms.length]; //numero de algoritmos
		double time_avg[] = new double[algorithms.length];
		double error_avg[] = new double[algorithms.length];

                long begin,end;
                

		for (int xy = 4; xy < Graphs.size(); xy++) {
			Graph G = new Graph(Graphs.getFullFile(xy));
			System.out.println("file: " + G.getFileSource());
			for (int i = 0; i < algorithms.length; i++) {
				System.out.printf("\t\t" + algorithms[i] + "\t");
			}
			Arrays.fill(time_avg, 0l);
			Arrays.fill(error_avg, 0f);
			System.out.println();
			m = G.getM();
			String result = "";
                        //buscar cache
                        Files.cache cache = Files.readFromFile(resultsDir + "backtracking/" +Graphs.getFile(xy));
                        if(cache.time==0 && cache.result == 0){
                            System.out.println("No Ejecutar este grafo...");
                            continue;
                        }else{
                            //System.out.println(cache.time);
                            //System.out.println(cache.result);
                        }
			for (int k = 0; k < iterations; k++) {
				Arrays.fill(times, 0l);
				Arrays.fill(error, 0f);
				Arrays.fill(results, 0f);

                                //Datos del backtracking vienen por cache
                                times[0] = cache.time;
                                results[0] = cache.result;
				//System.out.println("times[0] = "+times[0]+"\tresults[0] = "+ results[0]);
				//backtracking
				//MIN = Double.MAX_VALUE;
				visited = new int[G.getM().getColumns()];
				/*/begin = System.currentTimeMillis();
				tsp(0,0,visited.length,0.0);
                                end = System.currentTimeMillis();
				//System.err.println("MIN: "+MIN);
				times[0] += (end - begin);
				results[0] = MIN;
				//System.out.println("D. = "+ length);
                                System.out.println("times[0] = "+times[0]+"\tresults[0] = "+ results[0]);
				error[0] += TSP.calError(results[0] , MIN);
				//end backtracking*/
				
				//ag
				begin = System.currentTimeMillis();
				Engine eng = new Engine(IntegerChromosome.class, 500, visited.length-1, 0.4, 0.05, new TSP());
				IntegerChromosome chr = (IntegerChromosome)eng.evolve(10000);
				end = System.currentTimeMillis();
				times[1] += (end - begin);
				results[1] = new TSP().fitness(chr);
				//System.out.println("D. = "+ length);
                                //System.out.println("times[1] = "+times[1]+"\tresults[1] = "+ results[1]);
				error[1] += TSP.calError(results[0] , results[1]);
				//System.err.println(new TSP().fitness(chr)+" => "+ chr.toString());
				//end ag

                                //AS
                                TSP_AS problemAS = new TSP_AS(G, 0, 0, setparametros);
                                begin = System.currentTimeMillis();
                                problemAS.solve();
                                end = System.currentTimeMillis();

                                times[2] += (end - begin);
                                results[2] = problemAS.f(problemAS.getBestSolution());
                                //System.out.println("times[2] = "+times[2]+"\tresults[2] = "+ results[2]);
                                if (results[2] == Double.MAX_VALUE) {
                                        results[2] = 0;
                                }
                                error[2] += TSP.calError(results[0] , results[2]);
                                //end AS
                                //EAS
                                TSP_EAS problemEAS = new TSP_EAS(G, 0, 0, setparametros);
                                begin = System.currentTimeMillis();
                                problemEAS.solve();
                                end = System.currentTimeMillis();

                                times[3] += (end - begin);
                                results[3] = problemEAS.f(problemEAS.getBestSolution());
                                //System.out.println("times[3] = "+times[3]+"\tresults[3] = "+ results[3]);
                                if (results[3] == Double.MAX_VALUE) {
                                        results[3] = 0;
                                }
                                error[3] += TSP.calError(results[0] , results[3]);
                                //end EAS
                                //RAS
                                TSP_RAS problemRAS = new TSP_RAS(G, 0, 0, setparametros);
                                begin = System.currentTimeMillis();
                                problemRAS.solve();
                                end = System.currentTimeMillis();

                                times[4] += (end - begin);
                                results[4] = problemRAS.f(problemRAS.getBestSolution());
                                //System.out.println("times[4] = "+times[4]+"\tresults[4] = "+ results[4]);
                                if (results[4] == Double.MAX_VALUE) {
                                        results[4] = 0;
                                }
                                error[4] += TSP.calError(results[0] , results[4]);
                                //end RAS
                                //ACS
                                TSP_ACS problemACS = new TSP_ACS(G, 0, 0, setparametros);
                                begin = System.currentTimeMillis();
                                problemACS.solve();
                                end = System.currentTimeMillis();

                                times[5] += (end - begin);
                                results[5] = problemACS.f(problemACS.getBestSolution());
                                //System.out.println("times[5] = "+times[5]+"\tresults[5] = "+ results[5]);
                                if (results[5] == Double.MAX_VALUE) {
                                        results[5] = 0;
                                }
                                error[5] += TSP.calError(results[0] , results[5]);
                                //end ACS
                                //ANTQ
                                TSP_ANTQ problemANTQ = new TSP_ANTQ(G, 0, 0, setparametros);
                                begin = System.currentTimeMillis();
                                problemANTQ.solve();
                                end = System.currentTimeMillis();

                                times[6] += (end - begin);
                                results[6] = problemANTQ.f(problemANTQ.getBestSolution());
                                //System.out.println("times[6] = "+times[6]+"\tresults[6] = "+ results[6]);
                                if (results[6] == Double.MAX_VALUE) {
                                        results[6] = 0;
                                }
                                error[6] += TSP.calError(results[0] , results[6]);
                                //end ANTQ
                                //MMAS
                                TSP_MMAS problemMMAS = new TSP_MMAS(G, 0, 0, setparametros);
                                begin = System.currentTimeMillis();
                                problemMMAS.solve();
                                end = System.currentTimeMillis();

                                times[7] += (end - begin);
                                results[7] = problemMMAS.f(problemMMAS.getBestSolution());
                                //System.out.println("times[7] = "+times[7]+"\tresults[7] = "+ results[7]);
                                if (results[7] == Double.MAX_VALUE) {
                                        results[7] = 0;
                                }
                                error[7] += TSP.calError(results[0] , results[7]);
                                //end MMAS
				for (int a = 0; a < algorithms.length; a++) {
					int total = 1;
					System.out.printf("%f\t%f\t", times[a] / (double) (total), error[a] / (double) (total));
					//System.out.printf("%f",(times[a]/(double)(total)));
					time_avg[a] += times[a] / (double) (total);
					error_avg[a] += error[a] / (double) (total);
                                        result = result + (times[a] / (double) (total)) + "\t" + error[a] / (double) (total) + "\t";
				}
                                result = result + "\n";
				System.out.println();
			}
                        Files.writeToFile(resultsDir + setparametros + "/" + Graphs.getFile(xy), result);
			/*escribir los resultados de la corrida
			String text = "";
			for (int a = 0; a < algorithms.length; a++) {
				System.out.printf("Average = %f ", time_avg[a] / iterations);
				System.out.printf("Error = %f \n", error_avg[a] / iterations);
				text = text + "" + time_avg[a] / iterations + "\t" + error_avg[a] / iterations + "\t";
			}
                        Files.writeToFile(resultsDir + setparametros + "/" + Graphs.getFile(xy), text);*/
			System.out.println("----");
		}
	}

	public static void tsp(int src, int current, int steps, double acum){
		visited[current]=steps;
		if(steps == 1){
			if(m.position(current,src) < Double.MAX_VALUE){
				/*if(MIN > acum+m.position(current,src)){
					System.out.print("solution: ["+(acum+m.position(current,src))+"] = {");
					for(int i=0;i<visited.length;i++)
						System.out.print((visited.length-visited[i])+",");
					System.out.println("}");
				}*/
				MIN = Math.min(MIN, acum+m.position(current,src));
			}
			visited[current] = 0;
			return;
		}
		
		for(int i=0,n=m.getColumns();i<n;i++){
			if(visited[i] != 0) continue;
			double c = m.position(current, i);
			if(c < Double.MAX_VALUE){
				if(acum+c < MIN){
					tsp(src, i, steps-1, acum+c);
				}
			}
		}
		visited[current] = 0;
	}

	//Implementacion de los geneticos
	public double fitness(Chromosome a) {
		IntegerChromosome c = (IntegerChromosome)a;
		//recorrer el grafo desde la posicion 0, acumulando las costos de los arcos.
		int[] steps = c.getGenes();
		double acum= 0;
		int src = 0;

		//TODO: establecer como voy a hacer para que se evalue y que es lo que representa cada posicion del chromosoma
		for(int i=0;i<steps.length;i++){
			if(m.position(src, steps[i]+1) == Double.MAX_VALUE)
				return theWorst();
			else
				acum += m.position(src, steps[i]+1);
			src = steps[i]+1;
		}
		if(m.position(steps[steps.length-1]+1,0) == Double.MAX_VALUE)
			return theWorst();
		else
			acum += m.position(steps[steps.length-1]+1,0);
		return acum;
	}

	public boolean isBetter(double a, double b) {
		return a < b;
	}

	public double theWorst() {
		return Double.MAX_VALUE;
	}
}
