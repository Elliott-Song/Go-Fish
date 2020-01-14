package cardgame;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CardGame extends JFrame implements Array, Actions{
        int run = 0;
        
        Color white = new Color(255, 255, 255);
        Color green = new Color(40, 80, 70);
	
	private Image dbImage;
	private Graphics dbg;
	
	private static Card[] myCards;
        
        int bnum = 20;
        int bx[] = new int[bnum];
        int by[] = new int[bnum];
        int br[] = new int[bnum];
        
        int mx;
        int fishx = 50;
        int fishy = 100;
        int turn = 1;
        int timer = 0;
        public int turn() {
            return turn;
        }
        int hsize=5;
        int ehsize;
        public Card[] hand = new Card[0];
        Card[] Ehand = new Card[0];
        Card[] pairs = new Card[0];
        Card[] Epairs = new Card[0];
        int[] cardist = new int[hsize];
        
        public String[] narray = new String[] {"","Go Fish!", "Your Turn...", "Their turn...", "You got some card!", "Oppnonent took your card!", "Opponent is fishing..."};
        public String narr = narray[0];

	public CardGame(){
	    //Load Images:
                for (int i = 0; i <bnum; i++ ) {
                    bx[i] = ThreadLocalRandom.current().nextInt(0, 1050);
                    by[i] = ThreadLocalRandom.current().nextInt(768/2, 768);
                    br[i] = ThreadLocalRandom.current().nextInt(5, 50);
                }
		myCards = new Card[52];
		int j = 2;
		for (int i = 0; i < 36; i=i+4){				
				myCards[i] = new Card(j+"H",0,0,"blue_back.png", i);
				myCards[i+1] = new Card(j+"C",0,0,"blue_back.png", i+1);
				myCards[i+2] = new Card(j+"S",0,0,"blue_back.png", i + 2);
				myCards[i+3] = new Card(j+"D",0,0,"blue_back.png", i + 3);
				j++;
		}
		String[] Faces = new String[4];
		Faces[0]="J";
		Faces[1]="Q";
		Faces[2]="K";
		Faces[3]="A";
		j = 0;
		for (int i = 36; i < 52; i=i+4){
			myCards[i] = new Card(Faces[j]+"H",0,0,"blue_back.png", i);
			myCards[i+1] = new Card(Faces[j]+"C",0,0,"blue_back.png", i + 1);
			myCards[i+2] = new Card(Faces[j]+"S",0,0,"blue_back.png", i + 2);
			myCards[i+3] = new Card(Faces[j]+"D",0,0,"blue_back.png", i + 3);
			j++;
                        
		}
            Card[][] swit;
            //Below code creates your hand
            for (int i = 0; i <hsize; i+=1) {
                swit=gofish(hand, myCards);
                hand= swit[0];
                myCards = swit[1];
                swit=gofish(Ehand, myCards);
                Ehand= swit[0];
                myCards = swit[1];
                //System.out.println(Arrays.toString(Ehand));
                //System.out.println(hand[i].myImage);
            }
            fishx= getWidth();
            hand = sort(hand);
            Ehand = sort(Ehand);
            
            
	    //Set up game
	    addMouseListener(new Mouse());
	    init();
	}
	
	public void init(){
	    windowManager();
	    
	}
	
	public void windowManager(){
	      JFrame f = new JFrame();
	      setTitle("Engine");
	      setVisible(true);
	      setResizable(false);
	      setSize(1050,768);
	      setBackground(green);
              setForeground(white);
              setFont(new Font("TimesRoman", Font.BOLD, 50)); 
	      setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
        
    

//adds a card to the end of a card array
    public Card[] add(Card[] o, Card n) {
        int length=o.length;
        Card[] temp = new Card[length+1];
        for (int i = 0; i<length; i+=1){
            temp[i]= o[i];
        }
        temp[length]=n;

        return temp;
    }

// deletes a specific card from a card array
    public Card[] delete(Card[] o, int pos) {
        int length=o.length;
        Card[] temp = new Card[length-1];
        
        for (int i = 0; i<pos; i+=1){
            temp[i]= o[i];
        }
        for (int i = pos; i<length-1; i+=1){
            temp[i]= o[i+1];
        }

        return temp;
    }

//sorts a card array depending on the card's value
    public Card[] sort(Card[] o) {
        int len = o.length;
        for (int i = 0; i < len-1; i+=1) {
            for (int j = 0; j < len -i-1; j +=1){
                if (o[j].compareTo(o[j+1])==1) {
                    Card temp = o[j];
                    o[j]=o[j+1];
                    o[j+1]=temp;
                    //System.out.println(j);
                }
            }
        }
        return o;
    }
    
//takes a random card from one card array and adds it to another card array
    public Card[][] gofish(Card[] o, Card[] d) {
        int rand = ThreadLocalRandom.current().nextInt(0, d.length);
        o=add(o,d[rand]);
        d=delete(d,rand);
        Card[][] c = new Card[][] {o,d};
        return c;
    }
    
// checks if a specific card has a match in another card array.
//If it does, the match is trasferred over. If not, the first card array goes fish
    public Card[][] ask(Card[] o, Card[] q, Card n, Card[] d) {
        Card[] temp=o;
        for (int i = 0; i < q.length; i+=1 ) {
            //hsize=hand.length;
            //ehsize=Ehand.length;
            //below if statement checks if enemy has what you asked for and then gives you the card
            if (q[i].name.equals(n.name)) {
                o=add(o,q[i]);
                q=delete(q,i);
                //System.out.print("q[i].name");
            }
        }
        //this if statement checks if you can steal a card and makes you go fish
        if (o==temp) {
            Card[][] swit=gofish(o,d);
            o = swit[0];
            d = swit[1];

            if (this.turn == 1) {
                //System.out.print("Go Fish!");
                narr=narray[1];
                o[o.length-1].x=getWidth()/2;
                o[o.length-1].y=getHeight()/2+100;
                fishx=getWidth();
            } else {
                narr=narray[6];
                o[o.length-1].x=getWidth()/2;
                o[o.length-1].y=getHeight()/2-100;
            }
            
        } else {
            if (this.turn == 1) {
                narr=narray[4];
            } else {
                narr=narray[5];
            }
        }
        this.timer=0;
        Card[][] c = new Card[][] {o,q,d};
        return c;
    }
    //This method is just for adding to the array "cardist"
    public int[] cdadd(int[] n, Card[] o) {
        int[] temp2 = n;
        if (n.length<o.length) {
            temp2 = new int[n.length+1];
            
        } else if (n.length>o.length) {
            temp2 = new int[n.length-1];
        }
        return temp2;
    }
    
    //this method checks for same cards in a hand and then puts them into the appropriate "pair" array
    public Card[] fourkind(Card[] o){
        boolean k;
        if (o==this.hand) {
            k=true;
        } else{
            k= false;
        }
        Card[] temp = o;
        
        for (int i = 0; i < o.length-1; i+=1) {
            if (o[i].name.equals(o[i+1].name)) {
                if (k) {
                        if (o[i].y> 575 && o[i+1].y> 575 || run ==2) {    
                            this.pairs=add(this.pairs,o[i]);
                            this.pairs=add(this.pairs,o[i+1]);
                            o[i].y=getHeight();
                            o[i+1].y=getHeight();
                            o=delete(o,i+1);
                            o=delete(o,i);
                        }
                    } else {
                        if (o[i].y< 100 && o[i+1].y< 100 || run ==2) { 
                            this.Epairs=add(this.Epairs,o[i]);
                            this.Epairs=add(this.Epairs,o[i+1]);
                            o[i].y=0;
                            o[i+1].y=0;
                            o=delete(o,i+1);
                            o=delete(o,i);
                        }
                }
            }
        }
        System.out.println(pairs.length);
        return o;
    }
        // This thing checks if you click and then does stuff depending on who's turn it is
        public class Mouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
                    
		    double x = e.getX();
		    double y = e.getY();
                    //System.out.println(x);
                    //System.out.println(y);
                    //hand=gofish(hand);
                    if (turn == 1 && run ==1) {
                        int num=-1;
                        for (int i =0; i<hand.length; i+=1){
                            if (hand[i].y<580) {
                                num = i;
                            }
                        }
                        //System.out.println(num);
                        if (num>-1) {
                            Card[][] swit=ask(hand,Ehand,hand[num],myCards);
                            hand=swit[0];
                            Ehand=swit[1];
                            myCards=swit[2];
                            turn *= -1;
                        }
                    }
                    else if (turn == -1 && run == 1) {
                        int num=ThreadLocalRandom.current().nextInt(0, Ehand.length);
                        //System.out.println(num);
                        if (num>-1) {
                            Card[][] swit=ask(Ehand,hand,Ehand[num], myCards);
                            Ehand=swit[0];
                            hand=swit[1];
                            myCards=swit[2];
                            turn *= -1;
                        }
                        
                    }
                    if (run == 0) {
                        run =1;
                    }
                    
                    
		}
	}
	//this big thing does everything
	public void paintComponent(Graphics g){
            //System.out.println(turn);
            hand = sort(hand);
            Ehand = sort(Ehand);
            hand=fourkind(hand);
            Ehand=fourkind(Ehand);
            
            mx=MouseInfo.getPointerInfo().getLocation().x;
            hsize=hand.length;
            ehsize=Ehand.length;
            //System.out.println(cardist.length);
            cardist=cdadd(cardist,hand);
            ImageIcon icon = new ImageIcon("PNG/200.gif");
            if (timer<100) {
                g.drawImage(icon.getImage(), fishx , fishy , this);
            }
            
        if (run == 0) {
                this.dbg.drawString("Go Fish Game",getWidth()/2 - 100,getHeight()/2+50);
                this.dbg.drawString("Click to Play",getWidth()/2 - 100,getHeight()/2+100);
                for (int i = 0; i < bnum; i ++) {
                    bx[i]+= ThreadLocalRandom.current().nextInt(-3,3)*br[i]/5;
                    by[i]-=br[i]/5;
                    g.drawOval(bx[i], by[i], br[i], br[i]);
                }
                //System.out.println("STILL running");
                 
        }
	    if(run == 1){
                fishx -= 5;
                fishy = 300;
                //System.out.println(Arrays.toString(hand));
                //player
                for (int i = 0; i <hsize; i+=1) {
                //hand[i].x=(hand[i].wid*i);
                    if (hand[i].x-3 > hand[i].wid*i) {
                        hand[i].x-=2;
                    } else if (hand[i].x < hand[i].wid*i) {
                        hand[i].x+=2;
                }
                //hand[i].y=getHeight()-100-(100/(cardist[i]+1));
                //hand[i].y+=((getHeight()-100-(100/(cardist[i]+1)))-hand[i].y)/50;
                if (hand[i].y -3 > getHeight()-100-(100/(cardist[i]+1))) {
                    hand[i].y-=5;
                } else if (hand[i].y + 3 < getHeight()-100-(100/(cardist[i]+1))) {
                    hand[i].y+=5;
                }
                
                if (turn == 1) {
                    cardist[i]= ((mx-hand[i].x-65)*(mx-hand[i].x-65))/getWidth();
                } else {
                    cardist[i]=100;
                }
                ImageIcon II= hand[i].myImage;
                g.drawImage(II.getImage(), hand[i].x , hand[i].y , this);
                
                
                //System.out.println(hand[i].y);
                }
                
                timer+=1;
                if (turn ==1 && timer > 100){
                    narr=narray[2];
                    timer=0;
                } else if (turn == -1 && timer> 100){
                    narr=narray[3];
                    timer=0;
                }
                
                //enemy
                for (int i = 0; i <ehsize; i+=1) {
                Ehand[i].x=(getWidth()-Ehand[i].wid*ehsize)+Ehand[i].wid*i;
                if (Ehand[i].y -3 > 50) {
                    Ehand[i].y-=5;
                } else if (Ehand[i].y + 3 < 50) {
                    Ehand[i].y+=5;
                }
                
                ImageIcon II= Ehand[i].myBack;
                g.drawImage(II.getImage(), Ehand[i].x , Ehand[i].y , this);
                
                //System.out.println(hand[i].y);
                }
                
                //player pairs
                for (int i = pairs.length-1; i >=0; i-=1) {
                    pairs[i].wid=50;
                    if (pairs[i].y> (getHeight()-40*(i/2+2))+400*(pairs.length/22)) {
                        pairs[i].y-=1;
                    }
                    pairs[i].x=(getWidth()-30*(i%2+2)-i*10);
                    ImageIcon II= pairs[i].mySmallImage;
                    g.drawImage(II.getImage(), pairs[i].x , pairs[i].y , this);
                }
                //enemy pairs
                for (int i = 0; i <Epairs.length; i+=1) {
                    Epairs[i].wid=50;
                    if (Epairs[i].y< (40*(i/2+3))-400*(Epairs.length/22)) {
                        Epairs[i].y+=1;
                    }
                    Epairs[i].x=(30*(i%2))+10+i*10;
                    ImageIcon II= Epairs[i].mySmallImage;
                    g.drawImage(II.getImage(), Epairs[i].x , Epairs[i].y , this);
                }
                if (hsize==0 && myCards.length!=0) {
                    Card[][] swit=gofish(hand,myCards);
                    hand = swit[0];
                    myCards = swit[1];
                }
                if (ehsize==0 && myCards.length!=0) {
                    Card[][] swit=gofish(Ehand,myCards);
                    Ehand = swit[0];
                    myCards = swit[1];
                }
                if (myCards.length == 0 && hand.length == 0 && run == 1 || myCards.length == 0 && Ehand.length == 0 && run == 1) {
                    run = 2;
                }
                this.dbg.drawString(narr,getWidth()/2 - (narr.split("").length*10),getHeight()/2+50);
	        
	    } else if (run == 2) {
                
                this.dbg.drawString("Game Over!",getWidth()/2 - 130,getHeight()/2-50);
                if (pairs.length > Epairs.length) {
                    this.dbg.drawString("You win!",getWidth()/2 - 100,getHeight()/2+50);
                } else if (pairs.length < Epairs.length) {
                    this.dbg.drawString("You lost.",getWidth()/2 - 100,getHeight()/2+50);
                } else {
                    this.dbg.drawString("You tied!",getWidth()/2 - 100,getHeight()/2+50);
                }
                this.dbg.drawString("The score was: " + pairs.length/2 + " to " + Epairs.length/2 ,getWidth()/2 - 200,getHeight()/2+100);
                
                System.out.println(Arrays.toString(hand));
                for (int i = 0; i < hand.length; i++) {
                    System.out.print(hand[i].name);
                }System.out.println("");
                System.out.println(Arrays.toString(Ehand));
                for (int i = 0; i < Ehand.length; i++) {
                    System.out.print(Ehand[i].name);
                }System.out.println("");
                hand=fourkind(hand);
                Ehand=fourkind(Ehand);
            }
	    try
	    {
	        Thread.sleep(6);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	    repaint();
	}
	
	public void paint(Graphics g){
		
	    dbImage = createImage(getWidth(), getHeight());
	    dbg = dbImage.getGraphics();
	    paintComponent(dbg);
	    g.drawImage(dbImage,0,0,this);
	}

	public static void main(String[] args) {
		  new CardGame();
	}
}