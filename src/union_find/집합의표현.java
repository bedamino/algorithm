package union_find;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 집합의표현 {

	private static int N;
	private static int M;
	private static int[] root;
	private static int[] rank;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("집합의표현")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		root = new int[N+1];
		rank = new int[N+1];
		for(int i=1; i<=N; i++) {
			root[i] = i;
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int cmd = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			if(cmd == 0) {
				//union
				union(a, b);
				
			}else {
				//find
				if(find(a) == find(b)) {
					bw.append("YES").append("\n");
					
				}else {
					bw.append("NO").append("\n");
				}
			}
		}
		
		bw.close();
	}

	private static void union(int a, int b) {
		int A = find(a);
		int B = find(b);
		
		//두 집합의 대표값이 같은 경우 
		if(A == B) {
			return;
		}
		
		//B 집합 트리의 높이가 더 높을 때, A 집합 트리를 B 집합 트리의 밑으로 붙임
		if(rank[A] < rank[B]) {
			root[A] = B;
			
		}else {
			//A 집합 트리의 높이가 더 높을 때, B 집합 트리를 A 집합 트리의 밑으로 붙임
			root[B] = A;
			
			//두 집합 트리의 높이가 같다면 높이를 1 증가 시킴
			if(rank[A] == rank[B]) {
				rank[A]++;
			}
		}
	}

	private static int find(int a) {
		if(a == root[a]) {
			return a;
		}
		
		return root[a] = find(root[a]);
	}
}
