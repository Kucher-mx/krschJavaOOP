package sample;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
public class MacroObjSite {
    private String name;
    Queue<MicroObject> ct = new LinkedList<>();
    Queue<MicroObject> t = new LinkedList<>();

    public MicroObject get(Queue<MicroObject> obj, int idx){
        MicroObject element = null;
        int size = obj.size();
        for (int i = 0; i <= size; i++) {
            if (i == idx) {
                element = obj.remove();
            } else {
                obj.add(obj.remove());
            }
        }
        return element;
    }

    public MacroObjSite(String name) {
        this.name = name;
    }

    public void addCt(MicroObject obj) {
        ct.add(obj);
    }

    public void addT(MicroObject obj) {
        t.add(obj);
    }

    public void removeCt(MicroObject obj) {
        ct.remove(obj);
    }

    public void removeT(MicroObject obj) {
        t.remove(obj);
    }

    public void printTeamT(){

        Iterator iterator = this.t.iterator();
        if(t.peek() == null){
            System.out.println("It's nobody there");
        }else{
            System.out.println("T side members: ");
        }

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + "\n");
        }
    }

    public void printTeamCT(){
        Iterator iterator = this.ct.iterator();
        if(ct.peek() == null){
            System.out.println("It's nobody there");
        }else{
            System.out.println("CT side members: ");
        }

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + "\n");
        }
    }
}

