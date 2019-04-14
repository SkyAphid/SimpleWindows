package nokori.clear.vg.subapps;

import java.io.File;
import java.io.IOException;

import nokori.clear.vg.ClearApp;
import nokori.clear.vg.ClearColor;
import nokori.clear.vg.NanoVGContext;
import nokori.clear.vg.font.Font;
import nokori.clear.vg.font.FontStyle;
import nokori.clear.vg.widget.ButtonAssembly;
import nokori.clear.vg.widget.LabelWidget;
import nokori.clear.vg.widget.assembly.RootWidgetAssembly;
import nokori.clear.vg.widget.assembly.WidgetAssembly;
import nokori.clear.vg.widget.assembly.WidgetClip;
import nokori.clear.vg.widget.text.TextFieldWidget;
import nokori.clear.windows.GLFWException;
import nokori.clear.windows.Window;
import nokori.clear.windows.WindowManager;

/**
 * This ClearApp is an input window you can open and use to get user input. See ClearInputAppDemo.java to learn more about it.
 */
public abstract class ClearInputApp extends ClearApp {

	private static final int FONT_SIZE = 16;
	
	private int width, height;
	private ClearApp parent;
	private String title, message, defaultInput;
	private File fontLocation;
	
	public ClearInputApp(ClearApp parent, int windowWidth, int windowHeight, File fontLocation, String title, String message, String defaultInput) {
		this(parent.getWindowManager(), new RootWidgetAssembly(), parent, windowWidth, windowHeight, fontLocation, title, message, defaultInput);
	}
	
	private ClearInputApp(WindowManager windowManager, WidgetAssembly rootWidgetAssembly, 
			ClearApp parent, int windowWidth, int windowHeight, File fontLocation, String title, String message, String defaultInput) {
		
		super(windowManager, rootWidgetAssembly);
		this.parent = parent;
		this.width = windowWidth;
		this.height = windowHeight;
		this.fontLocation = fontLocation;
		this.title = title;
		this.message = message;
		this.defaultInput = defaultInput;
	}

	public static void show(ClearInputApp clearInputWindow) throws GLFWException {
		ClearApp parent = clearInputWindow.parent;
		parent.setPaused(true);
		parent.queueLaunch(clearInputWindow);
	}
	
	@Override
	public void init(WindowManager windowManager, Window window, NanoVGContext context, WidgetAssembly rootWidgetAssembly, String[] args) {
		
		float xPadding = 20f;
		float yPadding = 20f;

		try {
			Font font = new Font(fontLocation).load(context);
			
			/*
			 * Message
			 */
			
			float widgetWidth = width - (xPadding * 2f);
			
			LabelWidget messageLabel = new LabelWidget(widgetWidth, ClearColor.LIGHT_BLACK, message, font, FontStyle.REGULAR, FONT_SIZE).calculateBounds(context);
			messageLabel.addChild(new WidgetClip(WidgetClip.Alignment.TOP_LEFT, xPadding, yPadding));
			
			rootWidgetAssembly.addChild(messageLabel);
			
			/*
			 * Input field
			 */
			
			TextFieldWidget inputField = new TextFieldWidget(widgetWidth, ClearColor.LIGHT_BLACK, defaultInput, font, FONT_SIZE);
			inputField.setBackgroundFill(ClearColor.LIGHT_GRAY);
			inputField.addChild(new WidgetClip(WidgetClip.Alignment.TOP_LEFT, xPadding, (yPadding * 2f) + messageLabel.getHeight()));
			
			rootWidgetAssembly.addChild(inputField);
			
			/*
			 * Confirm button
			 */
			
			float buttonSynchX = xPadding;
			float buttonSynchY = (yPadding * 4f) + messageLabel.getHeight() + inputField.getHeight();
			
			ButtonAssembly confirmButton = new ButtonAssembly(75, 25, ClearColor.LIGHT_GRAY, ClearColor.BABY_BLUE, 0f, font, FONT_SIZE, ClearColor.LIGHT_BLACK, "Confirm");
			confirmButton.addChild(new WidgetClip(WidgetClip.Alignment.TOP_LEFT, buttonSynchX, buttonSynchY));
			
			confirmButton.setOnMouseButtonEvent(e -> {
				if (confirmButton.isMouseWithin() && !e.isPressed()) {
					confirmButtonPressed(inputField.getTextBuilder().toString());
					window.requestClose();
				}
			});
			
			rootWidgetAssembly.addChild(confirmButton);
			
			/*
			 * Exit button
			 */

			ButtonAssembly exitButton = new ButtonAssembly(75, 25, ClearColor.LIGHT_GRAY, ClearColor.BABY_BLUE, 0f, font, FONT_SIZE, ClearColor.LIGHT_BLACK, "Cancel");
			exitButton.addChild(new WidgetClip(WidgetClip.Alignment.TOP_LEFT, buttonSynchX + confirmButton.getWidth() + xPadding, buttonSynchY));
			
			exitButton.setOnMouseButtonEvent(e -> {
				if (exitButton.isMouseWithin() && !e.isPressed()) {
					window.requestClose();
				}
			});
			
			rootWidgetAssembly.addChild(exitButton);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void confirmButtonPressed(String text);

	@Override
	protected void endOfNanoVGApplicationCallback() {
		parent.setPaused(false);
	}

	@Override
	public Window createWindow(WindowManager windowManager) throws GLFWException {
		return windowManager.createWindow(title, width, height, false, true);
	}

	@Override
	protected boolean exitProgramOnEndOfApplication() {
		return false;
	}
}
