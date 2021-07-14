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
import java.util.Stack;
import java.util.StringTokenizer;

public class 최소비용구하기2 {
	
	private static int N;
	private static int M;
	private static List<Node>[] input;
	private static int[] min;
	private static int[] path;
	private static int INF = Integer.MAX_VALUE;
	private static PriorityQueue<Node> pq;
	private static int start;
	private static int end;
	private static Stack<Integer> stack;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("최소비용구하기2")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		input = new List[N+1];
		for(int i=1; i<=N; i++) {
			input[i] = new ArrayList<Node>();
		}
		
		path = new int[N+1];
		min = new int[N+1];
		Arrays.fill(min, INF);
		
		for(int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			input[a].add(new Node(b, c));
		}
		
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		pq = new PriorityQueue<Node>();
		pq.add(new Node(start, 0));
		min[start] = 0;
		path[start] = -1;
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if(cur.cost > min[cur.node]) {
				continue;
			}
			
			for(Node next : input[cur.node]) {
				if(min[next.node] > cur.cost + next.cost) {
					path[next.node] = cur.node; //경로 추적
					min[next.node] = cur.cost + next.cost;
					pq.add(new Node(next.node, min[next.node]));
				}
			}
		}
		
		trace(end);
		
		bw.append(String.valueOf(min[end])).append("\n");
		bw.append(String.valueOf(stack.size())).append("\n");
		while(!stack.isEmpty()) {
			bw.append(stack.pop() + " ");
		}
		
		bw.close();
	}
	
	private static void trace(int target) {
		stack = new Stack<Integer>();
		
		while(target != -1) {
			stack.add(target);
			target = path[target];
		}
	}

	public static class Node implements Comparable<Node>{
		public int node;
		public int cost;
		
		public Node(int node, int cost) {
			this.node = node;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}
}
