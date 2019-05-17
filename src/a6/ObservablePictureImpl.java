package a6;

import java.util.ArrayList;
import java.util.List;

public class ObservablePictureImpl implements ObservablePicture{

private Picture p;
private List<ROIObserver> observers;
private List<Region> regions;
private String caption;
private boolean suspended;
private Region overallChangedRegion;
	
	public ObservablePictureImpl(Picture p) {

		if (p == null) {
			throw new IllegalArgumentException("p can't be null");
		}

		suspended = false;
		overallChangedRegion = null;
		this.p = p;
		observers = new ArrayList<ROIObserver>();
		regions = new ArrayList<Region>();
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return p.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return p.getHeight();
	}

	@Override
	public Pixel getPixel(int x, int y) {
		// TODO Auto-generated method stub
		return p.getPixel(x, y);
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		this.p.paint(x, y, p, 1);
		int i = 0;

		Region myRegion = new RegionImpl(x, y, x, y);
		
		try {
			return notifyObserverChange(myRegion);
		} catch (NoIntersectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return this;
		}
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		// TODO Auto-generated method stub
		this.p.paint(x, y, p, factor);
		int i = 0;
		
		Region myRegion = new RegionImpl(x, y, x, y);
		
		for (Region region : regions) {
			try {
				myRegion.intersect(region);
			} catch (NoIntersectionException e){
				e.printStackTrace();
			} observers.get(i).notify(this, myRegion);
			i++;
		}
		
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub
		this.p.paint(ax, ay, bx, by, p);
		int i = 0;
		
		int left = (ax <= bx) ? ax : bx;
		int right = (ax > bx) ? ax : bx;
		int top = (ay <= by) ? ay : by;
		int bottom = (ay >= by) ? ay : by;
		
		Region myRegion = new RegionImpl(left, top, right, bottom);
		
		try {
			return notifyObserverChange(myRegion);
		} catch (NoIntersectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return this;
		}
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// TODO Auto-generated method stub
		this.p.paint(ax, ay, bx, by, p, factor);
		int i = 0;
		
		int left = (ax <= bx) ? ax : bx;
		int right = (ax > bx) ? ax : bx;
		int top = (ay <= by) ? ay : by;
		int bottom = (ay >= by) ? ay : by;
		
		Region myRegion = new RegionImpl(left, top, right, bottom);
		
		for (Region region : regions) {
			try {
				myRegion.intersect(region);
			} catch (NoIntersectionException e){
				e.printStackTrace();
			} Region intersectRegion = new RegionImpl(left, top, right, bottom);
			try { 
			intersectRegion = myRegion.intersect(region);
			} catch (NoIntersectionException e){
				e.printStackTrace();
			}observers.get(i).notify(this, intersectRegion);
			i++;
		}
		return this;	
		}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		this.p.paint(cx, cy, radius, p);
		int i = 0;
		
		int left = (int) (cx - radius);
		int top = (int) (cy - radius);
		int right = (int) (cx + radius);
		int bottom = (int) (cy + radius);
		
		Region myRegion = new RegionImpl(left, top, right, bottom);
		
		try {
			return notifyObserverChange(myRegion);
		} catch (NoIntersectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return this;
		}
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) throws NoIntersectionException  {
		// TODO Auto-generated method stub
		try {
			this.p.paint(cx, cy, radius, p, 1.0);
		} catch (NoIntersectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		
		int left = (int) (cx - radius);
		int top = (int) (cy - radius);
		int right = (int) (cx + radius);
		int bottom = (int) (cy + radius);
		
		Region myRegion = new RegionImpl(left, top, right, bottom);
		
		return notifyObserverChange(myRegion);
		
}
	@Override
	public Picture paint(int x, int y, Picture p) {
		// TODO Auto-generated method stub
		this.p = this.p.paint(x, y, p);
		int i = 0;
		
		Region myRegion = new RegionImpl(x, y, ((x + p.getWidth()) -1), ((y + p.getHeight())-1));
		
		try {
			return notifyObserverChange(myRegion);
		} catch (NoIntersectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return this;
		}
	}

	@Override
	public Picture paint(int x, int y, Picture p, double factor) {
		// TODO Auto-generated method stub
		this.p = this.p.paint(x, y, p, factor);
		int i = 0;
		
		Region myRegion = new RegionImpl(x, y, ((x + (p.getWidth()/p.getHeight()))-1), ((y + (p.getWidth()/p.getHeight()))-1));
		
		for (Region region : regions) {
			try {
				myRegion.intersect(region);
			} catch (NoIntersectionException e){
				e.printStackTrace();
			} Region intersectRegion = new RegionImpl(x, y, x, y);
			try { 
			intersectRegion = myRegion.intersect(region);
			} catch (NoIntersectionException e){
				e.printStackTrace();
			}observers.get(i).notify(this, intersectRegion);
			i++;
		}	
		return this;
	}

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return p.getCaption();
	}

	@Override
	public void setCaption(String caption) {
		// TODO Auto-generated method stub
		this.caption = caption;
	}

	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerROIObserver(ROIObserver observer, Region r) {
		// TODO Auto-generated method stub	
		if (r == null || observer == null) 
			throw new IllegalArgumentException("null values");
		observers.add(observer);
		regions.add(r);
	}

	@Override
	public void unregisterROIObservers(Region r) {
		// TODO Auto-generated method stub
//		if ( r == null) {
//			throw new IllegalArgumentException("r can't be null");
//		}
		int i = 0;
		
		List<ROIObserver> tempObserver = new ArrayList<ROIObserver>();
		List<Region> tempRegion = new ArrayList<Region>();
		
		for (Region r3 : regions) {
			try {
				r3.intersect(r);
			} catch (NoIntersectionException e) {
				tempRegion.add(r3);
				tempObserver.add(observers.get(i));
			} finally {
				i++;
			}
		}
		regions = tempRegion;
		observers = tempObserver;
	}

	@Override
	public void unregisterROIObserver(ROIObserver observer) {
		// TODO Auto-generated method stub
//		if ( observer == null) {
//			throw new IllegalArgumentException("r can't be null");
//		}
		int i = 0;
		
		ArrayList<ROIObserver> tempObserver = new ArrayList<ROIObserver>();
		ArrayList<Region> tempRegion = new ArrayList<Region>();
		
		for (ROIObserver test : observers) {
			if (!observer.equals(test)) {
				tempRegion.add(regions.get(i));
				tempObserver.add(test);
			}
			i++;
		}
		regions = tempRegion;
		observers = tempObserver;
	}
	

	@Override
	public ROIObserver[] findROIObservers(Region r) {
		// TODO Auto-generated method stub
 //       if (r == null) {
 //           throw new IllegalArgumentException("Region cannot be null.");
 //       }
        int counter = 0;
        List<ROIObserver> oList = new ArrayList<ROIObserver>();
 //       ArrayList<Region> unregisteredRegion = new ArrayList<Region>();
        
        
        for (Region myRegion : regions) {
            if (checkIntersection(myRegion, r)) {
            	oList.add(observers.get(counter));
            }
            counter++;
        }

        ROIObserver[] observerArray = new ROIObserver[oList.size()];
        observerArray = oList.toArray(observerArray);
        return observerArray;
    }

	@Override
	public void suspendObservable() {
		// TODO Auto-generated method stub
		suspended = true;
	}

	@Override
	public void resumeObservable() {
		suspended = false;
		try {
			notifyObserverChange(overallChangedRegion);
		} catch (NoIntersectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public boolean checkIntersection(Region r1, Region r2) {
		try {
			r1.intersect(r2);
		}catch (NoIntersectionException e) {
			return false;
		}return true;
	}
// classmate lyell mcmerty helped explain how this worked to me	
	public Picture notifyObserverChange(Region r) throws NoIntersectionException {
		int counter = 0;
		if (suspended == true) {
			overallChangedRegion = r.union(overallChangedRegion);
			return this;
		} else {
			for (Region someRegion : regions) {
	            if(checkIntersection(someRegion, r)) {
	            	observers.get(counter).notify(this, someRegion.intersect(r));
	            }
	            counter++;
			}
		}	overallChangedRegion = null;

		return this;
	}
}
