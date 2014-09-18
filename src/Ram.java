import java.awt.Panel;

public class Ram
extends Panel
implements IMREvent
{
	short[] ram;
	LEDPanel addrpanel;
	LEDPanel a8panel;
	int address;
	
	public Ram()
	{
		ram = new short[2048];
		addrpanel = new LEDPanel(8, "A7-A0", null);
		a8panel = new LEDPanel(1, "A8", null);
		address = 0;
		
		setLayout(null);
		
		add(addrpanel);
		add(a8panel);

		autoSize();
	}
	
	public void autoSize()
	{
		int height = (addrpanel.getHeight() > a8panel.getHeight()) ? addrpanel.getHeight() : a8panel.getHeight();
		
		setSize(addrpanel.getWidth() + a8panel.getWidth() + 10, height);
		
		a8panel.setLocation(0, 0);
		addrpanel.setLocation(a8panel.getWidth() + 10, 0);
	}
	
	public int getValue()
	{
		return ram[address];
	}

	public void updateAddress()
	{
		if(Minirechner.a8modebox.getState()) {
			if(Minirechner.a8box.getState())
				address |= 0x100;
			else
				address &= 0xff;
		} else {
			address &= 0xff;
			address |= (Minirechner.sra.getValue() << 5) & 0x100;
		}
		
		addrpanel.setValue(address & 0xff);
		a8panel.setValue(address >> 8);
	}

	public void procEvent(int event)
	{
		if(event == IMREvent.RESET) {
			address = 0;
		} else if(event == IMREvent.LOAD) {
			if(Minirechner.writebox.getState())
				ram[address] = (short)Minirechner.progsr.getValue();
			return;
		} else if(event == IMREvent.TAKT) {
			address += 1;
		}
		
		address &= 0xff;
		updateAddress();
	}
}
