package cassetete;
public class Case 
{
    
    protected Pos position;
    protected boolean etat; // "0" = Case inoccupée, "1" = Case Occupée
    protected String type; // Type "CASE_CHEMIN" ou l'un des Symbole.toString()
    
    //Constructeur de base
    public Case()
    {
        position = new Pos();
        etat = false;
        type = "CASE_CHEMIN";
    }
    //Constructeur avec arguments x et y
    public Case(int x, int y)
    {
        position = new Pos(x,y);
        etat = false;
        type = "CASE_CHEMIN";
    }
    
    //Constructeur avec arguments x et y son type
    public Case(int x, int y, String _type)
    {
        position = new Pos(x,y);
        type = _type;
        if(type == "CASE_CHEMIN")
        {
            etat = false;
        }
        else
        {
            etat = true;
        }
        
    }
    
    //Constructeur par copie de pos
    public Case(Pos pos)
    {
        position = new Pos(pos);
        etat = false;
    }
    //Constructeur par copie de pos avec etat
    public Case(Pos pos, boolean _etat)
    {
        position = new Pos(pos);
        etat = _etat;
    }
    
    //Constructeur par copie de pos avec type 
    public Case(Pos pos, String _type)
    {
        position = new Pos(pos);
        type = _type;
        if(type == "CASE_CHEMIN")
        {
            etat = false;
        }
        else
        {
            etat = true;
        }
    }
    
     //Constructeur par copie de pos avec type 
    public Case(Pos pos, String _type, boolean _etat)
    {
        position = new Pos(pos);
        type = _type;
        if(type == "CASE_CHEMIN")
        {
            etat = _etat;
        }
        else
        {
            etat = true;
        }
    }
    
    public Case(Case caseC)
    {
        this.position = caseC.position;
        this.etat = caseC.etat;
    }
    
    public Pos getPos()
    {
        return this.position;
    }
    public void setPos(Pos p)
    {
        this.position = p;
    }
    public void setEtat(boolean s)
    {
        etat = s;
    }
    public boolean getEtat()
    {
        return etat;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String strT)
    {
        type = strT;
    }
    
    //Compare la position de cette case avec une autre
    public Pos comparePosCase(Pos comp)
    {
       return position.comparePos(comp);
    }
    
}
