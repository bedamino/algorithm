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

public class 최단경로 {
	
	private static int V;
	private static int E;
	private static int K;
	private static List<Node>[] input;
	private static PriorityQueue<Node> pq;
	private static int[] min;
	private static int INF = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("최단경로")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(br.readLine());
		
		min = new int[V+1];
		Arrays.fill(min, INF); //최단 거리 배열 초기화
		
		input = new List[V+1];
		for(int i=1; i<=V; i++) {
			input[i] = new ArrayList<Node>();
		}
		
		for(int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			input[u].add(new Node(v, w));
		}
		
		pq = new PriorityQueue<Node>();
		pq.add(new Node(K, 0));
		min[K] = 0; //시작점의 최단 거리는 0
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			for(Node next : input[cur.node]) {
				//이미 저장되어 있는 다음 노드까지의 최단 거리보다 (현재 노드까지의 최단 거리 + 다음 노드)까지의 거리가 작으면 갱신 
				if(min[next.node] > cur.dist + next.dist) {
					min[next.node] = cur.dist + next.dist;
					pq.add(new Node(next.node, min[next.node]));
				}
			}
		}
		
		for(int i=1; i<=V; i++) {
			if(min[i] == INF) {
				bw.append("INF").append("\n");
				
			}else {
				bw.append(String.valueOf(min[i])).append("\n");
			}
		}
		
		bw.close();
	}
	
	public static class Node implements Comparable<Node>{
		public int node;
		public int dist;
		
		public Node(int node, int dist) {
			this.node = node;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			return this.dist - o.dist;
		}
	}
}
