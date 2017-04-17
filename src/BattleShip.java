import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class BattleShip extends JFrame
{
	
	protected JFrame GameBoard = new JFrame();
	
	protected JTextField Result = new JTextField("Try to Sink my BattleShip");

	protected Border border = LineBorder.createGrayLineBorder();
	
	protected JPanel Main = new JPanel();
	protected JPanel GameField = new JPanel();
	protected JPanel StatsPanel = new JPanel();
	protected JPanel HitsPanel = new JPanel();
	protected JPanel MissPanel = new JPanel();
	protected JPanel SunkPanel = new JPanel();
	protected JPanel Buttons = new JPanel();
	
	protected JLabel Hits = new JLabel("Hits");
	protected JLabel HitsCount = new JLabel("0");
	protected JLabel Miss = new JLabel("Miss");
	protected JLabel MissCount = new JLabel("0");
	protected JLabel Sunk = new JLabel("Sunk");
	protected JLabel SunkCount = new JLabel("0");
	
	protected JButton[] BtnList = new JButton[100];
	protected JButton Btn_Exit = new JButton("EXIT");
	protected JButton Btn_NewGame = new JButton("New Game");
	
	
	protected Font S = new Font("Times New Roman",Font.PLAIN,40);
	protected Font D = new Font("Times New Roman",Font.PLAIN,30);
	
	public BattleShip()
	{
		initFrame();
	}

	public void initFrame()
	{
		GameBoard.setTitle("BattleShip!");
		GameBoard.setSize(800,600);
		GameBoard.setLayout(new BorderLayout(0,0));
		
		initStatsPanel();
		initGamePanel();
		GameBoard.add(Main);
		Main.setSize(800,600);
		Main.setLayout(new BorderLayout(0,0));
		Main.add(Result,BorderLayout.NORTH);
		Main.add(StatsPanel,BorderLayout.WEST);
		Main.add(GameField,BorderLayout.CENTER);
		initExit();
		GameBoard.setVisible(true);
		
		
	}
	public void initStatsPanel()
	{
		StatsPanel.setLayout(new GridLayout(4,1));
		InitSubPanels();
		StatsPanel.add(HitsPanel);
		StatsPanel.add(MissPanel);
		StatsPanel.add(SunkPanel);
		StatsPanel.add(Buttons);
	}
	
	public void initGamePanel()
	{
		GameField.setLayout(new GridLayout(10,10,1,1));
		initGameButtons();
	}
	
	public void initGameButtons()
	{
		char Column ='A';
		for (int i = 0;i<100;i++)
		{
			BtnList[i] = new JButton();
			BtnList[i].setText(Column+Integer.toString((i%10)+1));
			
			BtnList[i].addActionListener(new ActionListener()
			{
			    @Override
			    public void actionPerformed(ActionEvent e)
			    {
			    	JButton button = (JButton) e.getSource();
			    	
			    	button.setEnabled(false);
			    	if((int)(button.getClientProperty("Ship"))==1)
					{
			    		button.setBackground(Color.RED);
			    		if(Integer.parseInt(HitsCount.getText())<5)
			    		{
			    			HitsCount.setText(String.valueOf((Integer.parseInt(HitsCount.getText())+1)));
			    			Result.setText(button.getText()+ " : HIT");
			    			
			    		}
			    		else
			    		{
			    			HitsCount.setText("0");
			    			SunkCount.setText(String.valueOf(Integer.parseInt(SunkCount.getText())+1));
			    			
			    			for(int j= 0;j<100;j++)
			    			{
			    				BtnList[j].setEnabled(false);
				
			    			}
			    			JOptionPane.showMessageDialog(GameBoard, "You Sunk My BattleShip!");
			    			
			    			
			    		}
					}
					else
					{
						button.setBackground(Color.blue);
						MissCount.setText(String.valueOf((Integer.parseInt(MissCount.getText())+1)));
						Result.setText(button.getText()+ " : Miss");
			    		
					}
				}
			});
			BtnList[i].putClientProperty("Ship", 0);
			if(((i+1)%10)==0)
			{
				Column++;
			}
			GameField.add(BtnList[i]);
		}
		SetShip();
	
	}

	public void InitSubPanels()
	{
		
		HitsPanel.setLayout(new GridLayout(2,1));
		MissPanel.setLayout(new GridLayout(2,1));
		SunkPanel.setLayout(new GridLayout(2,1));
		HitsPanel.setBorder(border);
		MissPanel.setBorder(border);
		SunkPanel.setBorder(border);
		
		Buttons.setLayout(new BorderLayout(0,0));
				
		Hits.setFont(S);
		HitsCount.setFont(S);
		Miss.setFont(S);
		MissCount.setFont(S);
		Sunk.setFont(S);
		SunkCount.setFont(S);
		
		Result.setFont(D);
		Result.setHorizontalAlignment(JTextField.CENTER);
			
		
		HitsPanel.add(Hits);
		Hits.setHorizontalAlignment(Hits.CENTER);
		HitsPanel.add(HitsCount);
		HitsCount.setHorizontalAlignment(HitsCount.CENTER);
		MissPanel.add(Miss);
		Miss.setHorizontalAlignment(Miss.CENTER);
		MissPanel.add(MissCount);
		MissCount.setHorizontalAlignment(MissCount.CENTER);
		SunkPanel.add(Sunk);
		Sunk.setHorizontalAlignment(Sunk.CENTER);
		SunkPanel.add(SunkCount);
		SunkCount.setHorizontalAlignment(SunkCount.CENTER);
		
		
		
		Buttons.add(Btn_NewGame,BorderLayout.WEST);
		Buttons.add(Btn_Exit,BorderLayout.EAST);
		
		
				
		Result.setEditable(false);
		
		
		
	}
	
	public void SetShip()
	{
		int ShipIndex = (int)(Math.random()*100);
			
		BtnList[ShipIndex].putClientProperty("Ship", 1);
		Random VertShip = new Random();
		boolean Vertical;
		Vertical = VertShip.nextBoolean();

		int First = ShipIndex;
		int Last = ShipIndex;
		
		if(Vertical)
		{
			boolean up = false;
			for(int i = 1; i<=5;i++)
			{
				if(Last >89)
				{
					First -= 10;
					BtnList[First].putClientProperty("Ship", 1);
			
				}
				else if(First <10)
				{
					Last += 10;
					BtnList[Last].putClientProperty("Ship", 1);
				}
				else
				{
					up = VertShip.nextBoolean();
					if(up)
					{
						First -= 10;
						BtnList[First].putClientProperty("Ship", 1);
					}
					else
					{
						Last += 10;
						BtnList[Last].putClientProperty("Ship", 1);
					}
				}
			}
		}
		else
		{
			boolean right = false;
			for(int i = 1; i<=5;i++)
			{
				if(((Last+1)%10) ==0)
				{
					First -= 1;
					BtnList[First].putClientProperty("Ship", 1);
				}
				else if(((First)%10) ==0)
				{
					Last += 1;
					BtnList[Last].putClientProperty("Ship", 1);
				}
				else
				{
					right = VertShip.nextBoolean();
					if(right)
					{
						First -= 1;
						BtnList[First].putClientProperty("Ship", 1);
					}
					else
					{
						Last += 1;
						BtnList[Last].putClientProperty("Ship", 1);
					}
				}
			}
		}	
	}
	public void initExit()
	{
		Btn_Exit.addActionListener(new ActionListener()
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	System.exit(DISPOSE_ON_CLOSE);		
		    }
		});
	}
	public void ClearBoard()
	{
		Btn_NewGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(int i =0;i<100;i++)
				{
					BtnList[i].setEnabled(true);
					BtnList[i].setBackground(null);
					BtnList[i].putClientProperty("Ship", 0);
					
				}	
				MissCount.setText("0");
				HitsCount.setText("0");
				
				SetShip();
			}
		});
	}
}
