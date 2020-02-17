
public class Trace {

	public static void tr(int[][] map) {
		int height = map.length;
		int width = map[0].length;
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < height; y ++) {
			for(int x = 0; x < width; x ++) {
				sb.append(map[y][x]);
				sb.append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}

	public static void tr(Object...objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			sb.append(o);
			sb.append(' ');
		}
		System.out.println(sb);
	}
}
