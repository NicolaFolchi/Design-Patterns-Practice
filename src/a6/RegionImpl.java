package a6;

public class RegionImpl implements Region {

	int left, top, right, bottom;
	
	public RegionImpl(int left, int top, int right, int bottom) {
		if (top > bottom) {
			throw new IllegalArgumentException("top can't be greater than bottom");
		}
		if( left > right) {
			throw new IllegalArgumentException(" left can't be greater than right");
		}
	
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
	}

	@Override
	public int getTop() {
		// TODO Auto-generated method stub
		return top;
	}

	@Override
	public int getBottom() {
		// TODO Auto-generated method stub
		return bottom;
	}

	@Override
	public int getLeft() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public int getRight() {
		// TODO Auto-generated method stub
		return right;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		// TODO Auto-generated method stub
		if (other == null) {
			throw new NoIntersectionException();
		}
		
		if (getTop() > other.getBottom()
			|| getRight() < other.getLeft()
			|| getLeft() > other.getRight()
			|| getBottom() < other.getTop()) {
			throw new NoIntersectionException();
		}
		
		
		int left = (getLeft() >= other.getLeft()) ? getLeft() : other.getLeft();
		int right = (getRight() <= other.getRight()) ? getRight() : other.getRight();
		int top = (getTop() >= other.getTop()) ? getTop() : other.getTop();
		int bottom = (getBottom() <= other.getBottom()) ? getBottom() : other.getBottom();
		/*int largerTop = 0;
		int smallerBottom = 0;
		int largerLeft = 0;
		int smallerRight = 0;
		
		// Finding the larger Top
		if (getTop() < other.getTop()) {
			 largerTop = other.getTop();
		}
		else if (getTop() > other.getTop()) {
			 largerTop = getTop();
		}
		
		// Finding the smaller Bottom
		if (getBottom() > other.getBottom()) {
			 smallerBottom = other.getBottom();
		}
		else if (getBottom() < other.getBottom()) {
			 smallerBottom = getBottom();
		}
		
		// Finding the larger Left
		if (getLeft() < other.getLeft()) {
			 largerLeft = other.getLeft();
		}
		else if (getLeft() > other.getLeft()) {
			 largerLeft = getLeft();
		}
		
		// Fiding the smaller Right
		if (getRight() > other.getRight()) {
			 smallerRight = other.getRight();
		}
		else if (getRight() < other.getRight()) {
			 smallerRight = getRight();
		}*/

		Region inter = new RegionImpl(left, top, right, bottom);
		
	
		return inter;
	}

	@Override
	public Region union(Region other) {
		// TODO Auto-generated method stub
		if (other == null) {
			return this;
		}
		int smallerTop = 0;
		int largerBottom = 0;
		int smallerLeft = 0;
		int largerRight = 0;
		
		// Finding the Smaller Top
		if (getTop() > other.getTop()) {
			 smallerTop = other.getTop();
		}
		else if (getTop() < other.getTop()) {
			 smallerTop = getTop();
		}
		
		// Finding the Larger Bottom
		if (getBottom() < other.getBottom()) {
			 largerBottom = other.getBottom();
		}
		else if (getBottom() > other.getBottom()) {
			 largerBottom = getBottom();
		}
		
		// Finding the Smaller Left
		if (getLeft() > other.getLeft()) {
			 smallerLeft = other.getLeft();
		}
		else if (getLeft() < other.getLeft()) {
			 smallerLeft = getLeft();
		}
		
		// Fiding the Larger Right
		if (getRight() < other.getRight()) {
			 largerRight = other.getRight();
		}
		else if (getRight() > other.getRight()) {
			 largerRight = getRight();
		}
		
		Region myUnion = new RegionImpl(smallerLeft, smallerTop, largerRight, largerBottom);
		
		return myUnion;
	}

}
