//Cette classe permet de gérer les politiques d'ordonnancement.

public class Politique {
    
    private IOCommandes myIO = new IOCommandes();	
    private File_ file = new File_();
    private Pile pile = new Pile();



    public Politique(IOCommandes myIO){
        this.myIO = myIO;
    }


    
    //Cette methode permet de faire le tri par FIFO.
    //Cette methode prend en paramètre un tableau de processus.
    //Cette methode retourne un tableau de processus trié par FIFO.
    //Cette methode est utilisée dans la politique FIFO.
    public Processus[] triFIFO(Processus[] process){
        Processus[] processTri = new Processus[process.length];
        for (int i = 0; i < process.length; i++) {
            processTri[i] = process[i];
        }
        for (int i = 0; i < process.length; i++) {
            for (int j = 0; j < process.length-1; j++) {
                if(processTri[j].arrive_t > processTri[j+1].arrive_t){
                    Processus temp = processTri[j];
                    processTri[j] = processTri[j+1];
                    processTri[j+1] = temp;
                }
            }
        }
        return processTri;
    }


    //Cette methode permet de faire le tri par FIFO avec priorité.
    //Cette methode prend en paramètre un tableau de processus.
    //Cette methode recupere la priorité de chaque processus et le trie par ordre croissant.
    //Cette methode retourne un tableau de processus trié par FIFO avec priorité.
    //Cette methode est utilisée dans la politique FIFO avec priorité.
    public Processus[] triFIFOPrio(Processus[] process){
        Processus[] processTri = new Processus[process.length];
        for (int i = 0; i < process.length; i++) {
            processTri[i] = process[i];
        }
        for (int i = 0; i < process.length; i++) {
            for (int j = 0; j < process.length-1; j++) {
                if(processTri[j].priority_l > processTri[j+1].priority_l){
                    Processus temp = processTri[j];
                    processTri[j] = processTri[j+1];
                    processTri[j+1] = temp;
                }
            }
        }
        return processTri;
    }




    //Cette methode permet de calculer le temps d'execution d'un processus.
    //Cette methode prend en paramètre un processus.
    //Cette methode retourne le temps d'execution du processus.
    public float calculTempsExecution(Processus process){
        float tempsExecution = 0;
        tempsExecution = process.remain_t;
        return tempsExecution;
    }

    //Cette methode calcul la somme des temps d'execution des processus.
    //Cette methode prend en paramètre un tableau de processus.
    //Cette methode retourne la somme des temps d'execution des processus.
    public float calculSommeTempsExecution(Processus[] process){
        float sommeTempsExecution = 0;
        for (int i = 0; i < process.length; i++) {
            sommeTempsExecution += calculTempsExecution(process[i]);
        }
        return sommeTempsExecution+process[0].arrive_t;
    }



    //Cette methode permet de determiner si un processus est en attente.
    //Cette methode prend un tableau de processus en paramètre.
    //Cette methode prend une valeur de temps en paramètre.
    //Cette methode retourne un boolean.
    public boolean isWaiting(Processus process, float time){
        if(process.arrive_t == time && !process.isActivable && !process.isActif){
            process.isActivable = true;
            return true;
        }
        return false;
    }

    //Cette methode permet de determiner si un processus est en cours d'execution.
    public boolean isRunning(Processus process, float time){
        return process.isActif;
    }

    //Cette methode permet de verifier si un processus est en cours d'execution.
    //Cette methode prend un tableau de processus en paramètre.
    //Cette methode prend une valeur de temps en paramètre.
    //Cette methode retourne un boolean.
    public boolean isSomeoneRunning(Processus[] process, float time){
        for (int i = 0; i < process.length; i++) {
            if(isRunning(process[i], time)){
                return true;
            }
        }
        return false;
    }

    //Cette methode verifie si un processus peut etre activé.
    public boolean canBeRuned(Processus process, float time){
        return process.arrive_t <= time && !process.isActif && !process.isDone;
    }





    //Cette methode permet de verifier si un processus à terminer.
    //Cette methode prend un processus en paramètre.
    //Cette methode retourne un boolean.
    public boolean isFinished(Processus process){
        return process.timeExecuted == process.remain_t;
    }



    //Cette methode recupere le prochain processus à réaliser.
    //Cette methode prend le processus avec le plus petit temps d'arrivé où la variable isDone est à false.
    //Cette methode retourne l'indice du prochain processus à réaliser.
    public int getNextProcess(Processus[] process){
        int indice = 0;
        for (int i = 0; i < process.length; i++) {
            if(process[i].arrive_t < process[indice].arrive_t && !process[i].isDone){
                indice = i;
            }
        }
        return indice;
    }








    public void basiqueFIFO(Processus[] p){
        myIO.ecrireEcran("Vous avez choisi la politique FIFO Basique\n");
        myIO.ecrireEcran("\n");
        int indiceProc = 0;
        System.out.print("Date              ");
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i].nameProc+"		");

        }
        myIO.ecrireEcran("\n");

        for (float i = 0; i < calculSommeTempsExecution(p); i++) {
            System.out.print(i+"		");
            for (int j = 0; j < p.length; j++) {
                //On verifie si un processus peut être activé.
                if (isFinished(p[j]) && !p[j].isDone) {
                    p[j].isActif = false;
                    p[j].isActivable = false;
                    p[j].isDone = true;
                    indiceProc++;
                    triFIFO(p)[indiceProc].isActif = true;
                    triFIFO(p)[indiceProc].isActivable = true;


                }


                if((canBeRuned(p[j], i) && !isSomeoneRunning(p, i)) || p[j].isActif){
                    p[j].isActif = true;
                    p[j].isActivable = false;
                    System.out.print("A("+p[j].timeExecuted+")          ");
                    p[j].timeExecuted++;
                }else if(isWaiting(p[j], i) || p[j].isActivable){
                    System.out.print("a                ");
                }
                else{
                    System.out.print("-                ");

                }

            }
            System.out.print("\n");
            
            
        }
    }


    public void prioritaireFIFO(Processus[] p){
        myIO.ecrireEcran("Vous avez choisi la politique FIFO Prioritaire\n");
        myIO.ecrireEcran("\n");
        int indiceProc = 0;
        System.out.print("Date              ");
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i].nameProc+"		");

        }
        myIO.ecrireEcran("\n");
        for (float i = 0; i < calculSommeTempsExecution(p); i++) {
            System.out.print(i+"		");

            for (int j = 0; j < triFIFOPrio(p).length; j++) {
                // System.out.println(triFIFOPrio(p)[j].nameProc+" "+triFIFOPrio(p)[j].priority_l);
                //On verifie si un processus peut être activé.
                if (isFinished(p[j]) && !p[j].isDone) {
                    p[j].isActif = false;
                    p[j].isActivable = false;
                    p[j].isDone = true;
                    indiceProc++;
                    triFIFOPrio(p)[indiceProc].isActif = true;
                    triFIFOPrio(p)[indiceProc].isActivable = true;
                }




            }
            System.out.print("\n");









        }
        


    }



    //Cette methode recupere le premier processus à realiser en utilisant l'algorithme de Round Robin.
    //Cette methode prend un tableau de processus en paramètre.
    //Cette methode retourne le processus à réaliser.
    public Processus getFirtProcessusRR(Processus[] p){
        int indice = 0;
        for (int i = 0; i < p.length; i++) {
            if(p[i].arrive_t < p[indice].arrive_t){
                indice = i;
            }
        }
        return p[indice];
    }




    public void roundRobinBasique(Processus[] p){
        myIO.ecrireEcran("Vous avez choisi la politique Round Robin basique\n");
        myIO.ecrireEcran("\n");
        int indiceProc = 0;
        float timeSlice = 10.0f;
        Processus procBuf = null;
        Processus procBuf2 = null;

        System.out.print("Date              ");
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i].nameProc+"		");

        }

        boolean eventHappened = false;
        String affichageFinal = "";
        String line = "";
        myIO.ecrireEcran("\n");
        for (float j = 0; j < calculSommeTempsExecution(p); j++) {
            line += j+"		";
            // pile.show();
            for (int iP= 0; iP< p.length; iP++) {   

                // System.out.print(canBeRuned(p[iP], j));
                // System.out.print(isSomeoneRunning(p, j));
                
                if((canBeRuned(p[iP], j) && !isSomeoneRunning(p, j)) || p[iP].isActif) {
                    timeSlice--;
                    p[iP].isActif = true;
                    p[iP].isActivable = false;
                    line += "A("+p[iP].timeExecuted+")          ";
                    p[iP].timeExecuted++;

                }else if(isWaiting(p[iP], j) || p[iP].isActivable){
                    line += "a                ";
                    pile.push(p[iP]);

                }
                else{
                    line +="-                ";

                }


                if (isFinished(p[iP]) && !p[iP].isDone) {
                    p[iP].isActif = false;
                    p[iP].isActivable = false;
                    p[iP].isDone = true;
                    pile.processQueue.remove(p[iP]);
                    eventHappened = true;
                }


                if(timeSlice == 0 && p[iP].isActif){
                    timeSlice = 10.0f;
                    eventHappened = true;
                    if(!p[iP].isDone){

                        p[iP].isActif = false;
                        p[iP].isActivable = true;
                        pile.processQueue.remove(p[iP]);
                        pile.push(p[iP]);
                        procBuf = pile.first();

                        procBuf.isActif = true;
                        procBuf.isActivable = false;
                        // System.out.println(pile.first().nameProc);
                        // System.out.println(p[iP].nameProc);
                    }
                }
            }
        
            line += "\n";

            if(eventHappened == true){
                affichageFinal += line;
            }
        }
            System.out.print(line);
    }



    public void roundRobinPriorite(Processus[] p){
        myIO.ecrireEcran("Vous avez choisi la politique Round Robin avec priorité.\n");
        myIO.ecrireEcran("\n");
        System.out.print("Date              ");
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i].nameProc+"		");

        }
        myIO.ecrireEcran("\n");
        
        
    }

    public void prioritaireFIFOpreemptif(Processus[] p){
        myIO.ecrireEcran("Vous avez choisi la politique FIFO Prioritaire Preemptif\n");
    }    

}





/*
        for (float i = 0; i < calculSommeTempsExecution(p); i++) {
            System.out.print(i+"		");
            for (int j = 0; j < p.length; j++) {
                if (isRunning(p[j], i)) {
                    System.out.print("    A"+j+"("+(i-p[j].remain_t)+")");
                } else if(p[j].isActif){
                    System.out.print("    A"+j+"("+(i-p[j].remain_t)+")");
                }
                else {
                    System.out.print("          ");
                }


                if(isSomeoneWaiting(p[j], i)){
                    System.out.print("    a");
                } else if(p[j].isActivable){
                    System.out.print("    a");
                } else {
                    System.out.print("          ");
                }


            }
            System.out.print("\n");


        }
*/