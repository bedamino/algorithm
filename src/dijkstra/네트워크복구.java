package dijkstra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class 네트워크복구 {
	
	private static int N;
	private static int M;
	private static List<Node>[] input;
	private static PriorityQueue<Node> pq;
	private static int[] min;
	private static int INF = Integer.MAX_VALUE;
	private static int[] path;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("네트워크복구")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		min = new int[N+1];
		Arrays.fill(min, INF);
		
		path = new int[N+1];
		input = new List[N+1];
		for(int i=1; i<=N; i++) {
			input[i] = new ArrayList<Node>();
		}
		
		for(int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			input[a].add(new Node(b, c));
			input[b].add(new Node(a, c));
		}
		
		pq = new PriorityQueue<Node>();
		pq.add(new Node(1, 0));
		min[1] = 0;
		path[1] = -1;
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
					
			if(cur.time > min[cur.node]) {
				continue;
			}
			
			for(Node next : input[cur.node]) {
				if(min[next.node] > cur.time + next.time) {
					path[next.node] = cur.node;
					min[next.node] = cur.time + next.time;
					pq.add(new Node(next.node, min[next.node]));
				}
			}
		}
		
		int count = 0;
		for(int i=2; i<=N; i++) {
			if(min[i] != INF) {
				count++;
			}
		}
		bw.append(String.valueOf(count)).append("\n");
		
		for(int i=2; i<=N; i++) {
			if(min[i] != INF) {
				bw.append(i + " " + path[i]).append("\n");
			}
		}
		
		bw.close();
	}
	
	public static class Node implements Comparable<Node>{
		public int node;
		public int time;
		
		public Node(int node, int time) {
			this.node = node;
			this.time = time;
		}

		@Override
		public int compareTo(Node o) {
			return this.time - o.time;
		}
	}
}
