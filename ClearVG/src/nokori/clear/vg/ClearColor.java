package nokori.clear.vg;

import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

/**
 * This class is a wrapper for NVGColor that allows for short-hand customization and allocation.
 */
public class ClearColor {
	public static final ClearColor WHITE = new ClearColor("#FFFFFF").immutable(true);
	public static final ClearColor LIGHT_GRAY = new ClearColor("#D3D3D3").immutable(true);
	public static final ClearColor SILVER = new ClearColor("#C0C0C0").immutable(true);
	public static final ClearColor GRAY = new ClearColor("#808080").immutable(true);
	public static final ClearColor DARK_GRAY = new ClearColor("#A9A9A9").immutable(true);
	public static final ClearColor LIGHT_BLACK = new ClearColor("#0A0A0A").immutable(true);
	public static final ClearColor WHITE_SMOKE = new ClearColor("#F5F5F5").immutable(true);
	public static final ClearColor BLACK = new ClearColor("#000000").immutable(true);
	public static final ClearColor RED = new ClearColor("#FF0000").immutable(true);
	public static final ClearColor PINK = new ClearColor("#FFC0CB").immutable(true);
	public static final ClearColor ORANGE = new ClearColor("#E59400").immutable(true);
	public static final ClearColor YELLOW = new ClearColor("#FFFF00").immutable(true);
	public static final ClearColor LIGHT_YELLOW = new ClearColor("#FFFFE0").immutable(true);
	public static final ClearColor LIGHT_BLUE = new ClearColor("#ADD8E6").immutable(true);
	public static final ClearColor GREEN = new ClearColor("#00FF00").immutable(true);
	public static final ClearColor MAGENTA = new ClearColor("#FF00FF").immutable(true);
	public static final ClearColor VIOLET = new ClearColor("#EE82EE").immutable(true);
	public static final ClearColor DARK_VIOLET = new ClearColor("#8A2BE2").immutable(true);
	public static final ClearColor CYAN = new ClearColor("#00FFFF").immutable(true);
	public static final ClearColor BLUE = new ClearColor("#0000FF").immutable(true);
	public static final ClearColor AQUA = new ClearColor("#00FFFF").immutable(true);
	public static final ClearColor CORAL = new ClearColor("#FF7F50").immutable(true);
	public static final ClearColor TRANSPARENT = new ClearColor(0f, 0f, 0f, 0f).immutable(true);

	private Vector4f color;
	private boolean immutable = false;
	
	/**
	 * Creates a Color from integers (0-255)
	 * @param r
	 * @param g
	 * @param b
	 */
	public ClearColor(int r, int g, int b, int a) {
		this((float) r * (1f/255f), (float) g * (1f/255f), (float) b * (1f/255f), (float) a * (1f/255f));
	}

	/**
	 * Creates a Color from a given HEX value. 
	 * 
	 * @param hex e.g. #FFFFFF for white. The hash-sign must be included.
	 */
	public ClearColor(String hex) {
		this(Integer.valueOf(hex.substring(1, 3), 16), Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(hex.substring(5, 7), 16), 255);
	}
	
	/**
	 * Creates a Color from another Color (a copy).
	 * 
	 * @param color - the color to copy
	 */
	public ClearColor(ClearColor color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
	/**
	 * Creates a Color from floats (0f-1f)
	 * @param r
	 * @param g
	 * @param b
	 */
	public ClearColor(float r, float g, float b, float a) {
		set(r, g, b, a);
	}
	
	/**
	 * Sets whether or not this Color is immutable. Immutable Colors cannot be recycled, and any setters used will instead return a new Color object rather than internally modifying this color.
	 * 
	 * @param immutable 
	 * @return
	 */
	public ClearColor immutable(boolean immutable) {
		this.immutable = immutable;
		return this;
	}
	
	/**
	 * Creates a copy of this Color, however customization options such as immutable are not copied.
	 * 
	 * @return - the copy of this Color.
	 */
	public ClearColor copy() {
		return new ClearColor(this);
	}
	
	/**
	 * Sets a Color from another Color (turns this Color into a copy of the given color)
	 * 
	 * @param color - the color to copy
	 * 
	 * @return this Color object if the Color is not mutable, or a new Color object if this Color is set to be immutable.
	 */
	public ClearColor set(ClearColor color) {
		return set(color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha());
	}
	
	/**
	 * Sets the red value in the range (0.0 - 1.0). Useful for when a Color object needs to be recycled.
	 * 
	 * @return this Color object if the Color is not mutable, or a new Color object if this Color is set to be immutable.
	 */
	public ClearColor red(float r) {
		return set(r, getGreen(), getBlue(), getAlpha());
	}
	
	/**
	 * Sets the green value in the range (0.0 - 1.0). Useful for when a Color object needs to be recycled.
	 *
	 * @return this Color object if the Color is not mutable, or a new Color object if this Color is set to be immutable.
	 */
	public ClearColor green(float g) {
		return set(getRed(), g, getGreen(), getAlpha());
	}
	
	/**
	 * Sets the blue value in the range (0.0 - 1.0). Useful for when a Color object needs to be recycled.
	 *
	 * @return this Color object if the Color is not mutable, or a new Color object if this Color is set to be immutable.
	 */
	public ClearColor blue(float b) {
		return set(getRed(), getGreen(), b, getAlpha());
	}

	/**
	 * Sets the alpha value in the range (0.0 - 1.0). Useful for when a Color object needs to be recycled.
	 * 
	 * @return this Color object if the Color is not mutable, or a new Color object if this Color is set to be immutable.
	 */
	public ClearColor alpha(float a) {
		return set(getRed(), getGreen(), getBlue(), a);
	}
	
	/**
	 * Sets this color with the given RGBA float values. If this Color is immutable, a new object will be returned. If it is not immutable, this object will be modified.
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @return
	 */
	public ClearColor set(float r, float g, float b, float a) {
		if (immutable) {
			return new ClearColor(r, g, b, a);
		} else if (color == null ) {
			color = new Vector4f(r, g, b, a);
		} else {
			color.set(r, g, b, a);
		}
			
		return this;
	}
	
	/**
	 * Allocates and returns a NanoVG color with the same component values. This version is not automatically freed, so you'll have to call free() on it once you're finished with it.
	 * @return
	 */
	public NVGColor callocNVG() {
		NVGColor color = NVGColor.calloc();
		return sync(color);
	}
	
	/**
	 * Creates and returns a NanoVG color that will work with the LWJGL3 memory stack technique.
	 * 
	 * @param stack
	 * @return
	 */
	public NVGColor mallocNVG(MemoryStack stack) {
		NVGColor color = NVGColor.mallocStack(stack);
		return sync(color);
	}
	
	private NVGColor sync(NVGColor color) {
		return color.r(getRed()).g(getGreen()).b(getBlue()).a(getAlpha());
	}
	
	/**
	 * Allows for automatic stack push allocated colors to be used (and automatically disposed after usage).
	 */
	public void memoryStackPush(ColorAction a) {
		try (MemoryStack stack = MemoryStack.stackPush()){
			NVGColor color = NVGColor.mallocStack(stack);
			a.execute(sync(color));
		}
	}
	
	public interface ColorAction {
		public void execute(NVGColor color);
	}
	
	public float getRed() {
		return color.x();
	}
	
	public float getGreen() {
		return color.y();
	}
	
	public float getBlue() {
		return color.z();
	}
	
	public float getAlpha() {
		return color.w();
	}

	/**
	 * Multiplies this color's RGB values (but not the alpha) by the factor and returns a new object containing the new colors.
	 * <br><br>
	 * The intended application for this function is to be able to brighten/darken colors as needed.
	 * 
	 * @param factor
	 * @return a new ClearColor object with the given settings
	 */
	public ClearColor multiply(float factor) {
		return new ClearColor(getRed() * factor, getGreen() * factor, getBlue() * factor, getAlpha());
	}

	/**
	 * Divides this color's RGB values (but not the alpha) by the quotient and returns a new object containing the new colors.
	 * <br><br>
	 * The intended application for this function is to be able to brighten/darken colors as needed.
	 * 
	 * @param quotient
	 * @return a new ClearColor object with the given settings
	 */
	public ClearColor divide(float quotient) {
		return new ClearColor(getRed() / quotient, getRed() / quotient, getRed() / quotient, getAlpha());
	}

	/**
	 * Returns whether or not the RGB values of the given color match this one.
	 * 
	 * @param color
	 * @return
	 */
	public boolean rgbMatches(ClearColor color) {
		return (getRed() == color.getRed() && getGreen() == color.getGreen() && getBlue() == color.getBlue());
	}
	
	/**
	 * Blends the to colors, gradually fading the "from" color to the "to" color based on the given normalized multiplier (a 0-1 value, where 1 is full transitioned).
	 * 
	 * @param from - starting color
	 * @param to - what color to transition to
	 * @param store - the Color object to store the blended colors into (to prevent making garbage)
	 * @param mult - how much of the transition is completed (0 = 100% the from color, 1 = 100% the to color)
	 * @return
	 */
	public static ClearColor blend(ClearColor from, ClearColor to, ClearColor store, float mult) {
		//May not be the best approach, but if it looks good, then whatever.
		float r1 = from.getRed();
		float g1 = from.getGreen();
		float b1 = from.getBlue();
		float a1 = from.getAlpha();
		
		float r2 = to.getRed();
		float g2 = to.getGreen();
		float b2 = to.getBlue();
		float a2 = to.getAlpha();
		
		store.set(mix(r1, r2, mult), mix(g1, g2, mult), mix(b1, b2, mult), mix(a1, a2, mult));
		
		return store;
	}
	
	public static float mix(float x, float y, float a){
		return x + (y-x)*a;
	}
}