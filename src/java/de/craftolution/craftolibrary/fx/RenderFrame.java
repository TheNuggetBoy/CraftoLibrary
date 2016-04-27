package de.craftolution.craftolibrary.fx;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.04.2016
 */
public class RenderFrame {

	RenderCanvas canvas = null;

	/** TODO: Documentation */
	public RenderFrame(final int width, final int height, final int buffering) {
		this.canvas = new RenderCanvas(width, height, buffering);
	}

	/** TODO: Documentation */
	public RenderFrame(final int width, final int height) {
		this(width, height, 3);
	}

	/** TODO: Documentation */
	public Graphics getGraphics() {
		final BufferStrategy bs = this.canvas.getBufferStrategy();
		if (bs == null) { this.canvas.createBufferStrategy(this.canvas.buffering); this.requestFocus(); }

		return this.canvas.getGraphics();
	}

	/** TODO: Documentation */
	public boolean isKeyPressed(final Key key) {
		return this.canvas.keys[key.getCode()];
	}

	/** TODO: Documentation */
	public int getWidth() { return this.canvas.getWidth(); }

	/** TODO: Documentation */
	public int getHeight() { return this.canvas.getHeight(); }

	/** TODO: Documentation */
	public boolean hasFocus() { return this.canvas.hasFocus(); }

	/** TODO: Documentation */
	public void requestFocus() { this.canvas.requestFocus(); }

	/** TODO: Documentation */
	public Canvas getCanvas() { return this.canvas; }

	/** TODO: Documentation */
	public void renderTo(final Container container) { container.add(this.canvas); }

	private static class RenderCanvas extends Canvas {

		private static final long serialVersionUID = 839766590404041914L;

		private final int buffering;
		final boolean[] keys = new boolean[128];

		RenderCanvas(final int width, final int height, final int buffering) {
			final Dimension dim = new Dimension(width, height);
			this.setMinimumSize(dim);
			this.setPreferredSize(dim);
			this.setMaximumSize(dim);

			this.buffering = buffering;
			this.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(final KeyEvent e) { }
				@Override
				public void keyPressed(final KeyEvent e) { RenderCanvas.this.keys[e.getKeyCode()] = true; }
				@Override
				public void keyReleased(final KeyEvent e) { RenderCanvas.this.keys[e.getKeyCode()] = false; }
			});
		}

	}

	/** TODO: Documentation */
	static enum Key {

		ENTER ("ENTER", 10),
		BACK_SPACE ("BACK_SPACE", 8),
		TAB ("TAB", 9),
		CANCEL ("CANCEL", 3),
		CLEAR ("CLEAR", 12),
		SHIFT ("SHIFT", 16),
		CONTROL ("CONTROL", 17),
		ALT ("ALT", 18),
		PAUSE ("PAUSE", 19),
		CAPS_LOCK ("CAPS_LOCK", 20),
		ESCAPE ("ESCAPE", 27),
		SPACE ("SPACE", 32),
		PAGE_UP ("PAGE_UP", 33),
		PAGE_DOWN ("PAGE_DOWN", 34),
		END ("END", 35),
		HOME ("HOME", 36),
		LEFT ("LEFT", 37),
		UP ("UP", 38),
		RIGHT ("RIGHT", 39),
		DOWN ("DOWN", 40),
		COMMA ("COMMA", 44),
		MINUS ("MINUS", 45),
		PERIOD ("PERIOD", 46),
		SLASH ("SLASH", 47),
		ZERO ("0", 48),
		ONE ("1", 49),
		TWO ("2", 50),
		THRE ("3", 51),
		FOUR ("4", 52),
		FIVE ("5", 53),
		SIX ("6", 54),
		SEVEN ("7", 55),
		EIGHT ("8", 56),
		NINE ("9", 57),
		SEMICOLON ("SEMICOLON", 59),
		EQUALS ("EQUALS", 61),
		A ("A", 65),
		B ("B", 66),
		C ("C", 67),
		D ("D", 68),
		E ("E", 69),
		F ("F", 70),
		G ("G", 71),
		H ("H", 72),
		I ("I", 73),
		J ("J", 74),
		K ("K", 75),
		L ("L", 76),
		M ("M", 77),
		N ("N", 78),
		O ("O", 79),
		P ("P", 80),
		Q ("Q", 81),
		R ("R", 82),
		S ("S", 83),
		T ("T", 84),
		U ("U", 85),
		V ("V", 86),
		W ("W", 87),
		X ("X", 88),
		Y ("Y", 89),
		Z ("Z", 90),
		OPEN_BRACKET ("OPEN_BRACKET", 91),
		BACK_SLASH ("BACK_SLASH", 92),
		CLOSE_BRACKET ("CLOSE_BRACKET", 93),
		NUMPAD0 ("NUMPAD0", 96),
		NUMPAD1 ("NUMPAD1", 97),
		NUMPAD2 ("NUMPAD2", 98),
		NUMPAD3 ("NUMPAD3", 99),
		NUMPAD4 ("NUMPAD4", 100),
		NUMPAD5 ("NUMPAD5", 101),
		NUMPAD6 ("NUMPAD6", 102),
		NUMPAD7 ("NUMPAD7", 103),
		NUMPAD8 ("NUMPAD8", 104),
		NUMPAD9 ("NUMPAD9", 105),
		MULTIPLY ("MULTIPLY", 106),
		ADD ("ADD", 107),
		SEPARATER ("SEPARATER", 108),
		SEPARATOR ("SEPARATOR", 108),
		SUBTRACT ("SUBTRACT", 109),
		DECIMAL ("DECIMAL", 110),
		DIVIDE ("DIVIDE", 111),
		DELETE ("DELETE", 127),
		NUM_LOCK ("NUM_LOCK", 144),
		SCROLL_LOCK ("SCROLL_LOCK", 145),
		F1 ("F1", 112),
		F2 ("F2", 113),
		F3 ("F3", 114),
		F4 ("F4", 115),
		F5 ("F5", 116),
		F6 ("F6", 117),
		F7 ("F7", 118),
		F8 ("F8", 119),
		F9 ("F9", 120),
		F10 ("F10", 121),
		F11 ("F11", 122),
		F12 ("F12", 123),
		F13 ("F13", 61440),
		F14 ("F14", 61441),
		F15 ("F15", 61442),
		F16 ("F16", 61443),
		F17 ("F17", 61444),
		F18 ("F18", 61445),
		F19 ("F19", 61446),
		F20 ("F20", 61447),
		F21 ("F21", 61448),
		F22 ("F22", 61449),
		F23 ("F23", 61450),
		F24 ("F24", 61451),
		PRINTSCREEN ("PRINTSCREEN", 154),
		INSERT ("INSERT", 155),
		HELP ("HELP", 156),
		META ("META", 157),
		BACK_QUOTE ("BACK_QUOTE", 192),
		QUOTE ("QUOTE", 222),
		KP_UP ("KP_UP", 224),
		KP_DOWN ("KP_DOWN", 225),
		KP_LEFT ("KP_LEFT", 226),
		KP_RIGHT ("KP_RIGHT", 227),
		DEAD_GRAVE ("DEAD_GRAVE", 128),
		DEAD_ACUTE ("DEAD_ACUTE", 129),
		DEAD_CIRCUMFLEX ("DEAD_CIRCUMFLEX", 130),
		DEAD_TILDE ("DEAD_TILDE", 131),
		DEAD_MACRON ("DEAD_MACRON", 132),
		DEAD_BREVE ("DEAD_BREVE", 133),
		DEAD_ABOVEDOT ("DEAD_ABOVEDOT", 134),
		DEAD_DIAERESIS ("DEAD_DIAERESIS", 135),
		DEAD_ABOVERING ("DEAD_ABOVERING", 136),
		DEAD_DOUBLEACUTE ("DEAD_DOUBLEACUTE", 137),
		DEAD_CARON ("DEAD_CARON", 138),
		DEAD_CEDILLA ("DEAD_CEDILLA", 139),
		DEAD_OGONEK ("DEAD_OGONEK", 140),
		DEAD_IOTA ("DEAD_IOTA", 141),
		DEAD_VOICED_SOUND ("DEAD_VOICED_SOUND", 142),
		DEAD_SEMIVOICED_SOUND ("DEAD_SEMIVOICED_SOUND", 143),
		AMPERSAND ("AMPERSAND", 150),
		ASTERISK ("ASTERISK", 151),
		QUOTEDBL ("QUOTEDBL", 152),
		LESS ("LESS", 153),
		GREATER ("GREATER", 160),
		BRACELEFT ("BRACELEFT", 161),
		BRACERIGHT ("BRACERIGHT", 162),
		AT ("AT", 512),
		COLON ("COLON", 513),
		CIRCUMFLEX ("CIRCUMFLEX", 514),
		DOLLAR ("DOLLAR", 515),
		EURO_SIGN ("EURO_SIGN", 516),
		EXCLAMATION_MARK ("EXCLAMATION_MARK", 517),
		INVERTED_EXCLAMATION_MARK ("INVERTED_EXCLAMATION_MARK", 518),
		LEFT_PARENTHESIS ("LEFT_PARENTHESIS", 519),
		NUMBER_SIGN ("NUMBER_SIGN", 520),
		PLUS ("PLUS", 521),
		RIGHT_PARENTHESIS ("RIGHT_PARENTHESIS", 522),
		UNDERSCORE ("UNDERSCORE", 523),
		WINDOWS ("WINDOWS", 524),
		CONTEXT_MENU ("CONTEXT_MENU", 525),
		FINAL ("FINAL", 24),
		CONVERT ("CONVERT", 28),
		NONCONVERT ("NONCONVERT", 29),
		ACCEPT ("ACCEPT", 30),
		MODECHANGE ("MODECHANGE", 31),
		KANA ("KANA", 21),
		KANJI ("KANJI", 25),
		ALPHANUMERIC ("ALPHANUMERIC", 240),
		KATAKANA ("KATAKANA", 241),
		HIRAGANA ("HIRAGANA", 242),
		FULL_WIDTH ("FULL_WIDTH", 243),
		HALF_WIDTH ("HALF_WIDTH", 244),
		ROMAN_CHARACTERS ("ROMAN_CHARACTERS", 245),
		ALL_CANDIDATES ("ALL_CANDIDATES", 256),
		PREVIOUS_CANDIDATE ("PREVIOUS_CANDIDATE", 257),
		CODE_INPUT ("CODE_INPUT", 258),
		JAPANESE_KATAKANA ("JAPANESE_KATAKANA", 259),
		JAPANESE_HIRAGANA ("JAPANESE_HIRAGANA", 260),
		JAPANESE_ROMAN ("JAPANESE_ROMAN", 261),
		KANA_LOCK ("KANA_LOCK", 262),
		INPUT_METHOD_ON_OFF ("INPUT_METHOD_ON_OFF", 263),
		CUT ("CUT", 65489),
		COPY ("COPY", 65485),
		PASTE ("PASTE", 65487),
		UNDO ("UNDO", 65483),
		AGAIN ("AGAIN", 65481),
		FIND ("FIND", 65488),
		PROPS ("PROPS", 65482),
		STOP ("STOP", 65480),
		COMPOSE ("COMPOSE", 65312),
		ALT_GRAPH ("ALT_GRAPH", 65406),
		BEGIN ("BEGIN", 65368),
		UNDEFINED ("UNDEFINED", 0),
		;

		private final String name;
		private final int code;

		private Key(final String name, final int code) {
			this.name = name.intern();
			this.code = code;
		}

		public String getName() { return this.name; }

		public int getCode() { return this.code; }
	}

}