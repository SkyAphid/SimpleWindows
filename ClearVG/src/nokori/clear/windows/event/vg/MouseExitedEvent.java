package nokori.clear.windows.event.vg;

import nokori.clear.windows.Window;
import nokori.clear.windows.event.Event;
import nokori.clear.windows.pool.Pool;

public class MouseExitedEvent implements Event {
	
	private static final Pool<MouseExitedEvent> POOL = new Pool<MouseExitedEvent>() {
		@Override
		protected MouseExitedEvent create() {
			return new MouseExitedEvent();
		}
	};
	
	private Window window;
	private long timestamp;
	private double mouseX;
	private double mouseY;
	
	private MouseExitedEvent() {}
	
	public static MouseExitedEvent fire(Window window, long timestamp, double mouseX, double mouseY) {

		MouseExitedEvent e = POOL.get();
		
		e.window = window;
		e.timestamp = timestamp;
		e.mouseX = mouseX;
		e.mouseY = mouseY;
		
		return e;
	}
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void reset() {
		window = null;
	}

	public Window getWindow() {
		return window;
	}

	public double getMouseX() {
		return mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}
}
