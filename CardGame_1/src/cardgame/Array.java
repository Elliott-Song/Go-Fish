/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

/**
 *
 * @author songe
 */
public interface Array {
    public Card[] add(Card[] o, Card n);
    public Card[] delete(Card[] o, int pos);
    public Card[] sort(Card[] o);
    public int[] cdadd(int[] n, Card[] o);
    public Card[] fourkind(Card[] o);
}