package affichage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import algebre.*;

public class Run {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        try {
            
            //premier relation
    
            Relation r1=new Relation();
            String nom1="havia";
            r1.setNom(nom1);
    
            Object[] dom1=new Object[2];
            dom1[0]=(int.class);
            dom1[1]="idU";
    
            Object[] dom2=new Object[2];
            dom2[0]=(String.class);
            dom2[1]="nom";
    
            Object[] dom3=new Object[2];
            dom3[0]=(double.class);
            dom3[1]="argent";
    
            ArrayList <Object[]> listDom=new ArrayList();
            listDom.add(dom1);
            listDom.add(dom2);
            listDom.add(dom3);
    
            r1.setDomaines(listDom);
            double vola=0;
    
            Object []ligne1=new Object[3];
            ligne1[0]=1;
            ligne1[1]="Jason";
            vola=5000;
            ligne1[2]=vola;
    
            Object []ligne2=new Object[3];
            ligne2[0]=2;
            ligne2[1]="Tsinjo";
            vola=1000;
            ligne2[2]=vola;
    
            Object []ligne3=new Object[3];
            ligne3[0]=3;
            ligne3[1]="Mercia";
            vola=2000;
            ligne3[2]=vola;
    
            ArrayList<Object[]> elements1= new ArrayList();
            elements1.add(ligne1);
            elements1.add(ligne2);
            elements1.add(ligne3);
    
            r1.setElements(elements1);
        
            // deuxieme relation
    
            Relation r2=new Relation();
            String nom2="havanana";
            r2.setNom(nom2);
    
            Object[] domaine1=new Object[2];
            domaine1[0]=(int.class);
            domaine1[1]="idU";
    
            Object[] domaine2=new Object[2];
            domaine2[0]=(String.class);
            domaine2[1]="nom";
    
            Object[] domaine3=new Object[2];
            domaine3[0]=(double.class);
            domaine3[1]="argenthafa";
    
            ArrayList <Object[]> listDomaine=new ArrayList();
            listDomaine.add(domaine1);
            listDomaine.add(domaine2);
            listDomaine.add(domaine3);
    
            r2.setDomaines(listDomaine);
    
            Object []li1=new Object[3];
            li1[0]=1;
            li1[1]="Jason";
            vola=5000;
            li1[2]=vola;
    
            Object []li2=new Object[3];
            li2[0]=2;
            li2[1]="Ny avo";
            vola=1000;
            li2[2]=vola;
    
            Object []li3=new Object[3];
            li3[0]=3;
            li3[1]="Andy";
            vola=3000;
            li3[2]=vola;
    
            ArrayList<Object[]> elements2= new ArrayList();
            elements2.add(li1);
            elements2.add(li2);
            elements2.add(li3);
    
            r2.setElements(elements2);
    
            try {
                Relation bebe=r1.jointureNat(r2);
                bebe.print();
            } catch (Exception e) {
                e.printStackTrace();
            }


            Jsql jasonSql=new Jsql();
            jasonSql.addRelations(r1);
            jasonSql.addRelations(r2);
            //jasonSql.addRelations(bebe);

            //System.out.println(r1.listColonne(r2));
            while (true) {
                System.out.print("JSQL =>");
                String hataka = scanner.nextLine();
                jasonSql.run(hataka);
            }

        } catch (Exception e) {
            scanner.close();
            System.out.println(e.getMessage());
        }

    }
}
