import java.awt.*;
import java.awt.event.*;

public class DatenTakt
extends Panel
implements MouseListener
{
	private LEDPanel daten;
	private LEDPanel takt;
	
	public DatenTakt()
	{
		this.setLayout(null);
		
		daten = new LEDPanel(1, "Daten", null);
		takt = new LEDPanel(1, "Takt", null);
		
		add(daten);
		add(takt);
				
		autoSize();
		
		Minirechner.onebtn.addMouseListener(this);
		Minirechner.zerobtn.addMouseListener(this);
	}
	
	public void autoSize()
	{
		int height = (daten.getHeight() > takt.getHeight()) ? daten.getHeight() : takt.getHeight();
		setSize(daten.getWidth() + 5 + takt.getWidth(), height);
		
		daten.setLocation(0, 0);
		takt.setLocation(daten.getWidth() + 5, 0);
	}

	public void mouseClicked(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0)
	{
		if(arg0.getSource() == Minirechner.onebtn)
			daten.setValue(0x1);
		else if(arg0.getSource() == Minirechner.zerobtn)
			daten.setValue(0x0);
		
		takt.setValue(0x1);
	}

	public void mouseReleased(MouseEvent arg0)
	{
		takt.setValue(0x0);
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}
}
