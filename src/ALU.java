import java.awt.*;

public class ALU
extends Panel
implements ISRGInput, IMREvent
{
	private int f;
	private LEDPanel fpanel;
	private LEDPanel carrypanel;
	
	public ALU()
	{
		setLayout(null);
		
		fpanel = new LEDPanel(4, "F", null);
		carrypanel = new LEDPanel(1, "Carry", null);
		
		add(fpanel);
		add(carrypanel);

		autoSize();
	}
	
	public void autoSize()
	{
		int height = (fpanel.getHeight() > carrypanel.getHeight()) ? fpanel.getHeight() : carrypanel.getHeight();
		
		setSize(fpanel.getWidth() + carrypanel.getWidth() + 10, height);
		
		fpanel.setLocation(0, 0);
		carrypanel.setLocation(fpanel.getWidth()+10, 0);
	}
	
	public int getValue()
	{
		return f;
	}
	
	public int getMUX()
	{
		if((Minirechner.ram.getValue() & 0x100) != 0)
			return Minirechner.srf.getValue();
		else
			return Minirechner.sra.getValue();
	}

	public void procEvent(int event)
	{
		int carry = Minirechner.ram.getValue() >> 5;
		carry = ~carry;
		carry &= 0x1;
		
		int m = Minirechner.ram.getValue() >> 4;
		m &= 0x1;
		
		int select = Minirechner.ram.getValue() & 0xf;
		int srb = Minirechner.srb.getValue();
		
		if(m != 0) {
			if(select == 0x0) {
				f = ~getMUX();
			} else if(select == 0x1) {
				f = ~(getMUX() | srb);
			} else if(select == 0x2) {
				f = ~getMUX() & srb;
			} else if(select == 0x3) {
				f = 0;
			} else if(select == 0x4) {
				f = ~(getMUX() & srb);
			} else if(select == 0x5) {
				f = ~srb;
			} else if(select == 0x6) {
				f = getMUX() ^ srb;
			} else if(select == 0x7) {
				f = getMUX() & ~srb;
			} else if(select == 0x8) {
				f = ~getMUX() | srb;
			} else if(select == 0x9) {
				f = ~(getMUX() ^ srb);
			} else if(select == 0xa) {
				f = srb;
			} else if(select == 0xb) {
				f = getMUX() & srb;
			} else if(select == 0xc) {
				f = 1;
			} else if(select == 0xd) {
				f = getMUX() | ~srb;
			} else if(select == 0xe) {
				f = getMUX() | srb;
			} else if(select == 0xf) {
				f = getMUX();
			}
		} else {
			if(select == 0x0) {
				f = getMUX();
			} else if(select == 0x1) {
				f = getMUX() | srb;
			} else if(select == 0x2) {
				f = getMUX() | ~srb;
			} else if(select == 0x3) {
				f = -1;
			} else if(select == 0x4) {
				f = getMUX() + (getMUX() & ~srb);
			} else if(select == 0x5) {
				f = (getMUX() | srb) + (getMUX() & ~srb);
			} else if(select == 0x6) {
				f = getMUX() - srb - 1;
			} else if(select == 0x7) {
				f = (getMUX() & ~srb) - 1;
			} else if(select == 0x8) {
				f = getMUX() + (getMUX() & srb);
			} else if(select == 0x9) {
				f = getMUX() + srb;
			} else if(select == 0xa) {
				f = (getMUX() | ~srb) + (getMUX() & srb);
			} else if(select == 0xb) {
				f = (getMUX() & srb) - 1;
			} else if(select == 0xc) {
				f = getMUX() + getMUX();
			} else if(select == 0xd) {
				f = (getMUX() | srb) + getMUX();
			} else if(select == 0xe) {
				f = (getMUX() | ~srb) + getMUX();
			} else if(select == 0xf) {
				f = getMUX() - 1;
			}
			
			f += carry;
		}
		
		fpanel.setValue(f);
	}
}
