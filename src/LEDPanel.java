import java.awt.*;

class LEDPanel
extends Canvas
{
	private static final Color ONCOLOR = Color.RED;
	private static final Color OFFCOLOR = Color.BLACK;
	private static final int LEDWIDTH = 10;
	private static final int LEDHEIGHT = 10;
	private static final int LEDTEXTSPC = 5;
	private static final int LEDLEDSPC = 5;
	
	private String title;
	private String[] ledtitle;
	private int size;
	private int val;
	
	private int[] ledwidth;
	private int gesledwidth;
	
	LEDPanel(int size, String title, String[] ledtitle)
	{
		this.size = size;
		this.title = title;
		this.ledtitle = ledtitle;

		if((ledtitle != null) && (ledtitle.length != size)) {
			this.ledtitle = null;
		}
		
		autoSize();
	}
	
	public void setValue(int val)
	{
		this.val = val;
		repaint();
	}
	
	public void autoSize()
	{
		FontMetrics fm = Minirechner.fm;
		
		int width = fm.stringWidth(title);
		int height = fm.getHeight() + LEDTEXTSPC + LEDHEIGHT;
		ledwidth = new int[size];
		gesledwidth = 0;

		for(int i = 0; i < size; ++i) {
			ledwidth[i] = LEDWIDTH;
			
			if(ledtitle != null) {
				if(fm.stringWidth(ledtitle[i]) > LEDWIDTH)
					ledwidth[i] = fm.stringWidth(ledtitle[i]);
			}
			
			gesledwidth += ledwidth[i];
		}
		
		if((LEDLEDSPC * (size-1) + gesledwidth) > width)
			width = LEDLEDSPC * (size-1) + gesledwidth;
		
		if(ledtitle != null)
			height += LEDTEXTSPC + fm.getHeight();
		
		this.setSize(width, height);
	}
	
	public void paint(Graphics g)
	{
		FontMetrics fm = Minirechner.fm;
		int mask = (int)Math.pow(2, size-1);
		g.setFont(Minirechner.font);
		
		int titlex = getWidth()/2 - fm.stringWidth(title)/2;
		int ledy = fm.getHeight() + LEDTEXTSPC;
		int lty = getHeight() - fm.getDescent() - 2;
		
		for (int i = size-1; i >= 0; --i) {
			int ledx = 0;
			int ltx = 0;
			
			if((val & mask) != 0) {
				g.setColor(ONCOLOR);
			} else {
				g.setColor(OFFCOLOR);
			}
					
			for(int j = size-1; j > i; --j) {
				ledx += ledwidth[j] + LEDLEDSPC;
				ltx += ledwidth[j] + LEDLEDSPC;
			}
			ledx += ledwidth[i]/2;
			
			if((LEDLEDSPC * size + gesledwidth) < fm.stringWidth(title))
				ledx += fm.stringWidth(title)/2 - (LEDLEDSPC * size + gesledwidth - LEDLEDSPC)/2;
			
			g.fillOval(ledx - LEDWIDTH/2, ledy, LEDWIDTH, LEDHEIGHT);
			g.setColor(Color.BLACK);
			
			if(ledtitle != null)
				g.drawString(ledtitle[i], ledx - fm.stringWidth(ledtitle[i])/2, lty);
			
			mask >>= 1;
		}
		
		g.drawString(title, titlex, fm.getHeight() - 2);
	}
}