import java.awt.*;

public class InputField
extends Panel
implements ISRGInput
{
	private static final int BOXSIZE = 20;
	
	private int size;
	private String title;
	private Checkbox[] boxes;
	
	public InputField(String title, int size)
	{
		this.size = size;
		this.title = title;
		boxes = new Checkbox[size];
		
		for(int i = size-1; i >= 0; --i) {
			boxes[i] = new Checkbox("");
			boxes[i].setSize(BOXSIZE, BOXSIZE);
			add(boxes[i]);
		}
		
		autoSize();
	}
	
	public int getValue()
	{
		int value = 0;
		
		for(int i = 0; i < size; ++i) {
			value <<= 1;
			
			if(boxes[i].getState())
				value |= 0x1;
		}
		
		return value;
	}
	
	public void autoSize()
	{
		int width = (BOXSIZE*size > Minirechner.fm.stringWidth(title)) ? BOXSIZE*size : Minirechner.fm.stringWidth(title);
		
		setSize(width, Minirechner.fm.getHeight()+5+BOXSIZE);
	}
	
	public void paint(Graphics g)
	{
		g.setFont(Minirechner.font);
				
		g.drawString(title, 0, Minirechner.fm.getHeight());
		
		for(int i = size-1; i >= 0; --i) {
			boxes[i].setLocation(i*BOXSIZE, Minirechner.fm.getHeight()+5);
		}
		super.paint(g);
	}
}
