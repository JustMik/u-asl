package it.justmik.dev.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.justmik.dev.controller.Controller;

public class View extends JFrame implements ActionListener{
	
	private JButton ret;
	private JPanel panelContent;
	private FocusablePanel panel;
	
	public View() {

		initialize();

	}

	private void initialize(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		ret = new JButton(){
			{
				setIcon(new ImageIcon(this.getClass()
						.getResource("/resources/img/Logout_orange.png")));
				setBorderPainted(false);
				setToolTipText("Torna al Login");
			}
			
			
			@Override
			public String toString() {
				return "RETURN";
			}
		};
		ret.addActionListener(this);
		
		panelContent = new JPanel(new BorderLayout());

		JPanel panelRet = new JPanel(new BorderLayout());
		BufferedImage logo = null;
		try {
			logo = ImageIO.read(this.getClass()
					.getResource("/resources/img/uandasl_logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel labelLogo = new JLabel(new ImageIcon(logo));
		
		panelRet.add(labelLogo, BorderLayout.WEST);
		
		panelRet.setPreferredSize(new Dimension(10, 55));
		panelRet.setBackground(new Color(220, 220, 220));
		
		panelRet.add(ret, BorderLayout.EAST);
		panelContent.add(panelRet, BorderLayout.NORTH);
		
		setContentPane(panelContent);
		
	}
	
	public View getInstance(){
		return this;
	}
	
	public void setPane(String title, FocusablePanel pane){
		this.panel = pane;
		setTitle(title);
		
		
		if (pane.toString().equals("LOGIN")){
			ret.setVisible(false);
		}
		else{
			ret.setVisible(true);
		}
		
		BorderLayout bl = (BorderLayout) panelContent.getLayout();
		FocusablePanel fo = (FocusablePanel) bl.getLayoutComponent(BorderLayout.CENTER);
		
		if (fo != null)
			panelContent.remove(fo);
		
		panelContent.add(panel, BorderLayout.CENTER);
		panelContent.repaint();
		
		panel.getFocus();
		revalidate();
		
		
	}
	
	@Override
	public JPanel getContentPane(){
		return panel;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String act = e.getSource().toString();
		if (act.equals("RETURN")){
			Controller.getInstance().displayContent(act, "ADMIN");
		}
	}
	
	
	
}
