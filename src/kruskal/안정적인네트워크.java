package kruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 안정적인네트워크 {
	
	private static int N;
	private static int M;
	private static int[] root;
	private static int[][] input;
	private static PriorityQueue<Edge> pq;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("안정적인네트워크")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;

		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		root = new int[N+1];
		for(int i=1; i<=N; i++) {
			root[i] = i;
		}
		input = new int[N+1][N+1];
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			//직접 연결된 컴퓨터는 같은 집합으로 묶음
			int a = find(x);
			int b = find(y);
			
			root[b] = a;
		}
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				int cost = Integer.parseInt(st.nextToken());
				input[i][j] = cost;
			}
		}
		
		pq = new PriorityQueue<Edge>();
		
		for(int i=2; i<=N; i++) {
			for(int j=i+1; j<=N; j++) {
				//본사 컴퓨터를 제외한 나머지 컴퓨터들간의 간선 처리
				pq.add(new Edge(i, j, input[i][j]));
			}
		}
		
		int min = 0;
		int count = 0;
		List<Edge> answer = new ArrayList<Edge>();
		
		while(!pq.isEmpty()) {
			Edge edge = pq.poll();
			
			int a = find(edge.from);
			int b = find(edge.to);
			
			if(a == b) {
				continue;
			}
			
			root[b] = a;
			
			min += edge.cost;
			count++;
			answer.add(new Edge(edge.from, edge.to, 0));
		}
		
		bw.append(min + " " + count).append("\n");
		for(Edge edge : answer) {
			bw.append(edge.from + " " + edge.to).append("\n");
		}
		
		bw.close();
	}

	private static int find(int a) {
		if(root[a] == a) {
			return a;
		}
		return root[a] = find(root[a]);
	}
	
	public static class Edge implements Comparable<Edge>{
		public int from;
		public int to;
		public int cost;
		
		public Edge(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return this.cost - o.cost;
		}
	}
}
