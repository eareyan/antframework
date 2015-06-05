package antframework.experiments;

import antframework.common.*;
import antframework.experiments.common.*;
import java.util.*;
import antframework.test.TSP.*;

/**
 *
 * @author kronenthaler
 */
public class Knight {
	static node map[];
	static int[] stepsx = {1, 1,-1,-1,2, 2,-2,-2};
	static int[] stepsy = {2,-2, 2,-2,1,-1, 1,-1};
	static int startPos;
	static int SIZE;
	static int marks[];

    public static double calError(double error1,double error2){
        //return (error1 - error2) * (error1 - error2);//Error cuadratico
        return Math.abs(error1 - error2);//Error absoluto
    }

    public static void main(String[] args) throws Exception{
                Files Graphs = new Files(Files.KGFilter);
                System.out.println(Graphs+"---");
		int iterations=10;
		int algorithms=7;
                int setparametros=2;

		long times[] = new long[algorithms];//numero de algoritmos
		double error[] = new double[algorithms];//numero de algoritmos
		double results[] = new double[algorithms]; //numero de algoritmos
                for(int xy=6;xy<7/*Graphs.size()*/;xy++){
                    
                    Graph g = new Graph(Graphs.getFullFile(xy));
                    Matrix m = g.getM();
                    int n = (int)Math.sqrt(m.getColumns());
                    System.out.println("file: "+Graphs.getFile(xy));
                    String result = "";
                    for(int k=0;k<iterations;k++){
                            Arrays.fill(times, 0l);
                            Arrays.fill(error, 0f);
                            Arrays.fill(results, 0f);
                            long begin = System.currentTimeMillis();
                            map = new node[m.getColumns()];
                            marks = new int[m.getColumns()];

                            for(int i=0;i<map.length;i++)
                                    map[i]=new node(i,0);

                            for(int i=0;i<map.length;i++){
                                    for(int j=0;j<map.length;j++){
                                            if((int)m.position(i, j) < Integer.MAX_VALUE){
                                                    map[i].count++;
                                                    map[i].aux.add(map[j]);
                                            }
                                    }
                                    map[i].distanceToCorner = manhattan(i,n,n);
                                    map[i].visited = 0;
                                    map[i].neighbours = map[i].aux.toArray(new node[0]);
                                    map[i].aux.clear();
                            }

                            SIZE = m.getColumns();
                            startPos = ((n>>1)*n) + (n>>1);
                            //System.out.println("startPos = "+startPos);

                            addMove(startPos,SIZE);
                            if(n%2==0 && knight(startPos, SIZE-1)) results[0] = 1;
                            else results[0] = 0;
                            long end = System.currentTimeMillis();

                            times[0] = (end-begin);
                            error[0] = 0;

                            //hacer para lo mismo para los otros algoritmos.
                            //AS
                            TSP_AS problemAS = new TSP_AS(g, startPos, startPos, setparametros);
                            begin = System.currentTimeMillis();
                            problemAS.solve();
                            end = System.currentTimeMillis();

                            times[1] += (end - begin);
                            results[1] = problemAS.f(problemAS.getBestSolution());
                            if (results[1] == Double.MAX_VALUE) {
                                    results[1] = 0;
                            }
                            error[1] += Knight.calError(results[0] , results[1]);
                            //end AS*/
                            //times[1] = 0;
                            //error[1] = 1;
                            //EAS
                            TSP_EAS problemEAS = new TSP_EAS(g, startPos, startPos, setparametros);
                            begin = System.currentTimeMillis();
                            problemEAS.solve();
                            end = System.currentTimeMillis();

                            times[2] += (end - begin);
                            results[2] = problemEAS.f(problemEAS.getBestSolution());
                            if (results[2] == Double.MAX_VALUE) {
                                    results[2] = 0;
                            }
                            error[2] += TSP.calError(results[0] , results[2]);
                            //end EAS*/
                            //times[2] = 0;
                            //error[2] = 1;
                            //RAS
                            TSP_RAS problemRAS = new TSP_RAS(g, startPos, startPos, setparametros);
                            begin = System.currentTimeMillis();
                            problemRAS.solve();
                            end = System.currentTimeMillis();

                            times[3] += (end - begin);
                            results[3] = problemRAS.f(problemRAS.getBestSolution());
                            if (results[3] == Double.MAX_VALUE) {
                                    results[3] = 0;
                            }
                            error[3] += TSP.calError(results[0] , results[3]);
                            //end RAS
                            //ACS
                            TSP_ACS problemACS = new TSP_ACS(g, startPos, startPos, setparametros);
                            begin = System.currentTimeMillis();
                            problemACS.solve();
                            end = System.currentTimeMillis();

                            times[4] += (end - begin);
                            results[4] = problemACS.f(problemACS.getBestSolution());
                            if (results[4] == Double.MAX_VALUE) {
                                    results[4] = 0;
                            }
                            error[4] += TSP.calError(results[0] , results[4]);
                            //end ACS
                            //ANTQ
                            TSP_ANTQ problemANTQ = new TSP_ANTQ(g, startPos, startPos, setparametros);
                            begin = System.currentTimeMillis();
                            problemANTQ.solve();
                            end = System.currentTimeMillis();

                            times[5] += (end - begin);
                            results[5] = problemANTQ.f(problemANTQ.getBestSolution());
                            if (results[5] == Double.MAX_VALUE) {
                                    results[5] = 0;
                            }
                            error[5] += TSP.calError(results[0] , results[5]);
                            //end ANTQ
                            //MMAS
                            TSP_MMAS problemMMAS = new TSP_MMAS(g, startPos, startPos, setparametros);
                            begin = System.currentTimeMillis();
                            problemMMAS.solve();
                            end = System.currentTimeMillis();

                            times[6] += (end - begin);
                            results[6] = problemMMAS.f(problemMMAS.getBestSolution());
                            if (results[6] == Double.MAX_VALUE) {
                                    results[6] = 0;
                            }
                            error[6] += TSP.calError(results[0] , results[6]);
                            //end MMAS
                            //imprimir los resultados por corrida.
                            for(int a=0;a<algorithms;a++){
                                    System.out.printf("%f\t%f\t",times[a]/1.0, error[a]);
                                    result = result + (times[a]/1.0) + "\t" + error[a] + "\t";
                            }
                            result = result + "\n";
                            System.out.println();
                    }
                    Files.writeToFile("results/KG/" + setparametros + "/" + Graphs.getFile(xy), result);
                }
		System.out.println("---");
	}

	static boolean knight(int current, int steps){
		//marks[current] = steps;
		if(steps == 0){
			for(int j=0;j<map[current].neighbours.length;j++){//int next : neighbours[pos].neighbours)
				int next = map[current].neighbours[j].index;
				if(next == startPos){
					/*for(int i=0;i<map.length;i++){
						if(i%(int)Math.sqrt(SIZE)==0)System.err.println();
						System.err.printf("%4d ",map[i].visited);
					}
					System.err.println();//*/

					return true;
				}
			}
			return false;
		}
		Arrays.sort(map[current].neighbours);
		
		for(int i=0,n=map[current].neighbours.length;i<n;i++){
			int next = map[current].neighbours[i].index;
			
			if(addMove(next,steps)){
				if(knight(next, steps-1)) return true;
				removeMove(next);
			}
		}

		return false;
	}

	static boolean addMove(int pos,int move){
		if(map[pos].visited == 0){
			map[pos].visited = move;
			for(int i=0;i<map[pos].neighbours.length;i++)
				map[map[pos].neighbours[i].index].count--;
			
			return true;
		}
		return false;
	}

	static void removeMove(int pos){
		map[pos].visited = 0;

		for(int i=0;i<map[pos].neighbours.length;i++)//int next : neighbours[pos].neighbours)
			map[map[pos].neighbours[i].index].count++;
	}

	static int manhattan(int index,int N, int M){
		int x=index/N;
		int y=index%M;
		int min=x+y;			// 0,0
		int aux=Math.abs((N-1)-x+y);// N,0
		if(aux<min)min=aux;
		aux=Math.abs((M-1)-y+x);				// 0,M
		if(aux<min)min=aux;
		aux=Math.abs((M-1)-y+(N-1)-x);		// N,M
		if(aux<min)min=aux;
		return min;
	}

	static class node implements Comparable<node>{
		int visited; //id
		int index; //pos
		int count;
		int distanceToCorner;
		ArrayList<node> aux = new ArrayList<node>();
		node[] neighbours;
		
		node(int i,int c){
			index = i;
			count = c;
			distanceToCorner = Integer.MAX_VALUE;
			visited = 0;
		}

		public int compareTo(node o) {
			if(count == o.count)
				return distanceToCorner - o.distanceToCorner;
			return count - o.count;
		}
	}
}