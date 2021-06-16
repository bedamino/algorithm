package prim;

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

public class 정복자 {
	
	private static int N;
	private static int M;
	private static int T;
	private static List<Node>[] input;
	private static boolean[] visit;
	private static PriorityQueue<Node> pq;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("정복자")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		visit = new boolean[N+1];
		input = new List[N+1];
		for(int i=1; i<=N; i++) {
			input[i] = new ArrayList<Node>();
		}
		
		for(int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			input[a].add(new Node(b, cost));
			input[b].add(new Node(a, cost));
		}
		
		pq = new PriorityQueue<Node>();
		pq.add(new Node(1, 0));
		
		int min = 0;
		int count = -1;
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if(visit[cur.to]) {
				continue;
			}
			
			visit[cur.to] = true;
			
			for(Node next : input[cur.to]) {
				if(visit[next.to]) {
					continue;
				}
				
				pq.add(new Node(next.to, next.cost));
			}
			
			min += cur.cost;
			if(count > 0) {
				//정복한 도시의 숫자만큼 비용 증가
				min += (count * T);
			}
			count++;
		}
		
		bw.append(String.valueOf(min));
		bw.close();
	}
	
	public static class Node implements Comparable<Node> {
		public int to;
		public int cost;
		
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}
}
