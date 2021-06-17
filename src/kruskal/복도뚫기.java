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

public class 복도뚫기 {

	private static int T;
	private static int W;
	private static int N;
	private static int[][] input;
	private static PriorityQueue<Node> pq;
	private static int[] root;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("복도뚫기")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			W = Integer.parseInt(br.readLine());
			N = Integer.parseInt(br.readLine());
			input = new int[N][3]; //0:x, 1:y, 2:r
			root = new int[N+2];
			for(int i=0; i<N+2; i++) {
				root[i] = i;
			}
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				
				input[i][0] = Integer.parseInt(st.nextToken());
				input[i][1] = Integer.parseInt(st.nextToken());
				input[i][2] = Integer.parseInt(st.nextToken());
			}
			
			pq = new PriorityQueue<Node>();
			pq.add(new Node(N, N+1, W)); //왼쪽벽과 오른쪽벽 사이의 거리
			
			for(int i=0; i<N; i++) {
				double dist = input[i][0] - input[i][2]; //왼쪽벽까지의 거리
				pq.add(new Node(N, i, dist));
				
				dist = W - input[i][0] - input[i][2]; //오른쪽벽까지의 거리
				pq.add(new Node(i, N+1, dist));
				
				//다른 센서들까지의 거리
				for(int j=i+1; j<N; j++) {
					dist = dist(input[i], input[j]);
					pq.add(new Node(i, j, dist));
				}
			}
			
			while(!pq.isEmpty()) {
				Node node = pq.poll();
				
				int a = find(node.from);
				int b = find(node.to);
				
				if(a == b) {
					continue;
				}
				
				root[b] = a;
				
				if(find(N) == find(N+1)) { //왼쪽벽과 오른쪽벽이 같은 그룹인지 확인
					if(node.dist <= 0) {
						bw.append("0").append("\n"); //지나갈 수 없음
					}else {
						bw.append(String.format("%.6f", node.dist/2)).append("\n");
					}
					break;
				}
			}
		}
		
		bw.close();
	}
	
	private static int find(int a) {
		if(root[a] == a) {
			return a;
		}
		return root[a] = find(root[a]);
	}

	private static double dist(int[] a, int[] b) {
		//센서와 센서 사이의 거리, 각각의 반지름을 빼줘야 함
		return Math.sqrt(Math.pow((b[0]-a[0]), 2) + Math.pow((b[1]-a[1]), 2)) - a[2] - b[2];
	}

	public static class Node implements Comparable<Node>{
		public int from;
		public int to;
		public double dist;
		
		public Node(int from, int to, double dist) {
			this.from = from;
			this.to = to;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			return Double.compare(this.dist, o.dist); //double compare
		}
	}
}
