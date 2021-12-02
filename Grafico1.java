import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Grafico1 extends JFrame {

    private JPanel contentPane;
    public ArrayList<Punto_Skyline> P = new ArrayList<Punto_Skyline>(); 
    

    /**
     * Create the frame.
     */
    public Grafico1(ArrayList<Punto_Skyline> P) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBounds(0,0,1000,1000);
        this.P = P;
    }
    
    public void paint (Graphics g)
    {
        int x = 500, y = 500;
        int nuevox, nuevoy;
        g.drawLine (0,500,1000,500);
        g.drawLine (500,0,500,1000);
        for(Punto_Skyline p: P){
            nuevox = 500+(p.getX()*50);
            nuevoy = 500-(p.getY()*50);
            g.drawLine (x, y, nuevox, y);
            g.drawLine (nuevox, y, nuevox, nuevoy);
            x = nuevox;
            y = nuevoy;
        }
        g.drawLine (x, y, x, 500);
        x = 500;
        y = 500;
    }
}