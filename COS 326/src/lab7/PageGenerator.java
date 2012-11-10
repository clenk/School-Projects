package lab7;

/**
 * This class generates page references ranging from 0 .. 9
 *
 * Usage:
 *	PageGenerator gen = new PageGenerator()
 *	int[] ref = gen.getReferenceString();
 */

public class PageGenerator
{
	private static final int DEFAULT_SIZE = 100;
	private static final int RANGE = 9;

	int[] referenceString;

	public PageGenerator() {
		this(DEFAULT_SIZE);
	}

	public PageGenerator(int count) {
		if (count < 0)
			throw new IllegalArgumentException();

		java.util.Random generator = new java.util.Random();
		referenceString = new int[count];

		for (int i = 0; i < count; i++)
			referenceString[i] = generator.nextInt(RANGE + 1);
	}

	public int[] getReferenceString() {
		return referenceString;
	}
}