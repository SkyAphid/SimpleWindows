package nokori.clear.vg;

import nokori.clear.vg.ClearColor;

import java.io.IOException;
import nokori.clear.vg.ClearApplication;
import nokori.clear.vg.NanoVGContext;
import nokori.clear.vg.font.Font;
import nokori.clear.vg.widget.DropShadowWidget;
import nokori.clear.vg.widget.RectangleWidget;
import nokori.clear.vg.widget.assembly.WidgetAssembly;
import nokori.clear.vg.widget.assembly.WidgetClip;
import nokori.clear.vg.widget.text.TextAreaWidget;
import nokori.clear.windows.GLFWException;
import nokori.clear.windows.Window;
import nokori.clear.windows.WindowManager;

import static nokori.clear.vg.widget.text.ClearEscapeSequences.*;

/**
 * This demo show-cases the TextArea functionality available in ClearVG.
 *
 */
public class ClearTextAreaDemo extends ClearApplication {

	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;
	
	protected TextAreaWidget textAreaWidget;
	
	public static void main(String[] args) {
		ClearApplication.launch(new ClearTextAreaDemo(), args);
	}

	public ClearTextAreaDemo() {
		super(new WidgetAssembly());
	}
	
	@Override
	public void init(WindowManager windowManager, Window window, NanoVGContext context, WidgetAssembly rootWidgetAssembly, String[] args) {
		//WidgetAssemblies act as containers for various widgets. This will allow you to "assemble" a variety of UI components.
		WidgetAssembly button = new WidgetAssembly(1000, 500, new WidgetClip(WidgetClip.Alignment.CENTER));
		
		button.addChild(new DropShadowWidget(ClearColor.LIGHT_BLACK));
		button.addChild(new RectangleWidget(ClearColor.WHITE_SMOKE, ClearColor.LIGHT_GRAY));
		
		try {
			Font font = new Font("fonts/NotoSans/", "NotoSans-Regular", "NotoSans-Bold", "NotoSans-Italic", "NotoSans-Light").load(context);

			textAreaWidget = new TextAreaWidget(900, 400, ClearColor.BLACK, getText(), font, 18);
			
			textAreaWidget.addChild(new WidgetClip(WidgetClip.Alignment.CENTER));
			button.addChild(textAreaWidget);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		rootWidgetAssembly.addChild(button);
	}

	protected String getText() {
		String s = "";
		
		for (int j = 0; j < 50; j++) {
			if (j > 0) {
				s += "\n\n";
			}
			
			String b = Character.toString(ESCAPE_SEQUENCE_BOLD);
			String c = Character.toString(ESCAPE_SEQUENCE_COLOR);
			String i = Character.toString(ESCAPE_SEQUENCE_ITALIC);
			String r = Character.toString(ESCAPE_SEQUENCE_RESET);
			
			//You can either tab or use \t - either works. You can also reference escape sequences via strings or use the constants I've provided.
			s += b + c + "#FF7F50Hello World!" + r + " " + i + "This is entry number " + j + ":" + r + "\n";
			s += "\tLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ";
			s += "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ";
			s += "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
		}
		
		return s;
	}

	@Override
	protected void endOfNanoVGApplicationCallback() {
		
	}

	@Override
	public Window createWindow(WindowManager windowManager) throws GLFWException {
		return windowManager.createWindow("Hello World!", WINDOW_WIDTH, WINDOW_HEIGHT, true, true);
	}

}
