package cassetete;
public enum Symbole
{
        CROIX ("croix"),
        CERCLE ("cercle"),
        TRIANGLE ("triangle"),
        CARRE ("carre"),
        PENTAGONE ("pentagone");
        
        private String nomSymbole;
        
        //Constructeur
        Symbole(String _nomSymbole)
        {
          nomSymbole = _nomSymbole;
        }
        
        public String toString()
        {
            return nomSymbole;
        }
}

