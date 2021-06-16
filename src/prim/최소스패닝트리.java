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

public class 최소스패닝트리 {
	
	private static int V;
	private static int E;
	private static List<Node>[] input;
	private static PriorityQueue<Node> pq;
	private static boolean[] visit;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("최소스패닝트리")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		input = new List[V+1];
		for(int i=1; i<=V; i++) {
			input[i] = new ArrayList<Node>();
		}
		visit = new boolean[V+1];
		
		for(int i=1; i<=E; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			//무방향 간선 처리
			input[a].add(new Node(b, cost));
			input[b].add(new Node(a, cost));
		}
		
		pq = new PriorityQueue<Node>();
		pq.add(new Node(1, 0)); //탐색 시작점
		
		int minCost = 0;
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			//도착지점이 방문한 노드이면 skip
			if(visit[cur.to]) {
				continue;
			}
			
			visit[cur.to] = true; //방문처리
			
			//다음 도착지점을 탐색
			for(Node next : input[cur.to]) {
				//다음 도착지점이 방문한 노드이면 skip
				if(visit[next.to]) {
					continue;
				}
				pq.add(new Node(next.to, next.cost));
			}
			
			//처리가 완료된 노드의 가중치 합산
			minCost += cur.cost;
		}
		
		bw.append(String.valueOf(minCost));
		bw.close();
	}
	
	public static class Node implements Comparable<Node>{
		public int to; //도착점
		public int cost; //가중치
		
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			//cost 오름차순으로 정렬
			return this.cost - o.cost;
		}
	}
}
