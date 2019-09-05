// Shreyas Vaderiyattil
package drawJuliaSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;

public class drawJuliaSet extends JPanel implements AdjustmentListener,ActionListener {
	
	JFrame frame;
	JScrollBar scrollBar[] = new JScrollBar[6];
	int w,h;
	int[] color;
	JCheckBox invert;
	double a = 0.0;
	double b = 0.0;
	double zFinal = 1.0;
	float sat = 1;
	float hue = 0;
	float bright = 1;
	
	JLabel aLabel;
	JLabel bLabel;
	JLabel zoomLabel;
	JLabel hueLabel;
	JLabel satLabel;
	JLabel brightLabel;
	
	boolean f = true;
	
	
	public drawJuliaSet() {
		
		frame = new JFrame("Julia Set");
		frame.add(this);
		frame.setSize(1000,800);
		color=new int[3];
		invert=new JCheckBox();
		invert.addActionListener(this);
		
		JPanel scrollPanel=new JPanel();
		JPanel labelPanel=new JPanel();
		scrollPanel.setLayout(new GridLayout(6,1));
		labelPanel.setLayout(new GridLayout(6,1));
		
		for(int i=0;i<2;i++) {
			
			scrollBar[i] = new JScrollBar(JScrollBar.HORIZONTAL,0,0,-1000,1000);
			scrollBar[i].addAdjustmentListener(this);
			scrollBar[i].setUnitIncrement(1);
			scrollPanel.add(scrollBar[i]);
		}
		
		scrollBar[2]=new JScrollBar(JScrollBar.HORIZONTAL,250,0,1,500);
		scrollBar[2].addAdjustmentListener(this);
		scrollBar[2].setUnitIncrement(1);
		scrollPanel.add(scrollBar[2]);
		
		scrollBar[3]=new JScrollBar(JScrollBar.HORIZONTAL,-100,0,-100,0);
		scrollBar[3].addAdjustmentListener(this);
		scrollBar[3].setUnitIncrement(1);
		scrollPanel.add(scrollBar[3]);
		
		scrollBar[4]=new JScrollBar(JScrollBar.HORIZONTAL,-100,0,-100,0);
		scrollBar[4].addAdjustmentListener(this);
		scrollBar[4].setUnitIncrement(1);
		scrollPanel.add(scrollBar[4]);
		
		scrollBar[5]=new JScrollBar(JScrollBar.HORIZONTAL,-100,0,-100,0);
		scrollBar[5].addAdjustmentListener(this);
		scrollBar[5].setUnitIncrement(1);
		scrollPanel.add(scrollBar[5]);
		
		
		
		JPanel total = new JPanel();
		
		total.setLayout(new BorderLayout());
		total.add(scrollPanel,BorderLayout.CENTER);
		total.add(invert,BorderLayout.EAST);
		total.add(labelPanel,BorderLayout.WEST);
		frame.add(total,BorderLayout.SOUTH);
		
		aLabel = new JLabel("A");
		bLabel = new JLabel("B");
		zoomLabel = new JLabel("Zoom");
		satLabel = new JLabel("Saturation");
		hueLabel = new JLabel("Hue");
		brightLabel = new JLabel("Brightness");

		labelPanel.add(aLabel);
		labelPanel.add(bLabel);
		labelPanel.add(zoomLabel);
		labelPanel.add(satLabel);
		labelPanel.add(hueLabel);
		labelPanel.add(brightLabel);
		
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void drawJulia(Graphics g) {
		
		int w = frame.getWidth();
		int h = frame.getHeight();
		double zoom = zFinal;
		float flo = 0;
		float iterator = 300;
		double zx = 0.0;
		double zy = 0.0;
		BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		
		for(int x=0;x<w;x++) {
			for(int y = 0;y<h;y++) {
				flo = iterator;
				 zx = 1.5 * (x - w / 2) / (0.5 * zoom * w)+0.0;
				 zy = (y - h / 2) / (0.5 * zoom * h)+0.0;
				 
				 while ( (zx*zx) + (zy*zy) < 6 && flo > 0 )
	                {
						double d = (zx*zx) - (zy * zy) + a;
						zy = 2.0 * zx*zy + b;
						zx = d;
						flo--;
					}
				 
				 int col = 0;
					if(flo > 0)
						col = Color.HSBtoRGB((iterator / flo+hue) % 1, sat, bright);
					else
						col = Color.HSBtoRGB(iterator / flo, 1, 0);

					image.setRGB(x,y,col);

				
			}
		}
		g.drawImage(image,0,0,null);
		
		
	
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(color[0],color[1],color[2]));
		g.fillRect(0,0,frame.getWidth(),frame.getHeight());
		drawJulia(g);
		
	}
	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==invert)
		{
			int t = scrollBar[0].getValue();
			scrollBar[0].setValue(scrollBar[1].getValue());
			scrollBar[1].setValue(t);
		}

		repaint();
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		
		double temp0=scrollBar[0].getValue();
		double temp1=scrollBar[1].getValue();
		double temp2=scrollBar[2].getValue();
		float temp3=scrollBar[3].getValue();
		float temp4=scrollBar[4].getValue();
		float temp5=scrollBar[5].getValue();

		a=temp0/1000.0;
		b=temp1/1000.0;
		zFinal = temp2/250.0;
		sat = Math.abs(temp3/100.0f);
		hue = Math.abs(temp4/100.0f);
		bright = Math.abs(temp5/100.0f);

		repaint();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		drawJuliaSet app = new drawJuliaSet();
	}

}
