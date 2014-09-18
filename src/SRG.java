import java.awt.Panel;

public class SRG
extends Panel
implements IMREvent
{
	private String title;
	private ISRGInput input;
	private LEDPanel panel;
	private int s0mask;
	private int s1mask;
	private int sirmask;
	private int silmask;
	private int value;
	
	public SRG(String title, ISRGInput input, int s0mask, int s1mask, int sirmask, int silmask)
	{
		this.title = title;
		this.input = input;
		panel = new LEDPanel(4, title, null);
		this.s0mask = s0mask;
		this.s1mask = s1mask;
		this.sirmask = sirmask;
		this.silmask = silmask;
		
		setLayout(null);
		
		add(panel);
		autoSize();
	}
	
	public void autoSize()
	{
		setSize(panel.getSize());
	}
	
	public int getValue()
	{
		return value;
	}

	public void procEvent(int event)
	{
		if(event == IMREvent.RESET) {
			value = 0;
		} else if(event == IMREvent.TAKT) {
			int opcode = Minirechner.ram.getValue();
			
			if((opcode & s0mask) != 0) {
				if((opcode & s1mask) != 0)
					value = input.getValue();
				else {
					value <<= 1;
					
					if((opcode & sirmask) != 0)
						value |= 0x1;
				}
			} else {
				if((opcode & s1mask) != 0) {
					value >>= 1;
				
					if((opcode & sirmask) != 0)
						value |= 0x4;
				}
			}
			
			value &= 0xf;
		}
		
		panel.setValue(value);
	}
}
