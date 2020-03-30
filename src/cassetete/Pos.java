package cassetete;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author p1607481
 */
public class Pos {
    private int x;
    private int y;
    
    //Constructeur de base
    public Pos()
    {
        x=0;
        y=0;
    }
    //Constructeur avec arguments int x et int y
    public Pos(int xc, int yc)
    {
        x=xc;
        y=yc;
    }
    //Constructeur par copie
    public Pos(Pos posc)
    {
        x=posc.getX();
        y=posc.getY();
    }
    
    //Fonctions Get
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public Pos getPos()
    {
        return this;
    }
    
    //Compare deux positions (Retourne la position relative de la case donnée en paramètre
    public Pos comparePos(Pos pos)
    {
        return new Pos(pos.getX() - this.getX(),pos.getY() - this.getY());
    }
}
