
class Point implements Comparable<Point> {
	public final int x;
	public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point o) {
		return x == o.x ? (y - o.y) : (x - o.x);
	}
}
