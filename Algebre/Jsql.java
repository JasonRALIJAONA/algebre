package algebre;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Jsql {
    ArrayList <Relation> relations=new ArrayList<Relation>();

    public ArrayList getRelations() {
        return this.relations;
    }

    public void setRelations(ArrayList <Relation> relations) {
        this.relations = relations;
    }
    
    public void addRelations (Relation r){
        relations.add(r);
    }

    //Mamoaka relation amin' ny alalan' ny anarany
    public Relation extractRel (String nom){
        Relation ret=new Relation();

        for (Relation relation : relations) {
            String anaranaRel=relation.getNom();
            if (anaranaRel.equalsIgnoreCase(nom)) {
                ret=relation;
            }
        }

        return ret;
    }

    //Mamoaka ny indexe ana String iray anaty String[]

    public int indString (String[] tab, String soratra){
        int i=-1;

        for (int j = 0; j < tab.length; j++) {
            if (tab[j].equalsIgnoreCase(soratra)) {
                i=j;
                break;
            }
        }

        return i;
    }


    //mandefa ny Jsql
    public void run(String fangatahana)throws Exception{
        String[] mizara= fangatahana.split(" (?![^(]*\\))");

        String lohany=mizara[0];

        switch (lohany.toUpperCase()) {
            case "MIVOAKA":
                //save();
                Exception e=new Exception("Nivoaka ny Jsql ianao");
                throw e;

            case "ASEHOY":
                if (mizara.length>2) {                    
                    String tohiny=mizara[1];
                    String fafana=mizara[2];
                    Relation temp= extractRel(fafana);

                    int indAkambana=indString(mizara,"mifandray");
                    if (indAkambana != -1) { 
                        if (mizara[indAkambana+2].equalsIgnoreCase("@")==false) {
                            System.out.println("Tsy mety ny fangatahanao . Tokony hoe fafana1 mifandray fafana2 @ (fepetra)");
                            break;
                        }
                        try {            
                            Relation r2=extractRel(mizara[indAkambana+1]);
                            temp=temp.tetaJointure(r2,mizara[indAkambana+3]);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                    }

                    int indUnion=indString(mizara,"akambana");
                    if (indUnion != -1) { 
                        try {            
                            Relation r2=extractRel(mizara[indUnion+1]);
                            temp=temp.union(r2);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                    }

                    int indCart=indString(mizara,"cartesien");
                    if (indCart != -1) { 
                        try {            
                            Relation r2=extractRel(mizara[indCart+1]);
                            temp=temp.cartesien(r2);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                    }

                    int indNat=indString(mizara,"jointureNat");
                    if (indNat != -1) { 
                        try {            
                            Relation r2=extractRel(mizara[indNat+1]);
                            temp=temp.jointureNat(r2);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                    }

                    int indKa=indString(mizara, "ka");
                    if (indKa !=-1) {
                        try {
                            temp=temp.selection(mizara[indKa+1]);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                    }
                    temp.print(tohiny);
                }
                break;
                
            case "AMPIDIRO":
                if (mizara.length>2) {
                    String anaty=mizara[1];
                    if (anaty.equalsIgnoreCase("anaty")==false) {
                        System.out.println("Tsy mety ny fomba fanoratrao, Tokony hoe : 'ampidiro anaty'");
                        break;
                    }
                    String fafana=mizara[2];
                    Relation rel= extractRel(fafana);
                    String val=mizara[3];
                    rel.addElement(val);
                }
                break;

            case "MANAMBOARA":
                if (mizara.length>2) {
                    String fafana=mizara[1];
                    if (fafana.equalsIgnoreCase("fafana")==false) {
                        System.out.println("Tsy mety ny fomba fanoratrao, Tokony hoe : 'manamboara fafana' ");
                        break;
                    }

                    String anarana=mizara[2];
                    String col=mizara[3];
                    try {
                        Relation rel= new Relation(anarana, col);
                        relations.add(rel);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;
        
            default:
                System.out.println("Tsy nety ny hataka nataonao");
                break;
        }
    }
    
    public Jsql (){
        // try {
        //     load();
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }
    }

    // sauvegarde dans un fichier
    public void save ()throws Exception{
        FileOutputStream fileOutputStream=new FileOutputStream("save.txt");
        ObjectOutputStream out=new ObjectOutputStream(fileOutputStream);
        out.writeObject(this.getRelations());

        out.close();
    }

    // prends les objets dans un fichier
    public void load ()throws Exception{
        FileInputStream fileInputStream=new FileInputStream("save.txt");
        ObjectInputStream in=new ObjectInputStream(fileInputStream);
        relations=((ArrayList<Relation>)in.readObject());

        in.close();
    }
}
