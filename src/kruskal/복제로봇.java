package kruskal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class 복제로봇 {

	private static int N;
	private static int M;
	private static int[][] input;
	private static boolean[][] visit;
	private static int[][] search;
	private static Queue<Node> queue;
	private static PriorityQueue<Edge> pq;
	private static int[] root;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("복제로봇")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		search = new int[4][2];
		search[0][0] = -1;
		search[0][1] = 0;
		search[1][0] = 0;
		search[1][1] = 1;
		search[2][0] = 1;
		search[2][1] = 0;
		search[3][0] = 0;
		search[3][1] = -1;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		int key = 0;
		input = new int[N][N];
		
		for(int i=0; i<N; i++) {
			String line = br.readLine();
			for(int j=0; j<N; j++) {
				char target = line.charAt(j);
				
				if(target == 'S' || target == 'K') {
					//출발점과 열쇠가 있는 위치에 넘버링
					input[i][j] = ++key;
					
				}else if(target == '1') {
					//갈 수 없는 지역
					input[i][j] = -1;
				}
			}
		}
		
		root = new int[key+1];
		for(int i=1; i<=key; i++) {
			root[i] = i;
 		}
		
		pq = new PriorityQueue<Edge>();

		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				//출발점과 열쇠가 있는 위치에서 BFS로 최단 거리 계산
				if(input[i][j] > 0) {
					bfs(i, j);
				}
			}
		}
		
		int count = 0;
		int min = 0;
		
		//우선순위큐에 삽입한 간선 정보들로 MST 생성
		while(!pq.isEmpty()) {
			Edge edge = pq.poll();
			
			int a = find(edge.from);
			int b = find(edge.to);
			
			if(a == b) {
				continue;
			}
			
			root[b] = a;
			count++;
			min += edge.dist;
		}
		
		//트리를 구성하는 간선의 갯수가 M개보다 작으면 MST가 아니므로 모든 열쇠를 찾는 것이 불가능
		if(count < M) {
			bw.append("-1");
			
		}else {
			bw.append(String.valueOf(min));
		}
		
		bw.close();
	}
	
	private static int find(int a) {
		if(root[a] == a) {
			return a;
		}
		return root[a] = find(root[a]);
	}

	private static void bfs(int x, int y) {
		queue = new LinkedList<Node>();
		queue.add(new Node(x, y, 0));
		
		visit = new boolean[N][N];
		visit[x][y] = true;
		
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			
			//출발점이나 열쇠가 있는 지점을 찾았을 떄 최단 거리를 우선순위큐에 삽입
			if(input[x][y] != input[cur.x][cur.y] && input[cur.x][cur.y] > 0) {
				pq.add(new Edge(input[x][y], input[cur.x][cur.y], cur.dist));
			}
			
			for(int i=0; i<4; i++) {
				int searchX = cur.x + search[i][0];
				int searchY = cur.y + search[i][1];
				
				if(searchX < 0 || searchX > N-1 || searchY < 0 || searchY > N-1) {
					continue;
				}
				
				if(visit[searchX][searchY] || input[searchX][searchY] == -1) {
					continue;
				}
				
				visit[searchX][searchY] = true;
				queue.add(new Node(searchX, searchY, cur.dist+1));
			}
		}
	}

	public static class Node {
		public int x;
		public int y;
		public int dist;
		
		public Node(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
	}
	
	public static class Edge implements Comparable<Edge> {
		public int from;
		public int to;
		public int dist;
		
		public Edge(int from, int to, int dist) {
			this.from = from;
			this.to = to;
			this.dist = dist;
		}

		@Override
		public int compareTo(Edge o) {
			return this.dist - o.dist;
		}
	}
}
