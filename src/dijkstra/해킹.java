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

public class 해킹 {
	
	private static int T;
	private static int N;
	private static int D;
	private static int C;
	private static List<Node>[] input;
	private static int[] min;
	private static int INF = Integer.MAX_VALUE;
	private static PriorityQueue<Node> pq;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("해킹")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		T = Integer.parseInt(br.readLine());
		
		for(int t=0; t<T; t++) {
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			min = new int[N+1];
			Arrays.fill(min, INF);
			
			input = new List[N+1];
			for(int i=1; i<=N; i++) {
				input[i] = new ArrayList<Node>();
			}
			
			for(int i=1; i<=D; i++) {
				st = new StringTokenizer(br.readLine());
				
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int s = Integer.parseInt(st.nextToken());
				
				input[b].add(new Node(a, s));
			}
			
			pq = new PriorityQueue<Node>();
			pq.add(new Node(C, 0));
			min[C] = 0;
			
			while(!pq.isEmpty()) {
				Node cur = pq.poll();
				
				if(cur.time > min[cur.node]) {
					continue;
				}
				
				for(Node next : input[cur.node]) {
					if(min[next.node] > cur.time + next.time) {
						min[next.node] = cur.time + next.time;
						pq.add(new Node(next.node, min[next.node]));
					}
				}
			}
			
			int count = 0;
			int max = Integer.MIN_VALUE;
			for(int i=1; i<=N; i++) {
				if(min[i] != INF) {
					count++;
					max = Math.max(max, min[i]);
				}
			}
			
			bw.append(count + " " + max).append("\n");
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
