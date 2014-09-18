import java.awt.Panel;

public class Programm
extends Panel
implements IMREvent
{
	private LEDPanel programm;
	private int progsrval;
	
	public Programm()
	{
		progsrval = 0;
		
		this.setLayout(null);
		String[] asd = { "S0", "S1", "S2", "S3", "M", "!CN", "S0", "S1", "SEL", "S0", "S1", "SIR", "SIL", "S0", "S1", "" };
		
		programm = new LEDPanel(16, "Programm", asd);
		add(programm);
		
		autoSize();
	}
	
	public int getValue()
	{
		return progsrval;
	}

	public void autoSize()
	{
		setSize(programm.getWidth(), programm.getHeight());
	}
		
	public void updateLEDs()
	{
		int actvalue;
		
		if(Minirechner.writebox.getState()) {
			actvalue = progsrval;
		} else {
			actvalue = Minirechner.ram.getValue();
		}
		
		programm.setValue(actvalue);
	}

	public void procEvent(int event)
	{
		if(event == IMREvent.PROGRESET) {
			progsrval = 0;
		} else if(event == IMREvent.ONE) {
			progsrval <<= 1;
			progsrval |= 0x1;
		} else if(event == IMREvent.ZERO) {
			progsrval <<= 1;
		}
		
		progsrval &= 0xffff;
		
		updateLEDs();
	}
}
