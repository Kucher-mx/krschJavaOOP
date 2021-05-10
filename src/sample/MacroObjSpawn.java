package sample;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MacroObjSpawn {
    private String name;
    Queue ct = new LinkedList();
    Queue t = new LinkedList();

    public MacroObjSpawn(String name){
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

