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
public interface Actions {
    public Card[][] gofish(Card[] o, Card[] d);
    public Card[][] ask(Card[] o, Card[] q, Card n, Card[] d);
}
