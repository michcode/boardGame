package util;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DroppingDisk extends JPanel implements ActionListener {
	private int row, column,nextPlayer, attempts;
	private JLabel banner, picLabel;
	private JButton[] menuBtns,playerBtns;
	private JLabel[][] boardImages;
	private JPanel bannerPanel, centerPanel, buttonPanel, boardPanel, menuPanel,footerPanel;
	private Image backgroundImage, greenDisk, purpleDisk,imageOnEast;
	private boolean gameOver;
		
	public DroppingDisk(int row, int column){		
		this.row =row;
		this.column = column;
		nextPlayer = 0;
		gameOver=false;
		attempts= row*column;
		setLayout(new BorderLayout());
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/circleBlue.png"));	
			greenDisk = ImageIO.read(getClass().getResource("/circleGreen.png"));
			purpleDisk =ImageIO.read(getClass().getResource("/circlePurple.png"));
			imageOnEast = ImageIO.read(getClass().getResource("/droppingDisk.jpg"));
		} catch (IOException e) {			
//			e.printStackTrace();
			System.out.println("Image file not found!");
			JOptionPane.showMessageDialog(null,"Alert","Failed to load pictures, please check the resource path!", JOptionPane.ERROR_MESSAGE);
		}
		
		bannerPanel = new JPanel();
		banner = new JLabel ("Welcome", JLabel.CENTER);
		bannerPanel.add(banner);
		bannerPanel.setBackground(new Color(200,255,255));
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		boardPanel = new JPanel();	
		boardPanel.setLayout(new GridLayout(this.row,this.column));	
		playerBtns = new JButton[column];
		boardImages= new JLabel[row][column];
		this.drawNewBoard();
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(3,1));
		menuPanel.setBackground(Color.WHITE);
		menuBtns = new JButton[3];
		menuBtns[0] = new JButton("Replay");
		menuBtns[1]= new JButton();
		menuBtns[1].setIcon(new ImageIcon(greenDisk));
		menuBtns[2]= new JButton();
		menuBtns[2].setIcon(new ImageIcon(purpleDisk));
		for(int i=0; i<3; i++){
			menuPanel.add(menuBtns[i]);
			menuBtns[i].addActionListener(this);
			menuBtns[i].setBackground(Color.white);
		}
				
		//Adding a picture on the easy side of the board game frame			
		picLabel = new JLabel(new ImageIcon(imageOnEast));
		
		footerPanel = new JPanel();
		JLabel footer = new JLabel("Copyright by Michelle Li@2017", JLabel.CENTER);
		footerPanel.add(footer);
		footerPanel.setBackground(new Color(200,255,255));
		
		//Place all panels into this Frame window of border layout
		add(bannerPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(menuPanel, BorderLayout.WEST);
		add(footerPanel, BorderLayout.SOUTH);
		add(picLabel,BorderLayout.EAST);
	}//end of DroppingDisk constructor
	
	public void drawNewBoard(){
		for(int col=0; col < column;col++){
			playerBtns[col] = new JButton();
			playerBtns[col].setIcon(new ImageIcon(backgroundImage));
			playerBtns[col].putClientProperty("colForDroppingDisk",col);
			playerBtns[col].addActionListener(this);
			buttonPanel.add(playerBtns[col]);
			buttonPanel.setBackground(Color.WHITE);
		}
		centerPanel.add(buttonPanel,BorderLayout.NORTH);
					
		for(int i=0;i< boardImages.length; i++){
			for(int j =0; j< boardImages[i].length; j++){
				boardImages[i][j] = new JLabel();
				boardImages[i][j].setIcon(new ImageIcon(backgroundImage));
//				boardImages[i][j].putClientProperty("isEmpty", "");
				boardImages[i][j].putClientProperty("color", "");
				boardPanel.add(boardImages[i][j]);				
			}
		}
		centerPanel.add(boardPanel,BorderLayout.CENTER);
		boardPanel.setBackground(Color.WHITE);
	}//end of drawNewBoard method

	public void resetBoard(){
		for(int i=0; i< boardImages.length; i++){
			for(int j=0; j< boardImages[i].length;j++){
				boardImages[i][j].setIcon(new ImageIcon(backgroundImage));	
				boardImages[i][j].putClientProperty("color", "");
			}			
		}
	}//end of resetBoard method

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedBtn = (JButton)e.getSource();
		int rowIndex, colIndex;
		String diskColor;
		if (!gameOver && clickedBtn != menuBtns[0]){			
			if (clickedBtn == menuBtns[1])
				nextPlayer = 1;
			else if (clickedBtn == menuBtns[2])
				nextPlayer = 2;
			else{
				if(nextPlayer ==0){
					JOptionPane.showMessageDialog(null,"Please first pick a disk to start!" );
				}
				else if(attempts==0){
					gameOver= true;
					JOptionPane.showMessageDialog(null,"Tie! Please start a new game!","Game Over!", JOptionPane.INFORMATION_MESSAGE);		
				
				}
				else{
					colIndex = (int)clickedBtn.getClientProperty("colForDroppingDisk");//refers to playerBtns on the top
					rowIndex = findRowLocation(colIndex);
					if(rowIndex != -1 ){
						diskColor = this.updateBoradImage(rowIndex, colIndex);
						boolean result = isConsecutiveFour(rowIndex,colIndex,diskColor);
						if(result)
							gameOver=true;
					}//end inner if
				}
			}	
		}
		else{
			if (clickedBtn == menuBtns[0]){
				resetBoard();
				nextPlayer=0;
				gameOver=false;
				attempts=this.row * this.column;
			}
		}
			
	}//end of actionPerformed
	
	public int findRowLocation(int columnTemp){
		int location = -1;
		for (int row = boardImages.length-1; row >= 0; row--) {
			if (boardImages[row][columnTemp].getClientProperty("color").equals("")) {
				location = row;
				break;
			}
		}
		if (location == -1) {				
			JOptionPane.showMessageDialog(null, "Column " + (++columnTemp)
					+ " is full already, please choose another column! ");
		}
		return location;
	}//end of findRowLocation method
	
	public String updateBoradImage(int row, int col){
		String diskColor="";
		if(nextPlayer==1){
			boardImages[row][col].setIcon(new ImageIcon(greenDisk));
			nextPlayer=2;
			boardImages[row][col].putClientProperty("color", "green");
			diskColor = "green";
			
		}
		else if (nextPlayer==2){
			boardImages[row][col].setIcon(new ImageIcon(purpleDisk));
			nextPlayer=1;
			boardImages[row][col].putClientProperty("color", "purple");
			diskColor = "purple";
		}
		attempts--;
		return diskColor;
	}//end of updateBoardImages
	
	private boolean isConsecutiveFour(int row, int col, String diskColor){		
		return (isConsecutiveFourInHorizontal(row) 
				|| isConsecutiveFourInVertical(row, col, diskColor)
				|| isConsecutiveFourInDiagonalLeft(row, col, diskColor)
				|| isConsecutiveFourInDiagonalRight(row, col, diskColor));
	}
	private boolean isConsecutiveFourInHorizontal(int row) {
		boolean result = false;
		String diskColor;
		for(int i=0; i< boardImages[0].length-3; i++){
			diskColor = (String)boardImages[row][i].getClientProperty("color");
			if(diskColor.equals(boardImages[row][i+1].getClientProperty("color"))
			   && diskColor.equals(boardImages[row][i+2].getClientProperty("color"))
			   && diskColor.equals(boardImages[row][i+3].getClientProperty("color"))
			   && (diskColor.equalsIgnoreCase("green") || diskColor.equalsIgnoreCase("purple"))){
				  setWinner(diskColor);
				  result= true;
				  break;				
			}
		}
		return result;
	}//end of isConsecutiveFourInHorizontal
	
	private boolean isConsecutiveFourInVertical(int row, int col, String diskColor){
		boolean result = false;
		if (row < boardImages.length-3 && row > -1){
			if(diskColor.equals(boardImages[row+1][col].getClientProperty("color"))
				&& diskColor.equals(boardImages[row+2][col].getClientProperty("color"))
				&& diskColor.equals(boardImages[row+3][col].getClientProperty("color"))){
					setWinner(diskColor);
					result = true;
					
				}
		}
		return result;
	}//end of isConsecutiveFourInVertical
	
	private boolean isConsecutiveFourInDiagonalLeft(int row, int col, String diskColor){
		boolean result= false;;
		if((row<3 && col <3) ||(row>boardImages.length-4 && col> boardImages[row].length-4))			
			result = false;
		else{
			for (int i=0; i <boardImages.length-3;i++){
				for (int j=3; j <boardImages[i].length; j++){
					if(diskColor.equals(boardImages[i][j].getClientProperty("color"))
							&& diskColor.equals(boardImages[i+1][j-1].getClientProperty("color"))
							&& diskColor.equals(boardImages[i+2][j-2].getClientProperty("color"))
							&& diskColor.equals(boardImages[i+3][j-3].getClientProperty("color"))){
						setWinner(diskColor);
						result = true;
						break;
					}//end if											
				}//end inner for loop
				if(result) break;
			}//end outer for loop
		}//end else
		return result;
	}
	
	private boolean isConsecutiveFourInDiagonalRight(int row, int col, String diskColor){
		boolean result = false;
		if((row<boardImages.length-4 && col<3) || (row<3 && col>boardImages[row].length-4))
			result = false;
		else{
			for(int i=0; i<boardImages.length-3; i++){
				for (int j=0; j<boardImages[row].length-3; j++){
					if(diskColor.equals(boardImages[i][j].getClientProperty("color"))
							&& diskColor.equals(boardImages[i+1][j+1].getClientProperty("color"))
							&& diskColor.equals(boardImages[i+2][j+2].getClientProperty("color"))
							&& diskColor.equals(boardImages[i+3][j+3].getClientProperty("color"))){
						setWinner(diskColor);
						result = true;
						break;
					}//end if
				}//end inner for loop
				if(result) break;
			}//end outer for loop
		}//end else
		return result;
	}
	
	private void setWinner(String diskColor){
		JOptionPane.showMessageDialog(null,"Player " +diskColor + " wins","Game Over!", JOptionPane.INFORMATION_MESSAGE);		
	}
	
}//end of DroppingDisk class
