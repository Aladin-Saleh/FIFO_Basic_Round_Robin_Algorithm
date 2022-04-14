import java.util.ArrayList;

//Cette classe est une file FIFO.


public class File_ {
    private ArrayList<Processus> file = new ArrayList<Processus>();



    public File_(){

    }


    public void push(Processus i){
        file.add(i);
    }

    public void pop(){
        file.remove(0);
    }

    public Processus peek(){
        return file.get(0);
    }


    public ArrayList<Processus> getFile(){
        return this.file;
    }


    //this method returns the index of the process who has the same name as the parameter.
    public int getIndex(String name){
        int i = 0;
        for (i = 0; i < file.size(); i++) {
            if(file.get(i).nameProc.equals(name))
                return i;
        }
        return -1;
    }

    public boolean isSomeoneElseActif(){
        for (int i = 0; i < file.size(); i++) {
            if(file.get(i).isActif)
                return true;
        }
        return false;
    }


    public boolean isSomeoneStartAtTheSameTime(float time){
        for (int i = 0; i < file.size(); i++) {
            if(file.get(i).arrive_t == time)
                return true;
        }
        return false;
    }
    





}
