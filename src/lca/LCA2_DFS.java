package lca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LCA2_DFS {
	
	private static int N;
	private static int M;
	private static List<Integer>[] input;
	private static int NN;
	private static int LIMIT;
	private static int[] in; //진입
	private static int[] out; //진출
	private static int count; //진입 or 진출 순서
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
		
		in = new int[N+1];
		out = new int[N+1];
		sparse = new int[N+1][LIMIT];
		
		for(int i=1; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			input[a].add(b);
			input[b].add(a);
		}
		
		sparse[1][0] = -1; //루트의 2^0번째 조상은 없다.
		setOrder(1); //오일러 투어 테크닉, 각 노드의 2^0번째 조상 확정
		setSparse(); //각 노드들에 대하여 2^0번째 조상을 제외한 나머지 조상 확정
		
		M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			int lca = lca(a, b); //a와 b의 최소 공통 조상
			
			bw.append(String.valueOf(lca)).append("\n");
		}
		
		bw.close();
	}

	private static int lca(int a, int b) {
		//b가 a의 자식 노드인 경우
		if(in[a] <= in[b] && out[a] >= out[b]) {
			return a; //최소 공통 조상은 a
		}
		
		int pos = LIMIT-1; //a의 2^(LIMIT-1)번째 조상부터 탐색
		while(pos >= 0) {
			int cur = sparse[a][pos];
			
			if(cur == -1) { //조상이 없는 경우
				pos--;
				continue;
			}
			
			/*
			 * a의 2^pos번째 조상이 b의 조상이 아니라면 a를 a의 2^pos번째 조상으로 변경
			 * a와 b의 최소 공통 조상 = a의 2^pos번째 조상과 b의 최소 공통 조상
			 */
			if(in[cur] > in[b] || out[cur] < out[b]) {
				a = cur;
			}
			
			pos--;
		}
		
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

	private static void setOrder(int cur) {
		in[cur] = ++count; //진입
		
		for(int next : input[cur]) {
			if(sparse[next][0] == 0) { //2^0번째 조상이 확정되지 않은 경우
				sparse[next][0] = cur; //2^0번째 조상을 확정
				setOrder(next); //DFS 탐색
			}
		}
		
		out[cur] = count; //진출
	}
}
