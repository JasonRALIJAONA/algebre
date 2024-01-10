package algebre;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.Vector;

public class Relation implements Serializable{
    String nom;
    ArrayList <?> domaines=new ArrayList<>();
    ArrayList <?> elements =new ArrayList<>();

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList getDomaines() {
        return this.domaines;
    }

    public void setDomaines(ArrayList <?> domaines) {
        this.domaines = domaines;
    }

    public ArrayList getElements() {
        return this.elements;
    }

    public void setElements(ArrayList <?> elements) {
        this.elements = elements;
    }

    public Relation(){

    }

    //Manamboatra arraylist vaovao tsy miovy Pointeur
    public ArrayList<?> copyArrayList (ArrayList<?> source){
        ArrayList<Object[]> copie = new ArrayList<>();

        for (Object[] originalArray : (ArrayList<Object[]>)(source)) {
            Object[] nouvArray = Arrays.copyOf(originalArray, originalArray.length);
            copie.add(nouvArray);
        }

        return copie;
    }

    //manamboatra Relation vaovao tsy mitovy Pointeur
    public Relation copier (){
        Relation retour=new Relation();
        String nomRetour=getNom();

        //Copie du nom
        retour.setNom(nomRetour);

        //copie du domaine
        ArrayList<?> copyDomaine=copyArrayList(getDomaines());
        retour.setDomaines(copyDomaine);
        
        //copie des elements
        ArrayList<?> copyElement=copyArrayList(getElements());
        retour.setElements(copyElement);

        return retour;

    }

    // manala ny doublons anaty relation iray
    public void filtre (){
        ArrayList<Object[]> vaovao= new ArrayList<>();
        for (Object[] objects : (ArrayList<Object[]>)getElements()) {
            if (!isInElement(vaovao,objects)) {
                vaovao.add(objects);
            }
        }

        setElements(vaovao);
    }

    //Fitsapana hamantarana hoe voahaja ve ny faritra sa tsia
    public Object toInstance (String dom,String valeur)throws Exception{
        try {
            switch (dom.toLowerCase()) {
                case "int":
                    return (Integer.parseInt(valeur));
    
                case "double":
                   return(Double.parseDouble(valeur));

    
                case "localdate":
                    return(LocalDate.parse(valeur));
            
                default:
                    break;
            }
            
        } catch (Exception e) {
            Exception a=new Exception("Tsy voahaja ny faritra");
            throw(a);
        }

        return valeur;

    }

    //manampy andalana anaty fafana iray
    public void addElement (String values){
        String[] dom=listDomaine();

        try {
            values=values.replace("(", "").replace(")", "");
            String [] mizara=values.split(",");
    
            if (mizara.length==0 || mizara==null || mizara.length<domaines.size()) {
                System.out.println("Tsy ampy ny nampidirinao");
                return;
            }
    
            if (mizara.length>domaines.size()) {
                System.out.println("Mihoatra ny nampidirinao");    
            }
    
            Object[] temp=new Object[mizara.length];
    
            for (int i = 0; i < temp.length; i++) {
                //testDom(dom[i],mizara[i]);
                temp[i]=toInstance(dom[i],mizara[i]);
            }
    
            this.getElements().add(temp);
            System.out.println("tafiditra");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    //Fitsapana hamantarana hoe mitovy ve ny tsanganan' ny relation 2
    public boolean estCompatible (Relation r){
        int nbrElement1=this.getDomaines().size();
        int nbrElement2=r.getDomaines().size();

        if (nbrElement1==nbrElement2) {
            return true;
        }

        return false;
    }

    public Relation unionAll(Relation r){
        Relation nouveau=new Relation();
        try {
            if (!estCompatible(r)) {
                Exception e=new Exception("Tsy mitovy ny isan ny tsanganana");
                throw e;
            }
    
            nouveau.setNom(this.getNom());
            nouveau.setDomaines(this.getDomaines());
    
            ArrayList <?> unis=new ArrayList<>(this.getElements());
            unis.addAll(r.getElements());
    
            nouveau.setElements(unis);
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return nouveau;
    }

    //Fitsapana hahafantarana hoe mitovy ve ny sanda 2
    public boolean isSame (Object[]a, Object[]b){
        if (a.length!=b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(b[i])==false) {
                return false;
            }
        }

        return true;
    }

    //Fitsapana hamantarana hoe efa anaty andalana ve ireo vondrona sanda
    public boolean isInElement (ArrayList <Object[]> ar, Object[]b){
        for (Object[] obj : ar) {
            if (isSame(obj, b)) {
                return true;
            }

        }
        return false;
    }

    public String[] getColumnName (){
        String[] ret=new String[this.getDomaines().size()];
        ArrayList temp=this.getDomaines();

        for (int i = 0; i < ret.length; i++) {
            Object[] dom=(Object[])temp.get(i);
            ret[i]=(String)dom[1];
        }

        return ret;
    }

    public Relation union (Relation r)throws Exception{
        if (!estCompatible(r)) {
            Exception e=new Exception("Le nombre de domaine n'est pas le meme");
            throw e;
        }
        
        Relation nouveau=new Relation();
        nouveau.setNom(this.getNom());
        nouveau.setDomaines(this.getDomaines());

        ArrayList <Object[]> unis=new ArrayList<>(this.getElements());

        ArrayList <Object[]> autre=r.getElements();

        for (Object[] obj : autre) {
            if (!isInElement(unis, obj)) {
                unis.add(obj);
            }
        }

        nouveau.setElements(unis);

        return nouveau;
    }

    public Relation intersection (Relation r)throws Exception{
        if (!estCompatible(r)) {
            Exception e=new Exception("Le nombre de domaine n'est pas le meme");
            throw e;
        }
        
        Relation nouveau=new Relation();
        nouveau.setNom(this.getNom());
        nouveau.setDomaines(this.getDomaines());

        ArrayList <Object[]> intersect=new ArrayList<>();

        ArrayList <Object[]> autre=r.getElements();

        for (Object[] obj : autre) {
            if (isInElement(this.getElements(), obj)) {
                intersect.add(obj);
            }
        }

        nouveau.setElements(intersect);

        return nouveau;
    }

    //Mampiseho ny fafana iray manontolo
    public void print() {
        ArrayList <?> data  = this.getElements();
        String[] columnName = this.getColumnName();

        if (data == null || data.size() == 0 || ((Object[])data.get(0)).length == 0) {
            System.out.println("Tsy misy anaty fafana");
            return ; // mireturn vide.
        }

        int numRows = data.size();
        int numCols = ((Object[])data.get(0)).length;

        // mikajy ny halavana colonne.
        int[] columnWidths = new int[numCols];
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                int length = String.valueOf(((Object[])data.get(row))[col]).length() + 8;
                columnWidths[col] = Math.max(columnWidths[col], length);
            }
        }

        // Manamboatra String builder.
        StringBuilder table = new StringBuilder();

        // Manamboatra ny lohan'ny fafana.

        for (int col = 0; col < numCols; col++) {
        String line = "+";
        for (int width = 0; width < columnWidths[col] + 2; width++) {
            line += "-";
        }
        table.append(line);
    }
    table.append("+\n");
    
    for (int col = 0; col < columnName.length; col++) {
        String header = String.format("%-" + columnWidths[col] + "s", columnName[col]);
        table.append("| ").append(header).append(" ");
    }
    table.append("|\n");

        // Tsipika manasaraka ny lohan'ny fafana sy ny vatany.
        for (int col = 0; col < numCols; col++) {
            String line = "+";
            for (int width = 0; width < columnWidths[col] + 2; width++) {
                line += "-";
            }
            table.append(line);
        }
        table.append("+\n");

        // Vatan' ny fafana.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                String cellValue = String.format("%-" + columnWidths[col] + "s", ((Object[])data.get(row))[col]);
                table.append("| ").append(cellValue).append(" ");
            }
            table.append("|\n");
        }

        for (int col = 0; col < numCols; col++) {
        String line = "+";
        for (int width = 0; width < columnWidths[col] + 2; width++) {
            line += "-";
        }
        table.append(line);
        }
        table.append("+\n");

        System.out.println(table.toString());
    }


    public Relation (String nom,String dom)throws Exception{
        try {
            this.setNom(nom);
            this.setDomaines(makeDomaine(dom));
            System.out.println("voaforona ny fafana");
        } catch (Exception e) {
            throw e;
        }

    }

    //mamoaka ny anaran'ny tsanganana
    public String[] listDomaine (){
        String[] ldom=new String[this.getDomaines().size()];

        for (int i = 0; i < ldom.length; i++) {
            Object[] temp=((Object[])domaines.get(i));
            String nomClass=((Class)temp[0]).getSimpleName();

            ldom[i]=nomClass;
        }

        return ldom;
    }

    //mitady ny tsanganana voafidy
    public int[] colConcerne (String tsanganana)throws Exception{
        String[] mizara=tsanganana.split(",");
        String[] col=getColumnName();
        int[] retour=new int[mizara.length];
        boolean existe=false;
        int index=0;

        for (int i = 0; i < col.length; i++) {
            for (int j = 0; j < mizara.length; j++) {
                if (col[i].equalsIgnoreCase(mizara[j])) {
                    existe=true;
                    retour[index]=i;
                    index++;
                    break;
                }        
            }
        }

        if (existe==false) {
            Exception e=new Exception ("Tsy misy ny tsanganana nofidinao");
            throw e;
        }
        return retour;
    }

    // maka ny valeur ana ligne izay concerne
    public Object[] elementConcerne (int indice, int[] concerne){
        Object[] ret=new Object[concerne.length];
        Object[] local=(Object[])elements.get(indice);

        for (int i = 0; i < concerne.length; i++) {
            ret[i]=local[concerne[i]];
        }

        return ret;
    }

    //Manao projection
    public Relation projection (String tsanganana)throws Exception{        
        int [] voafidy=colConcerne(tsanganana);
        Relation temp=new Relation();
        temp.setNom(this.getNom());

        //mampiditra ny domaines concerne
        for (int i = 0; i < voafidy.length; i++) {
            temp.getDomaines().add(((ArrayList<Object[]>)getDomaines()).get(voafidy[i]));
        }

        //mampiditra ny lignes concerne
        for (int i = 0; i < elements.size(); i++) {
            Object[] ligne=elementConcerne(i,voafidy);
            temp.getElements().add(ligne);
        }

        temp.filtre();
        return temp;

    }

    public void print (String tsanganana){
        try {
            String[] mizara=tsanganana.split(",");
    
            if (mizara[0].equalsIgnoreCase("*")) {
                print();
                return;
            }
    
            Relation temp=projection(tsanganana);
            temp.print();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void print (String tsanganana,String fepetra){
        try {
            Relation etape1=selection(fepetra);

            String[] mizara=tsanganana.split(",");
    
            if (mizara[0].equalsIgnoreCase("*")) {
                etape1.print();
                return;
            }
    
            Relation temp=etape1.projection(tsanganana);
            temp.print();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //mamorona faritra amin ny alalan' ny soratra azo
    public Class findClass(String type){
        switch (type.toUpperCase()) {
            case "INT":
                return (int.class);

            case "DOUBLE":
                return (double.class);

            case "STRING":
                return (String.class);

            case "DATE":
                return (LocalDate.class);
        
            default:
                break;
        }
        return null;
    }

    public void testDomaine (){

    }

    //mameno ny domaine amin'ny alalan'ny fangatahana azo
    public ArrayList<?> makeDomaine (String donnees) throws Exception{

        //System.out.println(donnees);
        donnees=donnees.replace("(", "").replace(")", "");
        ArrayList<Object[]> temp=new ArrayList<Object[]>();

        String[] mizara=donnees.split(",");
        //System.out.println(mizara.length);

        for (int i = 0; i < mizara.length; i++) {
            Object[] lo=new Object[2];
            String[]kely=mizara[i].split(":");

            Class dom=findClass(kely[0]);

            if(dom==null){
                Exception e=new Exception("Tsy mety ny faritra nampidirinao");
                throw e;
            }

            lo[0]=dom;
            lo[1]=kely[1];

            temp.add(lo);
        }

        return temp;


    }

     //manomboka eto ny vaovao

     public int indexCol (String tsanganana){
        String[] listDom=getColumnName();
        int retour=-1;

        for (int i = 0; i < listDom.length; i++) {
            if (listDom[i].equalsIgnoreCase(tsanganana)) {
                retour=i;
            }
        }

        return retour;
    }

    public boolean testLine (Object a,Object b,String operation)throws Exception{
        switch (operation) {
            case "=":
                return (a.equals(b));

            case "<>":
                return(!(a.equals(b)));

            default:
                return testLineAnnexe(a, b, operation);
        }
    }

    public boolean testLineAnnexe (Object a, Object b, String operator)throws Exception{
        if (a instanceof Integer && b instanceof Integer) {
            int intA=(int) a;
            int intB=(int) b;

            switch (operator) {
                case "<":
                    return intA<intB;

                case "<=":
                    return intA<=intB;

                case ">":
                    return intA>intB;

                case ">=":
                    return intA>=intB;
            
                default:
                    Exception e=new Exception("Tsy mety ny marika hametrahanao ny fepetra");
                    throw e;
            }
        } else if (a instanceof Double && b instanceof Double) {
            double doubleA= (double) a;
            double doubleB= (double) b;

            switch (operator) {
                case "<":
                    return doubleA<doubleB;

                case "<=":
                    return doubleA<=doubleB;

                case ">":
                    return doubleA>doubleB;

                case ">=":
                    return doubleA>=doubleB;
            
                default:
                    Exception e=new Exception("Tsy mety ny marika hametrahanao ny fepetra");
                    throw e;
            }
        }else if (a instanceof LocalDate && b instanceof LocalDate) {
            LocalDate dateA= (LocalDate) a;
            LocalDate dateB= (LocalDate) b;

            switch (operator) {
              case "<":
                    return dateA.isBefore(dateB);

                case "<=":
                    return (dateA.isBefore(dateB) || dateA.isEqual(dateB));

                case ">":
                    return dateA.isAfter(dateB);

                case ">=":
                    return (dateA.isAfter(dateB) || dateA.isEqual(dateB));
            
                default:
                    Exception e=new Exception("Tsy mety ny marika hametrahanao ny fepetra");
                    throw e;
            }
        }

        return false;
    }

    public Relation selection (String fepetra)throws Exception{
        fepetra=fepetra.replace("(", "").replace(")", "");

        Relation temp=new Relation();
        temp.setNom(this.getNom());
        temp.setDomaines(domaines);
        
        //String[] nomCol=getColumnName();
        String[] dom=listDomaine();

        String[] mizara=fepetra.split(" ");

        //maka ny index an ny colonne ho atao comparaison
        int col=indexCol(mizara[0]);
        if (col==-1) {
            Exception e=new Exception("Tsy mety ny fomba fanoratrao ny fepetra, Tsy misy ny tsanganana nofidinao");
            throw e;
        }
        
        int partieDroite=indexCol(mizara[2]);

        if (partieDroite==-1) {
            Object compareur=toInstance(dom[col],mizara[2]);

            for (Object[] ligne : (ArrayList<Object[]>)elements) {
                if (testLine(ligne[col],compareur,mizara[1])) {
                    temp.getElements().add(ligne);
                }
            }
        }

        else{
            for (Object[] ligne : (ArrayList<Object[]>)elements) {
                if (testLine(ligne[col],ligne[partieDroite],mizara[1])) {
                    temp.getElements().add(ligne);
                }
            }   
        }

        int ambiny=mizara.length % 3;

        if (ambiny>0) {
            Method function=null;

            //mametraka ny fonction ho antsoina
            if (mizara[3].equalsIgnoreCase("ary")) {
                function=this.getClass().getDeclaredMethod("intersection", Relation.class);
            }
            else if (mizara[3].equalsIgnoreCase("na")) {
                function=this.getClass().getDeclaredMethod("union", Relation.class);
            }

            //manipy ny hadisoana fanoratra
            if (function==null) {
                Exception e=new Exception("Tsy mety ny fomba fanoratrao ny fepetra ireto no no mety : 'ary','na'");
                throw e;
            }

            String tohiny=String.join(" ",Arrays.copyOfRange(mizara,4,mizara.length));

            return (Relation)(function.invoke(temp,selection(tohiny)));
        }
        else{
            temp.filtre();
            return temp;
        }

    }

    //fusionne 2 Object []
    public Object[] mergeArray (Object[] list1 , Object[] list2){
        Object[] ret = new Object[list1.length + list2.length];
        System.arraycopy(list1, 0, ret, 0, list1.length);
        System.arraycopy(list2, 0, ret, list1.length, list2.length);

        return ret;

    }

    //Produit cartesien de 2 relations
    public Relation cartesien (Relation autre){
            Relation ret=new Relation();

            Relation r1=this.copier();
            Relation r2=autre.copier();

            r1.nomColPrecis();
            r2.nomColPrecis();

            ArrayList<?>autreDom=new ArrayList<>();
            autreDom.addAll(r1.getDomaines());
            autreDom.addAll(r2.getDomaines());
            
            ret.setDomaines(autreDom);

            for (Object[] ligneRel1 : (ArrayList<Object[]>)getElements()) {
                for (Object[] ligneRel2 : (ArrayList <Object[]>)autre.getElements()) {
                    Object[] temporaire=mergeArray(ligneRel1, ligneRel2);
                    ret.getElements().add(temporaire);
                }
            }

            ret.filtre();
            return ret;
    }

    //Produit cartesien pour le jointure naturel
    public Relation cartesienNat (Relation autre){
        Relation ret=new Relation();

        Relation r1=this.copier();
        Relation r2=autre.copier();

        ArrayList<?>autreDom=new ArrayList<>();
        autreDom.addAll(r1.getDomaines());
        autreDom.addAll(r2.getDomaines());
        
        ret.setDomaines(autreDom);

        for (Object[] ligneRel1 : (ArrayList<Object[]>)getElements()) {
            for (Object[] ligneRel2 : (ArrayList <Object[]>)autre.getElements()) {
                Object[] temporaire=mergeArray(ligneRel1, ligneRel2);
                ret.getElements().add(temporaire);
            }
        }

        ret.filtre();
        return ret;
}

    //Jointure avec predicat
    public Relation tetaJointure (Relation r2, String Predicat)throws Exception{
        Relation combine =cartesien(r2);
        return combine.selection(Predicat);
    }

    //liste des colonnes separes par des virgules a utiliser pour la jointure naturelle
    public String listColonne (Relation r2){
        String[] col1=getColumnName();
        String[] col2=r2.getColumnName();

        ArrayList <String> arrayList=new ArrayList<>();
        arrayList.addAll(Arrays.asList(col1));

        for (String string2 : col2) {
            if (arrayList.stream().anyMatch(string2::equalsIgnoreCase)==false) {
                arrayList.add(string2);
            }
        }

        StringJoiner joiner=new StringJoiner(",");

        for (String string : arrayList) {
            joiner.add(string);
        }

        return joiner.toString();
    }

    //indice des collones pour r2 des collones qui ressemblent en nom a r1
    public int[] colRessemblant (Relation r2){
        String[] col1=getColumnName();
        String[] col2=r2.getColumnName();
                
        ArrayList <Integer> arrayList=new ArrayList<>();

        for (int i = 0; i < col2.length; i++) {
            String temporaire=col2[i];

            for (String string : col1) {
                if (temporaire.equalsIgnoreCase(string)) {
                    arrayList.add(i);
                    break;
                }
            }
        }

        return arrayList.stream().mapToInt(Integer::intValue).toArray();
    }

    //ajoute 2 au nom du colonne concerne
    public void changeNomCol (int[] indices){
        for (int i = 0; i < indices.length; i++) {
            String temporaire=(String)((Object[])getDomaines().get(indices[i]))[1];
            temporaire+="2";

            ((Object[])domaines.get(indices[i]))[1]=temporaire;
        }
    }

    //manao hoe fafana.Tsanganana
    public Relation nomColPrecis () {
        Relation temp=new Relation();
        temp.setNom(getNom());
        temp.setElements(getElements());
        temp.setDomaines(getDomaines());

        for (Object[] tsanganana : (ArrayList<Object[]>)(temp.getDomaines())) {
            String anaranaVaovao=temp.getNom()+"."+tsanganana[1];
            tsanganana[1]=anaranaVaovao;
        }

        return temp;
    }

    public Relation deNomColPrecis(){
        Relation temp=new Relation();
        temp.setNom(getNom());
        temp.setElements(getElements());
        temp.setDomaines(getDomaines());

        for (Object[] tsanganana : (ArrayList<Object[]>)(temp.getDomaines())) {
            int indexPoint=((String)tsanganana[1]).indexOf(".");
            
            String anaranaVaovao=((String)tsanganana[1]).substring(indexPoint+1);
            tsanganana[1]=anaranaVaovao;
        }

        return temp;
    }

    //enleve 2 au nom du colonne concerne
    public void deChangeNomCol (int [] indices){
        for (int i = 0; i < indices.length; i++) {
            String temporaire=(String)((Object[])getDomaines().get(indices[i]))[1];
            temporaire=temporaire.substring(0, temporaire.length()-1);

            ((Object[])domaines.get(indices[i]))[1]=temporaire;
        }
    }

    //creation de predicat pour jointureNat
    public String predicatNaturel (int [] indices){
        String[] col=getColumnName();
        
        String retour="";

        for (int i = 0; i < indices.length; i++) {
            retour+=col[indices[i]];
            retour+=" = ";
            retour+=col[indices[i]]+"2";

            if (i!=( indices.length)-1) {
                retour+=" ary ";
            }
        }

        return retour;
    }
    
    public Relation jointureNat (Relation r2)throws Exception{
        int[] memeCol=r2.colRessemblant(this);
        String colToPrint=listColonne(r2);

        r2.changeNomCol(memeCol);
        Relation temp=cartesienNat(r2);
       
        String predicat=predicatNaturel(memeCol);
        
        temp=temp.selection(predicat);
        temp=temp.projection(colToPrint);
        r2.deChangeNomCol(memeCol);
        
        return temp;
    }

    public Relation division (Relation autre, String parametre)throws Exception{
        parametre=parametre.replace("(", "").replace(")", "");
        String[] parties=parametre.split(" ");

        Relation r1=this.copier();
        Relation r2=autre.copier();

        r1.nomColPrecis();
        r2.nomColPrecis();


        String[] listCol1=r1.getColumnName();
        String filtreCol="";

        for (int i = 0; i < listCol1.length; i++) {
            if (listCol1[i].equalsIgnoreCase(parties[0]) == false) {

                filtreCol+=listCol1[i];

                if (i+1 != listCol1.length) {
                    filtreCol+=",";
                }
            }
        }
        
        Relation joint=jointureNat(autre);
        

    }
}