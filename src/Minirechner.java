import java.applet.Applet;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Minirechner
extends Applet
implements MouseListener, ItemListener
{
	public static final String BONE = "1";
	public static final String BZERO = "0";
	public static final String BPR = "Prog.-Reset";
	public static final String BRESET = "Reset";
	public static final String BTAKT = "Takt";
	public static final String BA8 = "A8";
	public static final String BA8MODE = "A8 manuell";
	public static final String BLOAD = "Load";
	public static final String BWRITE = "Write";
	public static Font font;
	public static FontMetrics fm;
	
	public static Button progresetbtn;
	public static Button onebtn;
	public static Button zerobtn;
	public static Button resetbtn;
	public static Button taktbtn;
	public static Checkbox a8box;
	public static Checkbox a8modebox;
	public static Button loadbtn;
	public static Checkbox writebox;
	public static InputField adaten;
	public static InputField bdaten;

	public static Programm progsr;
	public static Ram ram;
	public static ALU alu;
	public static SRG sra;
	public static SRG srb;
	public static SRG srf;
	
	public void init() {
		font = new Font("Arial", Font.PLAIN, 12);
		fm = getFontMetrics(font);
		
		this.setSize(500, 400);
		this.setLayout(null);
		
		onebtn = new Button(BONE);
		onebtn.setFont(font);
		
		zerobtn = new Button(BZERO);
		zerobtn.setFont(font);
		
		progresetbtn = new Button(BPR);
		progresetbtn.setFont(font);
		
		resetbtn = new Button(BRESET);
		resetbtn.setFont(font);
		
		taktbtn = new Button(BTAKT);
		taktbtn.setFont(font);
		
		loadbtn = new Button(BLOAD);
		loadbtn.setFont(font);
		
		a8box = new Checkbox(BA8);
		a8box.setFont(font);
		
		a8modebox = new Checkbox(BA8MODE, true);
		a8modebox.setFont(font);
		
		writebox = new Checkbox(BWRITE, true);
		writebox.setFont(font);
		
		adaten = new InputField("Daten A", 4);
		adaten.setFont(font);
		bdaten = new InputField("Daten B", 4);
		bdaten.setFont(font);
		
		onebtn.addMouseListener(this);
		zerobtn.addMouseListener(this);
		progresetbtn.addMouseListener(this);
		resetbtn.addMouseListener(this);
		taktbtn.addMouseListener(this);
		loadbtn.addMouseListener(this);
		a8box.addItemListener(this);
		a8modebox.addItemListener(this);
		writebox.addItemListener(this);
		
		onebtn.setLocation(10,10);
		zerobtn.setLocation(40,10);
		progresetbtn.setLocation(10,40);
		resetbtn.setLocation(10,70);
		taktbtn.setLocation(10,100);
		loadbtn.setLocation(10,130);
		a8box.setLocation(10,160);
		a8modebox.setLocation(10,190);
		writebox.setLocation(10,220);
		adaten.setLocation(10,270);
		bdaten.setLocation(10,320);
		
		add(onebtn);
		add(zerobtn);
		add(progresetbtn);
		add(resetbtn);
		add(taktbtn);
		add(loadbtn);
		add(a8box);
		add(a8modebox);
		add(writebox);
		add(adaten);
		add(bdaten);
		
		ram = new Ram();
		ram.setLocation(150, 120);
		add(ram);
		
		DatenTakt t = new DatenTakt();
		t.setLocation(150, 10);
		add(t);
		
		progsr = new Programm();
		progsr.setLocation(150, 50);
		add(progsr);
		
		alu = new ALU();
		alu.setLocation(150, 220);
		add(alu);
		
		sra = new SRG("SRG A", adaten, 0x200, 0x400, 0x800, 0x1000);
		sra.setLocation(150, 170);
		add(sra);

		srb = new SRG("SRG B", bdaten, 0x2000, 0x4000, 0x0, 0x0);
		srb.setLocation(220, 170);
		add(srb);
		
		srf = new SRG("SRG F", alu, 0x40, 0x80, 0x0, 0x0);
		srf.setLocation(290, 170);
		add(srf);
		
		sizeComponents();
		alu.procEvent(0);
		
		//loadExample(0);*/
	}
	
	/*void loadExample(int what) {
		ram[0] = 0x6600;
		
		ram[1] = 0x40;
		
		ram[2] = 0x200;
		ram[3] = 0x40;
		ram[4] = 0x200;
		ram[5] = 0x40;
		ram[6] = 0x200;
		ram[7] = 0x0;
		ram[8] = 0x200;
		
		ram[257] = 0x1e9;
		ram[258] = 0x240;
		ram[259] = 0x1e9;
		ram[260] = 0x240;
		ram[261] = 0x1e9;
		ram[262] = 0x240;
		ram[263] = 0x1e9;
		ram[264] = 0x200;
	}*/
	
	public void sizeComponents()
	{
		onebtn.setSize(fm.stringWidth(BONE) + 10, fm.getHeight() + 10);
		zerobtn.setSize(fm.stringWidth(BZERO) + 10, fm.getHeight() + 10);
		progresetbtn.setSize(fm.stringWidth(BPR) + 10, fm.getHeight() + 10);
		resetbtn.setSize(fm.stringWidth(BRESET) + 10, fm.getHeight() + 10);
		taktbtn.setSize(fm.stringWidth(BTAKT) + 10, fm.getHeight() + 10);
		loadbtn.setSize(fm.stringWidth(BLOAD) + 10, fm.getHeight() + 10);
		a8box.setSize(fm.stringWidth(BA8) + 30, fm.getHeight() + 10);
		a8modebox.setSize(fm.stringWidth(BA8MODE) + 30, fm.getHeight() + 10);
		writebox.setSize(fm.stringWidth(BWRITE) + 30, fm.getHeight() + 10);
	}

	public void itemStateChanged(ItemEvent arg0)
	{
		int event = 0;		
		
		if(arg0.getSource() == a8box)
			event = IMREvent.A8;
		else if(arg0.getSource() == a8modebox)
			event = IMREvent.A8;
		else if(arg0.getSource() == writebox)
			event = IMREvent.WRITE;
		
		sendEvent(event);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		int event = 0;
		
		if(e.getSource() == progresetbtn)
			event = IMREvent.PROGRESET;
		else if(e.getSource() == onebtn)
			event = IMREvent.ONE;
		else if(e.getSource() == zerobtn)
			event = IMREvent.ZERO;
		else if(e.getSource() == resetbtn)
			event = IMREvent.RESET;
		else if(e.getSource() == taktbtn)
			event = IMREvent.TAKT;
		else if(e.getSource() == loadbtn)
			event = IMREvent.LOAD;
		
		sendEvent(event);
	}
	
	void sendEvent(int event) {
		sra.procEvent(event);
		srb.procEvent(event);
		alu.procEvent(event);
		srf.procEvent(event);
		ram.procEvent(event);
		progsr.procEvent(event);
		alu.procEvent(event);
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}