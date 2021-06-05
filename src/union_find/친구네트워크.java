package union_find;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class 친구네트워크 {
	
	private static int T;
	private static int N;
	private static Map<String, Integer> map;
	private static int[] friends;
	private static int[] root;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File("친구네트워크")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		T = Integer.parseInt(br.readLine());
		
		for(int t=0; t<T; t++) {
			int index = 0;
			N = Integer.parseInt(br.readLine());
			
			//사용자(String)를 숫자(Int)로 매핑하기 위한 map
			map = new HashMap<String, Integer>();
			
			//사용자가 전부 다를 경우를 고려해서 2*N 크기의 배열 확보 
			friends = new int[2*N]; //친구 숫자 
			root = new int[2*N];
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				String a = st.nextToken();
				String b = st.nextToken();
				
				if(!map.containsKey(a)) {
					root[index] = index;
					friends[index]++;
					map.put(a, index++); //사용자를 숫자로 매핑
				}
				
				if(!map.containsKey(b)) {
					root[index] = index;
					friends[index]++;
					map.put(b, index++);
				}
				
				bw.append(String.valueOf(union(map.get(a), map.get(b)))).append("\n");
			}
		}
		
		bw.close();
	}

	private static int union(int a, int b) {
		int A = find(a);
		int B = find(b);
		
		//두 사용자의 집합이 같다면, 현재 친구 수를 return
		if(A == B) {
			return friends[A];
		}
		
		//두 사용자의 집합이 다르다면, 두 집합을 합침
		root[B] = A;
		
		//두 집합의 친구 수를 합치고 return
		friends[A] += friends[B];
		
		return friends[A];
	}

	private static int find(int a) {
		if(a == root[a]) {
			return a;
		}
		
		return root[a] = find(root[a]);
	}
}
