package antframework.experiments;

import antframework.common.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author kronenthaler
 */
public class GraphGenerator {

	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
			if(args.length == 0){
				usage();
				return;
			}

			for(int i=0;i<args.length;i++){
				if(args[i].equals("-cg")){
					int n = Integer.parseInt(args[++i]);
					int w = Integer.parseInt(args[++i]);
					Graph g = generateCompleteGraph(n,w);
					g.save("graph/cg"+n+"x"+n+"_"+w+".graph");
					
				}else if(args[i].equals("-g")){
					int n = Integer.parseInt(args[++i]);
					int k = Integer.parseInt(args[++i]);
					double p = Double.parseDouble(args[++i]);
					for(int j=0;j<k;j++){
						Graph g = generateGraphWithProbability(n,p);
						g.save("graph/g"+n+"x"+n+"_"+p+"["+j+"].graph");
					}

				}else if(args[i].equals("-kg")){
					int n = Integer.parseInt(args[++i]);
					Graph g = generateGraphKnightMoves(n);
					g.save("graph/kg"+n+"x"+n+".graph");
				
				}else{
					invalidOption(args[i]);
					usage();
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

	private static void invalidOption(String opt){
		System.out.println("Invalid Option: "+opt);
	}

	private static void usage(){
		System.out.println("USAGE: [-cg N W] [-g N K p] [-kg N]");
		System.out.println("N: size of the graph");
		System.out.println("W: {0,1} use same weights or not ");
		System.out.println("K: how many instances");
		System.out.println("p: probability of create a link");
	}

	private static Graph generateCompleteGraph(int n, int w){
		Graph g = new Graph(n, n, new double[n*n]);
		Matrix m = g.getM();
		Random r = new Random();

		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(i==j) continue;
				if(w==0) m.position(i, j, 1);
				else m.position(i, j, r.nextInt(n)+1);
			}
		}

		return g;
	}

	private static Graph generateGraphWithProbability(int n, double p){
		Graph g = new Graph(n, n, new double[n*n]);
		Matrix m = g.getM();
		Random r = new Random();

		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(i==j) continue;
				if(r.nextDouble() > p)
					m.position(i, j, r.nextInt(n)+1);
			}
		}
		return g;
	}

	private static Graph generateGraphKnightMoves(int n){
		Graph g = new Graph(n*n, n*n, new double[n*n*n*n]);
		Matrix m = g.getM();
		int[] stepsx = {1, 1,-1,-1,2, 2,-2,-2};
		int[] stepsy = {2,-2, 2,-2,1,-1, 1,-1};

		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				for(int k=0;k<8;k++){
					int ni = i+stepsx[k];
					int nj = j+stepsy[k];
					if(ni>=0 && ni<n && nj>=0 && nj<n){
						m.position((i*n)+j,(ni*n)+nj,1);
					}
				}
			}
		}

		return g;
	}

}
