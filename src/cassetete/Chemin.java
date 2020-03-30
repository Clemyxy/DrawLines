package cassetete;
import java.util.ArrayList;

public class Chemin 
{
    private ArrayList<Case> tabCase;
    private boolean termine;
    
    Chemin()
    {
        tabCase = new ArrayList<Case>();
        termine = false;
    }
    
    public void setTabCase(ArrayList<Case> _tabCase)
    {
        tabCase = _tabCase;
    }
    
    public ArrayList<Case> getTabCase()
    {
        return tabCase;
    }
    
    public boolean getTermine()
    {
        return termine;
    }
    
    private void setTermine(boolean _termine)
    {
        termine = _termine;
    }
    
    // Débute le chemin
    public boolean debuterChemin(Case caseSelectionnee)
    {
        if(caseSelectionnee instanceof CaseS)
        {
            ajouterCase(caseSelectionnee);
            return true;
        }
        
        else 
        {
            System.out.println("Impossible de commencer le chemin, la case est de type : " + caseSelectionnee.getClass());
            return false;
        }
    }
    
    // Ajoute la position de la case en cours du symbole si la case est adjacente à  la case précédente
    public boolean poursuivreChemin(Case caseSelectionnee)
    {
        if(caseSelectionnee.getEtat() == false || caseSelectionnee instanceof CaseS)
        {
            // On test si le chemin est commencé, c'est à  dire, s'il débute par un symbole et donc contient au moins une position
            if(tabCase.size() > 0)
            {   
                Case casePrec = tabCase.get(tabCase.size() - 1);
                if(adjacenceCase(casePrec, caseSelectionnee))
                {
                    // On teste si la case pointée est une case symbole
                    if(caseSelectionnee instanceof CaseS)
                    {
                        // Si le symbole est identique à celui de départ mais que ce n'est pas la même case
                        if(caseSelectionnee.getType() == tabCase.get(0).getType() && caseSelectionnee.getPos() != tabCase.get(0).getPos())
                        {
                            ajouterCase(caseSelectionnee);
                            // Le chemin est donc validé
                            setTermine(true);
                            // On trace donc le chemin validé
                            modifCaseChemin(tabCase);
                            System.out.println("Chemin valide");
                            return true;
                        }
                        // Sinon ce n'est pas le bon symbole et on invalide le chemin
                        else 
                        {
                            return false;
                        }
                    }
                    ajouterCase(caseSelectionnee);
                    caseSelectionnee.setEtat(true);
                    return true;
                }
                else 
                {
                    System.out.println("Impossible de poursuivre le chemin, la case n'est pas adjacente avec la précédente");
                    return false;
                }
            }
            else 
            {
                System.out.println("Impossible de poursuivre le chemin, la taille du chemin est nulle");
                return false;
            }
        }
        else 
        {
            System.out.println("Impossible de poursuivre le chemin, la case est déjà occupée!");
            return false;
        }
    }
    
    // Ajout d'une case dans un chemin
    public void ajouterCase(Case _case)
    {
        tabCase.add(_case);
    }
    
    // Suppression des cases d'un chemin 
    public void viderCase()
    {
        tabCase = new ArrayList<Case>();
    }
    
    // Teste l'adjacence des cases en comparant leurs positions 
    public boolean adjacenceCase(Case _case1, Case _case2)
    {
        // Test des adjacences
        if(adjacenceCaseH(_case1, _case2))
        {
            return true;
        }
        if(adjacenceCaseB(_case1, _case2))
        {
            return true;
        }
        if(adjacenceCaseG(_case1, _case2))
        {
            return true;
        }
        return adjacenceCaseD(_case1, _case2);
    }
    
    // Teste l'adjacence en haut des cases en comparant leurs positions 
    public boolean adjacenceCaseH(Case _case1, Case _case2)
    {
        return _case1.comparePosCase(_case2.getPos()).getX() == -1 && _case1.comparePosCase(_case2.getPos()).getY() == 0;
    }
    
    // Teste l'adjacence en bas des cases en comparant leurs positions 
    public boolean adjacenceCaseB(Case _case1, Case _case2)
    {
        return _case1.comparePosCase(_case2.getPos()).getX() == 1 && _case1.comparePosCase(_case2.getPos()).getY() == 0;
    }
    
    // Teste l'adjacence à gauche des cases en comparant leurs positions 
    public boolean adjacenceCaseG(Case _case1, Case _case2)
    {

        return _case1.comparePosCase(_case2.getPos()).getX() == 0 && _case1.comparePosCase(_case2.getPos()).getY() == -1;
    }
    
    // Teste l'adjacence à droite des cases en comparant leurs positions 
    public boolean adjacenceCaseD(Case _case1, Case _case2)
    {

        return _case1.comparePosCase(_case2.getPos()).getX() == 0 && _case1.comparePosCase(_case2.getPos()).getY() == 1;
       
    }   
    
    // Modification des cases d'un chemin afin de permettre de reconnaitre le chemin parcouru, uniquement si le chemin est validé
    public ArrayList<Case> modifCaseChemin(ArrayList<Case> chemin)
    {
        // On s'assure que le chemin a une taille supérieure à 1 (1 caseS de début et 1 caseS de fin avec au moins une caseC entre)
        if(chemin.size() > 1 && termine == true)
        {
            // On parcourt le chemin 
            for(int i = 0; i < chemin.size(); i++)
            {
                // On ne veut modifier que les cases chemin, si la case est de ce type, on lui affecte le type correspondant à la liaison dans le chemin
                if(chemin.get(i).getType() == "CASE_CHEMIN")
                {
                    // La première case étant forcément une caseS et la dernière aussi, il y a forcément une case avant et après une caseC
                    Case casePrec = new Case(chemin.get(i-1));
                    Case caseSuiv = new Case(chemin.get(i+1));
                    String posP = null, posS = null, typeC = "";
                    // Tests avec la case précédente
                    if(adjacenceCaseH(chemin.get(i), casePrec))
                    {
                        posP = "haut";
                    }
                    else if(adjacenceCaseB(chemin.get(i), casePrec))
                    {
                        posP = "bas";
                    }
                    else if(adjacenceCaseG(chemin.get(i), casePrec))
                    {
                        posP = "gauche";
                    }
                    else if(adjacenceCaseD(chemin.get(i), casePrec))
                    {
                        posP = "droite";
                    }
                    // Test avec la case suivante 
                    if(adjacenceCaseH(chemin.get(i), caseSuiv))
                    {
                        posS = "haut";
                    }
                    else if(adjacenceCaseB(chemin.get(i), caseSuiv))
                    {
                        posS = "bas";
                    }
                    else if(adjacenceCaseG(chemin.get(i), caseSuiv))
                    {
                        posS = "gauche";
                    }
                    else if(adjacenceCaseD(chemin.get(i), caseSuiv))
                    {
                        posS = "droite";
                    }
                    // Test de toute les combinaisons possibles pour construire le bon type de chemin avec le bon tracé
                    if(posP == "gauche")
                    {
                        typeC = posP + posS;
                    }
                    else if (posS == "gauche")
                    {
                        typeC = posS + posP;
                    }
                    else if (posP == "droite")
                    {
                        typeC = posP + posS;
                    }
                    else if (posS == "droite")
                    {
                        typeC = posS + posP;
                    }
                    else if (posP == "haut")
                    {
                        typeC = posP + posS;
                    }
                    else if (posS == "haut")
                    {
                        typeC = posS + posP;
                    }
                    // On affect le type du chemin à la case
                    chemin.get(i).setType(typeC);
                }
            }
        }
        return chemin;
    }
    
    public String toString()
    {
        String chemin = "";
        for(int i = 0; i < tabCase.size(); i++)
        {
                chemin += tabCase.get(i).getType() + " ";
        }
        return chemin;
    }
}
