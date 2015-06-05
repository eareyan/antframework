package antframework.experiments;

import java.util.*;
import antframework.common.*;
import antframework.test.SP.*;
import antframework.experiments.common.*;

/**
 *	Implementation of the Dijkstra's shortest path algorithm.
 *	This class loads the graph especified by the path as parameter and runs the
 *	dijkstra's algorithm for each node trying to reach all the others. Outputs
 *	over the stdout the time taked to find the shortest path and the length of
 *  the shortest path.
 *	@author kronenthaler
 */
public class Dijkstra {

	/**
	 * @param args the command line arguments
	 */
        public static double calError(double error1,double error2){
            //return (error1 - error2) * (error1 - error2);//Error cuadratico
            return Math.abs(error1 - error2);//Error absoluto
        }
	public static void main(String[] args) throws Exception {
		int iterations = 1;
		int setparametros = 3;

		String algorithms[] = new String[]{"Dijkstra","AS","EAS","RAS","ACS","ANTQ","MMAS"};

		String resultsDir = "results/SP/"; //rutas relativas por cristo!
                
                Files Graphs = new Files("graph/SP/",Files.SPFilter);
                System.out.println(Graphs);
                long times[] = new long[algorithms.length];//numero de algoritmos
		double error[] = new double[algorithms.length];//numero de algoritmos
		double results[] = new double[algorithms.length]; //numero de algoritmos
		double time_avg[] = new double[algorithms.length];
		double error_avg[] = new double[algorithms.length];

		for (int xy = 14; xy < 15/*Graphs.size()*/; xy++) { //que significa ese 34??? para ejecutar el archivo en posicion 34 directamente... para pruebas basicamente
			String graphLocation = Graphs.getFullFile(xy);
			Graph g = new Graph(graphLocation);
			System.out.println("file: " + graphLocation);
			for (int i = 0; i < algorithms.length; i++) {
				System.out.printf("\t\t" + algorithms[i] + "\t");
			}
			Arrays.fill(time_avg, 0l);
			Arrays.fill(error_avg, 0f);
                        String result = "";
			System.out.println();
			for (int k = 0; k < iterations; k++) {
				Arrays.fill(times, 0l);
				Arrays.fill(error, 0f);
				Arrays.fill(results, 0f);
                                int casosSinSol = 0;

				for (int i = 0, n = g.getM().getRows(); i < n; i++) {
					//System.out.println("nodo = " + i);
					for (int j = 0, m = g.getM().getColumns(); j < m; j++) {
						if (i != j) {
							//******Dijsktra
							long begin = System.currentTimeMillis();
							double length = dijkstra(g, i, j);
							long end = System.currentTimeMillis();

							if (length == Double.MAX_VALUE) {
								//No consiguio solucion
                                                                casosSinSol++;
								continue;
							}

							times[0] += (end - begin);
							results[0] = length;
							error[0] += calError(results[0] ,length);//siempre se compara contra el result[0]

                                                        //******AS
							SP_AS problemAS = new SP_AS(graphLocation, i, j, setparametros);
							begin = System.currentTimeMillis();
							problemAS.solve();
							end = System.currentTimeMillis();

							times[1] += (end - begin);
							results[1] = problemAS.f(problemAS.getBestSolution());
							if (results[1] == Double.MAX_VALUE) {
								results[1] = 0;
							}
							error[1] += calError(results[0] , results[1]);

							//******EAS
							SP_EAS problemEAS = new SP_EAS(graphLocation, i, j, setparametros);
							begin = System.currentTimeMillis();
							problemEAS.solve();
							end = System.currentTimeMillis();

							times[2] += (end - begin);
							results[2] = problemEAS.f(problemEAS.getBestSolution());
							if (results[2] == Double.MAX_VALUE) {
								results[2] = 0;
							}
							error[2] += calError(results[0] , results[2]);

                                                        //******RAS
							SP_RAS problemRAS = new SP_RAS(graphLocation, i, j, setparametros);
							begin = System.currentTimeMillis();
							problemRAS.solve();
							end = System.currentTimeMillis();

							times[3] += (end - begin);
							results[3] = problemRAS.f(problemRAS.getBestSolution());
							if (results[3] == Double.MAX_VALUE) {
								results[3] = 0;
							}
							error[3] += calError(results[0] , results[3]);

                                                        //******ACS
							SP_ACS problemACS = new SP_ACS(graphLocation, i, j, setparametros);
							begin = System.currentTimeMillis();
							problemACS.solve();
							end = System.currentTimeMillis();

							times[4] += (end - begin);
							results[4] = problemACS.f(problemACS.getBestSolution());
							if (results[4] == Double.MAX_VALUE) {
								results[4] = 0;
							}
							error[4] += calError(results[0] , results[4]);

                                                        //******ANTQ
							SP_ANTQ problemANTQ = new SP_ANTQ(graphLocation, i, j, setparametros);
							begin = System.currentTimeMillis();
							problemANTQ.solve();
							end = System.currentTimeMillis();

							times[5] += (end - begin);
							results[5] = problemANTQ.f(problemANTQ.getBestSolution());
							if (results[5] == Double.MAX_VALUE) {
								results[5] = 0;
							}
							error[5] += calError(results[0] , results[5]);

                                                        //******MMAS
							SP_MMAS problemMMAS = new SP_MMAS(graphLocation, i, j, setparametros);
							begin = System.currentTimeMillis();
							problemMMAS.solve();
							end = System.currentTimeMillis();

							times[6] += (end - begin);
							results[6] = problemMMAS.f(problemMMAS.getBestSolution());
							if (results[6] == Double.MAX_VALUE) {
								results[6] = 0;
							}
							error[6] += calError(results[0] , results[6]);
						}
					}
				}
				for (int a = 0; a < algorithms.length; a++) {
					int total = ((g.getM().getRows() * g.getM().getColumns()) - Math.min(g.getM().getRows(), g.getM().getColumns())) - casosSinSol;
					System.out.printf("%f\t%f\t", times[a] / (double) (total), error[a] / (double) (total));
                                        result = result + (times[a] / (double) (total)) + "\t" + error[a] / (double) (total) + "\t";
					//System.out.printf("%f",(times[a]/(double)(total)));
					time_avg[a] += times[a] / (double) (total);
					error_avg[a] += error[a] / (double) (total);
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

	public static double dijkstra(Graph g, int src, int dst) { //nodo inicial, numero de nodos
		double D[] = new double[g.getM().getRows()];			/* distancias minima desde s al nodo i */
		int padre[] = new int[g.getM().getRows()];			/* ruta hacia el nodo i desde s */
		boolean visited[] = new boolean[g.getM().getRows()];	/* true al tener la menor ruta al nodo i */
		Matrix C = g.getM();
		int n = visited.length;

		PriorityQueue<Node> pq = new PriorityQueue<Node>(); // maxheap => -max=min
		Node nodotmp;
		int i, j;
		// inicializacion
		Arrays.fill(D, Double.MAX_VALUE);
		Arrays.fill(padre, -1);

		// inicializacion del nodo inicial
		pq.add(new Node(src, D[src] = 0, 0));

		// calculamos las distancias
		while (!pq.isEmpty()) {
			nodotmp = pq.poll();
			i = nodotmp.getIndex();
			if (!visited[i]) {
				visited[i] = true;
				for (j = 0; j < n; j++) {
					if(!visited[j] && (int) C.position(i, j) < Double.MAX_VALUE && D[i] + C.position(i, j) < D[j]) {
							D[j] = D[i] + C.position(i, j);
							padre[j] = i;
							pq.add(new Node(j, D[j], 0));
						}
					}
				}
			}
			return D[dst];
		}
	}
