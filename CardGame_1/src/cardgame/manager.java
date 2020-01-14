/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author songe
 */
public class manager {//implements Array, Actions {
    //working!!!!!!!!
    /*
    CardGame myGame = CardGame.myGame; 
    public Card[] add(Card[] o, Card n) {
        int length=o.length;
        Card[] temp = new Card[length+1];
        for (int i = 0; i<length; i+=1){
            temp[i]= o[i];
        }
        temp[length]=n;

        return temp;
    }

// working
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


    public Card[] sort(Card[] o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Card[][] gofish(Card[] o, Card[] d) {
        int rand = ThreadLocalRandom.current().nextInt(0, d.length);
        o=add(o,d[rand]);
        d=delete(d,rand);
        Card[][] c = new Card[][] {o,d};
        return c;
    }
    
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

            if (myGame.getturn() == 1) {
                System.out.print("Go Fish!");
                myGame.setnarr(myGame.getnarray()[1]);
                o[o.length-1].x=1050/2;
                o[o.length-1].y=768/2+100;
            } else {
                myGame.setnarr(myGame.getnarray()[6]);
            }
            
        } else {
            if (myGame.getturn() == 1) {
                myGame.setnarr(myGame.getnarray()[4]);
            } else {
                myGame.setnarr(myGame.getnarray()[5]);
            }
        }
        myGame.settimer(0);
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
        if (o==myGame.gethand()) {
            k=true;
        } else{
            k= false;
        }
        int count;
        Card car;
        Card[] temp = o;
        
        for (int i = 0; i < o.length; i+=1) {
            int stop=0;
            count= 0;
            car = o[i];
            
            for (int j= 0; j < o.length; j+=1) {
                if (o[i].name.equals(o[j].name)) {
                    count+=1;
                   // System.out.print(o[i].name);
                }
            }
            
            if (count>=2) {
                for (int j= o.length -1 ; j >=0 && stop <=2; j-=1) {
                    if (car.name.equals(o[j].name)) {
                        if (k) {
                            myGame.setpairs(add(myGame.getpairs(),o[j]));
                            o[j].y=768;
                        } else {
                            myGame.setEpairs(add(myGame.getEpairs(),o[j]));
                            o[j].y=0;
                        }
                        stop+=1;
                        
                        o=delete(o,j);
                        
                    }
                }
            }
            
        }
        return o;
    }*/
}
