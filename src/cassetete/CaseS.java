package cassetete;

// Classe d'une case Symbole
public class CaseS extends Case
{   
    private String strSymb; // Symbole affecté à la case
    
    public String getStrSymb()
    {
        return strSymb;
    }
    
    CaseS(Pos pCase, Symbole symb)
    {
        super(pCase, symb.toString());
        strSymb = symb.toString();
    }
}
