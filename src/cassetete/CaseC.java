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
// Classe d'une Case Chemin
public class CaseC extends Case
{
    boolean estParcourue; // Indique si la case est parcourue (par un chemin)
    
    CaseC(Pos pCase)
    {
        super(pCase, "CASE_CHEMIN");
        estParcourue = false;
    }
}
