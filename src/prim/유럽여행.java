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

public class 유럽여행 {
	
	private static int N;
	private static int P;
	private static int[] cost;
	private static PriorityQueue<Node> pq;
	private static List<Node>[] input;
	private static boolean[] visit;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("유럽여행")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		
		visit = new boolean[N+1];
		cost = new int[N+1];
		input = new List[N+1];
		for(int i=1; i<=N; i++) {
			input[i] = new ArrayList<Node>();
		}
		
		int start = 0;
		int min = Integer.MAX_VALUE;
		for(int i=1; i<=N; i++) {
			cost[i] = Integer.parseInt(br.readLine());
			
			//방문 비용이 가장 작은 나라를 시작점으로 지정
			if(min > cost[i]) {
				min = cost[i];
				start = i;
			}
		}
		
		for(int i=1; i<=P; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int l = Integer.parseInt(st.nextToken());
			
			//(통과 비용 * 2) + 출발하는 나라 방문 비용 + 도착하는 나라 방문 비용
			input[s].add(new Node(e, (l*2)+cost[s]+cost[e]));
			input[e].add(new Node(s, (l*2)+cost[s]+cost[e]));
		}
		
		pq = new PriorityQueue<Node>();
		pq.add(new Node(start, cost[start]));
		
		int answer = 0;
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if(visit[cur.to]) {
				continue;
			}
			
			visit[cur.to] = true;
			answer += cur.cost;
			
			for(Node next : input[cur.to]) {
				if(visit[next.to]) {
					continue;
				}
				pq.add(new Node(next.to, next.cost));
			}
		}
		
		bw.append(String.valueOf(answer));
		bw.close();
	}
	
	public static class Node implements Comparable<Node>{
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
