import java.util.ArrayList;


public class Pile{
    public ArrayList<Processus> processQueue;
    public Processus runningProcess;
    public int priority;

    //Constructeur par défaut
    public Pile(){
        this.processQueue = new ArrayList<>();
        this.priority = -1;
    }

    //Constructeur de pile par copie
    public Pile(ArrayList<Processus> processQueue){
        this.processQueue = processQueue;
        this.priority = -1;
    }

    //Constructeur de pile par copie et avec un niveau de priorité
    public Pile(ArrayList<Processus> processQueue, int priority){
        this.processQueue = processQueue;
        this.priority = priority;
    }

    //Retourne et remet le processus qui s'exécute à la fin de la pile
    public Processus first(){
        //Processus processus = this.processQueue.remove(0);
        //this.processQueue.add(processus);
        return this.processQueue.get(0);
    }

    //Pousse le processus en paramètre à la fin de la pile
    public boolean push(Processus processus){
        boolean pushed = false;
        if(this.processQueue.indexOf(processus) == -1){
            // if(this.processQueue.size() == 1){
            //     Processus proc = this.processQueue.remove(0);
            //     pushed = this.processQueue.add(processus);
            //     this.processQueue.add(proc);
            // }else{
            pushed = this.processQueue.add(processus);
            //}
        }
        return pushed;
    }

    //Cette methode permet de monter les elements de la pile.
    //Lorsque le premier element de la pile est remove il est mis à la derniere position.
    public void up(){
        Processus processus = this.processQueue.remove(0);
        this.processQueue.add(processus);
    }
    

    public void show(){
        for(int i = 0;i < this.processQueue.size();i++){
            System.out.println(this.processQueue.get(i).nameProc);
        }
    }
}


/*
    public Processus first(){
        Processus processus = this.processQueue.remove(0);
        this.processQueue.add(processus);
        return processus;
    }
*/