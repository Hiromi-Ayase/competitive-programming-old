
public class Date {
	// 1/1/1からの経過日数
	public static int passDays(int y, int m, int d) {
		if (m <= 2) {
			y --;
			m += 12;
		}

		return 365 * y + y / 4 - y / 100 + y / 400 + 306 * (m + 1) / 10 + d - 429;
	}
}
