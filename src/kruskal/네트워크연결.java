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

public class 네트워크연결 {
	
	private static int N;
	private static int M;
	private static PriorityQueue<Node> pq;
	private static int[] root;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("네트워크연결")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		root = new int[N+1];
		for(int i=1; i<=N; i++) {
			root[i] = i;
		}
		pq = new PriorityQueue<Node>();
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			pq.add(new Node(a, b, cost));
		}
		
		int min = 0;
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			int a = find(cur.from);
			int b = find(cur.to);
			
			if(a == b) {
				continue;
			}
			
			root[b] = a;
			min += cur.cost;
		}
		
		bw.append(String.valueOf(min));
		bw.close();
	}
	
	private static int find(int a) {
		if(root[a] == a) {
			return a;
		}
		return root[a] = find(root[a]);
	}

	public static class Node implements Comparable<Node>{
		public int from;
		public int to;
		public int cost;
		
		public Node(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}
}
