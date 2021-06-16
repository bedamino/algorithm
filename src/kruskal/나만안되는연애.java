package kruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 나만안되는연애 {
	
	private static int N;
	private static int M;
	private static String[] input;
	private static int[] root;
	private static PriorityQueue<Node> pq;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("나만안되는연애")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		input = new String[N+1];
		root = new int[N+1];
		for(int i=1; i<=N; i++) {
			root[i] = i;
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i=1; i<=N; i++) {
			input[i] = st.nextToken();
		}
		
		pq = new PriorityQueue<Node>();
		
		for(int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			pq.add(new Node(a, b, cost));
		}
		
		int min = 0;
		int count = 0;
		while(!pq.isEmpty()) {
			Node node = pq.poll();
			
			int a = find(node.from);
			int b = find(node.to);
			
			//두 학교가 이미 연결 되어 있거나, 남학교와 여학교를 연결하는 도로가 아닌 경우
			if((a == b) || input[node.from].equals(input[node.to])) {
				continue;
			}
			
			root[b] = a;
			min += node.cost;
			count++;
		}
		
		if(count < N-1) {
			//연결된 도로의 개수가 N-1개가 아니라면 MST가 성립되지 않는다.
			bw.append("-1").append("\n");
		}else {
			bw.append(String.valueOf(min)).append("\n");
		}
		
		bw.close();
	}
	
	private static int find(int a) {
		if(root[a] == a) {
			return a;
		}
		return root[a] = find(root[a]);
	}

	public static class Node implements Comparable<Node> {
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
