package lis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 전깃줄 {
	
	private static int N;
	private static Line[] input;
	private static int[] lis;
	private static int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("전깃줄")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());
		input = new Line[N];
		lis = new int[N+1];
		Arrays.fill(lis, INF);
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			input[i] = new Line(a, b);
		}
		
		Arrays.sort(input);
	
		int answer = Integer.MIN_VALUE;
		for(int i=0; i<N; i++) {
			int l = 0;
			int r = N;
			int mid = 0;
			int target = input[i].right;
			
			while(l < r) {
				mid = (l+r)/2;
				
				if(lis[mid] < target) {
					l = mid + 1;
					
				}else {
					r = mid;
				}
			}
			lis[r] = target;
			answer = Math.max(answer, r+1);
		}
		
		bw.append(String.valueOf(N-answer));
		
		bw.close();
	}
	
	public static class Line implements Comparable<Line>{
		public int left;
		public int right;
		
		public Line(int left, int right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public int compareTo(Line o) {
			return this.left - o.left;
		}
	}
}
