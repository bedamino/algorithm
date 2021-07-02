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
	private static int[] depth; //노드의 깊이
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
		LIMIT = 0;
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
		
		sparse[1][0] = -1; //루트의 2^0번째 조상은 없다.
		setDepth(); //BFS로 각 노드들의 깊이와 2^0번째 조상 확정
		setSparse(); //각 노드들에 대하여 2^0번째 조상을 제외한 나머지 조상 확정
		
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
		//a의 깊이가 b의 깊이보다 항상 크게끔 변경
		if(depth[a] < depth[b]) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		//a의 깊이를 b의 깊이와 동일하게 변경
		int pos = LIMIT-1;
		while(pos >= 0) {
			//a와 b의 깊이 차이가 2^pos보다 크거나 같다면 a의 위치를 2^pos번째 조상 위치로 변경
			if(depth[a] - depth[b] >= 1<<pos) {
				a = sparse[a][pos];
			}
			pos--;
		}
		
		//a의 깊이를 b의 깊이와 동일하게 변경했을 때, a와 b가 같다면 최소 공통 조상은 a(b)
		if(a == b) {
			return a;
		}

		//a와 b의 깊이를 동시에 변경하며 최소 공통 조상의 자식 노드를 탐색
		pos = LIMIT-1;
		while(pos >= 0) {
			if(sparse[a][pos] != sparse[b][pos]) {
				a = sparse[a][pos];
				b = sparse[b][pos];
			}
			pos--;
		}
		
		//a(b)는 최소 공통 조상의 자식이므로, a(b)의 부모가 최소 공통 조상
		return sparse[a][0];
	}

	private static void setSparse() {
		for(int j=1; j<LIMIT; j++) {
			for(int i=1; i<=N; i++) {
				if(sparse[i][j-1] == -1) {
					sparse[i][j] = -1; //노드 i의 2^(j-1)번째 조상이 없다면 2^j번째 조상도 없음
					continue;
				}
				sparse[i][j] = sparse[sparse[i][j-1]][j-1]; //노드 i의 2^j번째 조상 확정
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
