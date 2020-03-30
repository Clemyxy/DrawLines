package cassetete;

import java.util.ArrayList;
import java.util.Observable;

public class Grille extends Observable
{
    private boolean finPartie;
    private final long startTime;
    private int lonX;
    private int lonY;
    private int nbSymbole;
    private Case[][] grilleCT;
    private ArrayList<Chemin> tabChem; // Tableau de Chemin, chaque Chemin est un tableau de Case
    private int nbCheminValide;
    
    int lastC, lastR;
    
    // Constructeur de la classe par défaut
    public Grille()
    {
        startTime=System.currentTimeMillis();
        finPartie=false;
        lonX = 3;
        lonY = 3;
        nbSymbole = 1;
        grilleCT = new Case[lonX][lonY];
        nbCheminValide = 0;
        for(int i=0; i<lonX ; i++)
        {
            for(int j=0; j<lonX ; j++)
            {
                grilleCT[i][j] = new CaseC(new Pos(i,j));
            }
        }
        tabChem = new ArrayList<Chemin>();
        for(int i=0; i < nbSymbole ; i++)
        {
            Chemin nouveauChemin = new Chemin();
            tabChem.add(nouveauChemin);
        }
        grilleCT[0][0] = new CaseS(new Pos(0,0), Symbole.CARRE);
        grilleCT[0][2] = new CaseS(new Pos(0,2), Symbole.CARRE);
    }
    // Constructeur de la classe
    public Grille(int _lonX, int _lonY, int _nbSymbole)
    {
        startTime=System.currentTimeMillis();
        finPartie=false;
        lonX = _lonX;
        lonY = _lonY;
        nbSymbole = _nbSymbole;
        nbCheminValide = 0;
        grilleCT = new Case[lonX][lonY];
        for(int i=0; i<lonX ; i++)
        {
            for(int j=0; j<lonX ; j++)
            {
                grilleCT[i][j] = new CaseC(new Pos(i,j));
            }
        }
        
        tabChem = new ArrayList<Chemin>();
        for(int i=0; i < nbSymbole ; i++)
        {
            
            tabChem.add(new Chemin());
        }
    }
    
    public float tempsVictoire(){
        return (float)(System.currentTimeMillis() - startTime)/1000;
    }
    // Retourne la taille de l'axe X
    public int getLonX()
    {
        return lonX;
    }
    
    // Retourne la taille de l'axe Y
    public int getLonY()
    {
        return lonY;
    }
    
    public int getLastC()
    {
        return lastC;
    }
    
    public int getLastR()
    {
        return lastR;
    }
    
    public void setLastC(int c)
    {
        lastC = c;
    }
    
    public void setLastR(int r)
    {
        lastR = r;
    }
    
    public int getNbCheminValide()
    {
        return nbCheminValide;
    }
    
    public Case[][] getGrilleCT()
    {
        return grilleCT;
    }
    
    // Reçois en paramètre le niveau de difficulté de la grille et renvoie la grille correspondante 
    public Grille niveauGrille(int nv)
    {
        try 
        {
            Grille nouvelleGrille = new Grille();
            switch(nv)
            {
                // Niveau 1
                case 0 : 
                    nouvelleGrille = new Grille(3, 3, 2);
                    nouvelleGrille.getGrilleCT()[0][0] = new CaseS(new Pos(0,0), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[2][1] = new CaseS(new Pos(2,1), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[0][2] = new CaseS(new Pos(0,2), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[2][2] = new CaseS(new Pos(2,2), Symbole.CERCLE);
                    break;
                
                // Niveau 2
                case 1 : 
                    nouvelleGrille = new Grille(3, 3, 2);
                    nouvelleGrille.getGrilleCT()[0][0] = new CaseS(new Pos(0,0), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[1][1] = new CaseS(new Pos(1,1), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[0][1] = new CaseS(new Pos(0,1), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[2][0] = new CaseS(new Pos(2,0), Symbole.CERCLE);
                    break;    

                // Niveau 3
                case 2 : 
                    nouvelleGrille = new Grille(4, 4, 2);
                    nouvelleGrille.getGrilleCT()[0][0] = new CaseS(new Pos(0,0), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[2][2] = new CaseS(new Pos(2,2), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[0][1] = new CaseS(new Pos(0,1), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[2][1] = new CaseS(new Pos(2,1), Symbole.CERCLE);
                    break;
                    
                // Niveau 4
                case 3 : 
                    nouvelleGrille = new Grille(4, 4, 3);
                    nouvelleGrille.getGrilleCT()[0][0] = new CaseS(new Pos(0,0), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[3][3] = new CaseS(new Pos(3,3), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[1][1] = new CaseS(new Pos(1,1), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[0][3] = new CaseS(new Pos(0,3), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[0][1] = new CaseS(new Pos(0,1), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[1][2] = new CaseS(new Pos(1,2), Symbole.TRIANGLE);
                    break;
                // Niveau 5
                case 4 : 
                    nouvelleGrille = new Grille(4, 4, 3);
                    nouvelleGrille.getGrilleCT()[0][1] = new CaseS(new Pos(0,1), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[3][1] = new CaseS(new Pos(3,1), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[0][3] = new CaseS(new Pos(0,3), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[3][3] = new CaseS(new Pos(3,3), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[1][1] = new CaseS(new Pos(1,1), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[3][2] = new CaseS(new Pos(3,2), Symbole.TRIANGLE);
                    break;
                    
                // Niveau 6
                 case 5 : 
                    nouvelleGrille = new Grille(5, 5, 3);
                    nouvelleGrille.getGrilleCT()[0][0] = new CaseS(new Pos(0,0), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[3][2] = new CaseS(new Pos(3,2), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[0][3] = new CaseS(new Pos(0,3), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[4][4] = new CaseS(new Pos(4,4), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[0][2] = new CaseS(new Pos(0,2), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[3][1] = new CaseS(new Pos(3,1), Symbole.TRIANGLE);
                    break;
                
                // Niveau 7
                case 6 : 
                    nouvelleGrille = new Grille(6, 6, 3);
                    nouvelleGrille.getGrilleCT()[0][2] = new CaseS(new Pos(0,2), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[5][0] = new CaseS(new Pos(5,0), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[2][1] = new CaseS(new Pos(2,1), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[3][1] = new CaseS(new Pos(3,1), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[0][5] = new CaseS(new Pos(0,5), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[5][3] = new CaseS(new Pos(5,3), Symbole.TRIANGLE);
                    break;
                // Niveau 8
                case 7 : 
                    nouvelleGrille = new Grille(5, 5, 4);
                    nouvelleGrille.getGrilleCT()[3][1] = new CaseS(new Pos(3,1), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[3][4] = new CaseS(new Pos(3,4), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[1][1] = new CaseS(new Pos(1,1), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[2][4] = new CaseS(new Pos(2,4), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[2][0] = new CaseS(new Pos(2,0), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[1][4] = new CaseS(new Pos(1,4), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[2][1] = new CaseS(new Pos(2,1), Symbole.CROIX);
                    nouvelleGrille.getGrilleCT()[3][3] = new CaseS(new Pos(3,3), Symbole.CROIX);
                    break;
                // Niveau 9
                case 8 : 
                    nouvelleGrille = new Grille(6, 6, 5);
                    nouvelleGrille.getGrilleCT()[1][0] = new CaseS(new Pos(1,0), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[3][1] = new CaseS(new Pos(3,1), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[0][2] = new CaseS(new Pos(0,2), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[3][4] = new CaseS(new Pos(3,4), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[1][2] = new CaseS(new Pos(1,2), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[2][3] = new CaseS(new Pos(2,3), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[0][0] = new CaseS(new Pos(0,0), Symbole.CROIX);
                    nouvelleGrille.getGrilleCT()[4][5] = new CaseS(new Pos(4,5), Symbole.CROIX);
                    nouvelleGrille.getGrilleCT()[5][5] = new CaseS(new Pos(5,5), Symbole.PENTAGONE);
                    nouvelleGrille.getGrilleCT()[4][2] = new CaseS(new Pos(4,2), Symbole.PENTAGONE);
                    break;
                
                // Niveau 10
                case 9 : 
                    nouvelleGrille = new Grille(8, 8, 5);
                    nouvelleGrille.getGrilleCT()[1][2] = new CaseS(new Pos(1,2), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[6][1] = new CaseS(new Pos(6,1), Symbole.CARRE);
                    nouvelleGrille.getGrilleCT()[0][4] = new CaseS(new Pos(0,4), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[6][4] = new CaseS(new Pos(6,4), Symbole.CERCLE);
                    nouvelleGrille.getGrilleCT()[0][7] = new CaseS(new Pos(0,7), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[4][4] = new CaseS(new Pos(4,4), Symbole.TRIANGLE);
                    nouvelleGrille.getGrilleCT()[3][2] = new CaseS(new Pos(3,2), Symbole.CROIX);
                    nouvelleGrille.getGrilleCT()[7][7] = new CaseS(new Pos(7,7), Symbole.CROIX);
                    nouvelleGrille.getGrilleCT()[1][6] = new CaseS(new Pos(1,6), Symbole.PENTAGONE);
                    nouvelleGrille.getGrilleCT()[5][7] = new CaseS(new Pos(5,7), Symbole.PENTAGONE);
                    break;
            }
            return nouvelleGrille;
        }
        catch(Exception e)
        {
            System.out.println("Une erreur s'est produite au moment de charger le niveau !");
            System.out.println(e);
            return new Grille();
        }
    }
    
    // Override de toString à des fins de déboggage
    @Override
    public String toString()
    {
        String grille = "";
        for(int i = 0; i < lonX; i++)
        {
            for(int j = 0; j < lonY; j++)
            {
                grille += grilleCT[i][j].getEtat() + " ";
            }
            grille += System.getProperty("line.separator");
        }
        return grille;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //Gestion Chemin
    ////////////////////////////////////////////////////////////////////////////
    
    //Verifie si le symbole sélectionné appartient deja a un des chemins, et supprime le chemin si c'est le cas
    public void testChemins(int c, int r)
    {
        for(int i = 0; i < nbCheminValide ; i++)
        {
            if(tabChem.get(i).getTabCase().get(0).getType() == grilleCT[c][r].getType())
            {   
                for(int y = 1; y< tabChem.get(i).getTabCase().size()-1;y++)
                {
                Pos pos = new Pos(tabChem.get(i).getTabCase().get(y).getPos());
                    grilleCT[pos.getX()][pos.getY()].etat = false;
                    grilleCT[pos.getX()][pos.getY()] = new CaseC(pos.getPos());
                }
                tabChem.remove(i);
                tabChem.add(new Chemin());
                nbCheminValide--;
            }
        }
    }
   
    // Récupère la case où on a débuté le chemin
     public void startDD(int c, int r) 
     {
        testChemins(c,r);
        tabChem.get(nbCheminValide).debuterChemin(grilleCT[c][r]);
        setLastC(c);
        setLastR(r);
        System.out.println("startDD : " + c + "-" + r);
        setChanged();
        notifyObservers();
    }
    // Récupère la case dans laquelle on a terminé le chemin et test la victoire
    public void stopDD(int c, int r) 
    {
        // Si le chemin est valide alors on augmente le nombre de chemins validés
        
        if(tabChem.get(nbCheminValide).getTermine() == true)
        {
            // On parcourt chaque case du chemin 
            for(int i = 0; i < tabChem.get(nbCheminValide).getTabCase().size(); i++)
            {
                // On parcours les cases de la grille
                for(int j = 0; j < lonX; j++)
                {
                    for(int k = 0; k < lonY; k++)
                    {
                        // Si la position de la grille est la même que la case du chemin, alors on lui affecte le type de la case du chemin
                        if(grilleCT[j][k].getPos() == tabChem.get(nbCheminValide).getTabCase().get(i).getPos())
                        {
                            grilleCT[j][k].setType(tabChem.get(nbCheminValide).getTabCase().get(i).getType());
                        }
                    }
                }
            }
            nbCheminValide++;
            testVictoire();
        }
        // Sinon on efface le chemin
        else 
        {
            for(int i = 0; i< tabChem.get(nbCheminValide).getTabCase().size();i++)
            {
                Pos pos = new Pos(tabChem.get(nbCheminValide).getTabCase().get(i).getPos());
                if(! (grilleCT[pos.getX()][pos.getY()] instanceof CaseS))
                    grilleCT[pos.getX()][pos.getY()].etat = false;
            }
            tabChem.get(nbCheminValide).viderCase();
            tabChem.remove(nbCheminValide);
            tabChem.add(new Chemin());
        }
        
        System.out.println("stopDD : " + c + "-" + r + " -> " + lastC + "-" + lastR);
        setChanged();
        notifyObservers();
    }
    
    // Récupère la case parcourue par le chemin et l'ajoute au chemin si elle est valide
    public void parcoursDD(int c, int r) 
    {
        if(!(c == getLastC() && r == getLastR()))
        {
            tabChem.get(nbCheminValide).poursuivreChemin(getGrilleCT()[c][r]);
        }
        setLastC(c);
        setLastR(r);
        System.out.println("parcoursDD : " + c + "-" + r);
        setChanged();
        notifyObservers();
    }
    
    //Vérifie si la grille est entièrement parcourue
    public boolean testVictoire()
    {
        boolean gg = true;
        for(int i=0; i<lonX; i++)
        {
            for(int j=0; j<lonY; j++)
            {
                if(grilleCT[i][j].getEtat() == false)
                    gg=false;
            }
        }
         return gg;
    }
}
