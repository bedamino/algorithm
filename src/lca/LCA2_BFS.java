package lca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class LCA2_BFS {
	
	private static int N;
	private static int M;
	private static int NN;
	private static int LIMIT;
	private static List<Integer>[] input;
	private static int[] depth;
	private static int[][] sparse; //희소 배열
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("LCA2")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		
		input = new List[N+1];
		for(int i=1; i<=N; i++) {
			input[i] = new ArrayList<Integer>();
		}
		
		NN = 1;
		while(N > NN) {
			NN*=2;
			LIMIT++;
		}
		
		depth = new int[N+1];
		sparse = new int[N+1][LIMIT];
		
		for(int i=1; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			input[a].add(b);
			input[b].add(a);
		}
		
		sparse[1][0] = -1;
		setDepth();
		setSparse();
		
		M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			int lca = lca(a, b);
			
			bw.append(String.valueOf(lca)).append("\n");
		}
		
		bw.close();
	}

	private static int lca(int a, int b) {
		if(depth[a] < depth[b]) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		int diff = depth[a] - depth[b];
		
		int pos = 0;
		while(diff > 0) {
			if(diff%2 == 1) {
				a = sparse[a][pos];
			}
			
			diff/=2;
			pos++;
		}
		
		if(a == b) {
			return a;
		}
		
		pos = LIMIT-1;
		while(pos >= 0) {
			if(sparse[a][pos] != sparse[b][pos]) {
				a = sparse[a][pos];
				b = sparse[b][pos];
			}
			pos--;
		}
		
		return sparse[a][0];
	}

	private static void setSparse() {
		for(int j=1; j<LIMIT; j++) {
			for(int i=1; i<=N; i++) {
				if(sparse[i][j-1] == -1) {
					sparse[i][j] = -1;
					continue;
				}
				sparse[i][j] = sparse[sparse[i][j-1]][j-1];
			}
		}
	}

	private static void setDepth() {
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(1);
		
		while(!queue.isEmpty()) {
			int cur = queue.poll();
			
			for(int next : input[cur]) {
				if(sparse[next][0] == 0) {
					sparse[next][0] = cur;
					depth[next] = depth[cur] + 1;
					queue.add(next);
				}
			}
		}
	}
}
