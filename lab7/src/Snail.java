
public class Snail {
	
	public static final char icon = '@';
	private int position;
	private QueueADT<Integer> movePattern;
	
	public Snail (int[] pattern) {
		position = 0;
		movePattern = new DLQueue<Integer>();
		for (int i = 0; i < pattern.length; i++) {
			movePattern.enqueue(pattern[i]);
		}
	}
	
	public void move () {
		// Use the movePattern queue to determine how far the snail moves.

		for (int i = 0; i < movePattern.size(); i++) {
			position += movePattern.dequeue();
			movePattern.enqueue(position);
		}

		if (position < 0) {
			position = 0;
		}

		if (position > SnailRace.raceLength) {
			position = SnailRace.raceLength;
		}
	}
	
	public int getPosition () {
		return position;
	}
	
	public void display () {
		int dashesBefore = position;
		int dashesAfter = SnailRace.raceLength - position;
		for (int i = 0; i < dashesBefore; i++) {
			System.out.print("-");
		}
		System.out.print(icon);
		for (int i = 0; i < dashesAfter; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

}
