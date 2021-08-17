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

public class 상자넣기 {

	private static int N;
	private static int[] box;
	private static int[] lis;
	private static int INF = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("상자넣기")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;

		N = Integer.parseInt(br.readLine());
		
		box = new int[N];
		lis = new int[N+1];
		Arrays.fill(lis, INF);
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			box[i] = Integer.parseInt(st.nextToken());
		}
		
		int answer = Integer.MIN_VALUE;
		for(int i=0; i<N; i++) {
			int l = 0;
			int r = box.length;
			int mid = 0;
			
			//동일한 크기의 상자가 들어올 수 있으므로 lower bound를 구한다. 
			while(l < r) {
				mid = (l+r)/2;
				
				if(lis[mid] < box[i]) {
					l = mid + 1;
					
				}else {
					r = mid;
				}
			}
			
			lis[r] = box[i];
			answer = Math.max(answer, r);
		}
		
		bw.append(String.valueOf(answer+1));
		
		bw.close();
	}
}
