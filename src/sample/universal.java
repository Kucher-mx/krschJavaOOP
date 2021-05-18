package sample;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.UUID;

public class universal {
    Scanner in = new Scanner(System.in);
    private int teamSize;
    LinkedList microObjectsT  = new LinkedList();
    LinkedList microObjectsCT  = new LinkedList();
    LinkedList spawns  = new LinkedList();
    LinkedList sites  = new LinkedList();

    public universal() throws FileNotFoundException {
        System.out.print("size of the teams: ");
        teamSize = in.nextInt();

        spawns.add(new MacroObjSpawn("t"));
        spawns.add(new MacroObjSpawn("ct"));

        sites.add(new MacroObjSite("A"));
        sites.add(new MacroObjSite("B"));

        System.out.println("filling ct team... ");
        this.createArraysMicroCT(teamSize);

        System.out.println("filling t team... ");
        this.createArraysMicroT(teamSize);
    }

    public void createArraysMicroT(int size) throws FileNotFoundException {
        for (int i = 0; i < size; i++){
            System.out.print("enter a lvl of the unit: ");
            int lvl = in.nextInt();
            MicroObject tmp;
            switch (lvl){
                case 1:
                    tmp = new MicroObject("t");
                    break;
                case 2:
                    tmp = new MicroObjectOne("t");
                    break;
                case 3:
                    tmp = new MicroObjectTwo("t");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + lvl);
            }
            this.microObjectsT.add(tmp);
        }
    }

    public void createArraysMicroCT(int size) throws FileNotFoundException {
        for (int i = 0; i < size; i++){
            System.out.print("enter a lvl of the unit: ");
            int lvl = in.nextInt();
            MicroObject tmp;
            switch (lvl){
                case 1:
                    tmp = new MicroObject("ct");
                    break;
                case 2:
                    tmp = new MicroObjectOne("ct");
                    break;
                case 3:
                    tmp = new MicroObjectTwo("ct");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + lvl);
            }
            this.microObjectsCT.add(tmp);
        }
    }

    public void addMicroObj() throws FileNotFoundException {
        System.out.println("enter a side of the unit t-ct");
        String unitSide = in.next();
        System.out.println("enter a level of the unit 1-3");
        int unitLvl = in.nextInt();
        MicroObject unit;
        switch (unitLvl){
            case 1:
                unit = new MicroObject(unitSide);
                break;
            case 2:
                unit = new MicroObjectOne(unitSide);
                break;
            case 3:
                unit = new MicroObjectTwo(unitSide);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + unitLvl);
        }
        System.out.println("where do you want to push an Micro: uni - universal, A - A plant, B - B plant, ct - ct spawn, t - t spawn");
        String choiceToAdd = in.next();

        if (unitSide.equals("t")){
            switch (choiceToAdd){
                case "uni":
                    microObjectsT.add(unit);
                    break;
                case "A":
                    MacroObjSite at = (MacroObjSite) sites.get(0);
                    at.addT(unit);
                    break;
                case "B":
                    MacroObjSite bt = (MacroObjSite) sites.get(1);
                    bt.addT(unit);
                    break;
                case "t":
                    MacroObjSpawn t = (MacroObjSpawn) spawns.get(0);
                    t.addT(unit);
                    break;
                case "ct":
                    MacroObjSpawn ct = (MacroObjSpawn) spawns.get(1);
                    ct.addT(unit);
                    break;
            }
        }else if(unitSide.equals("ct")){
            switch (choiceToAdd){
                case "uni":
                    microObjectsCT.add(unit);
                    break;
                case "A":
                    MacroObjSite act = (MacroObjSite) sites.get(0);
                    act.addCt(unit);
                    break;
                case "B":
                    MacroObjSite bct = (MacroObjSite) sites.get(1);
                    bct.addCt(unit);
                    break;
                case "t":
                    MacroObjSpawn t = (MacroObjSpawn) spawns.get(0);
                    t.addCt(unit);
                    break;
                case "ct":
                    MacroObjSpawn ct = (MacroObjSpawn) spawns.get(1);
                    ct.addCt(unit);
                    break;
            }
        }

    }


    public void printAll(){
        System.out.println("print site A: ");
        MacroObjSite firtst = (MacroObjSite) sites.get(0);
        firtst.printTeamCT();
        System.out.println();
        firtst.printTeamT();

        System.out.println("print site B: ");
        MacroObjSite second = (MacroObjSite) sites.get(1);
        second.printTeamCT();
        System.out.println();
        second.printTeamT();

        System.out.println("print t spawn: ");
        MacroObjSpawn third = (MacroObjSpawn) spawns.get(0);
        third.printTeamCT();
        System.out.println();
        third.printTeamT();

        System.out.println("print ct spawn: ");
        MacroObjSpawn fourth = (MacroObjSpawn) spawns.get(1);
        fourth.printTeamCT();
        System.out.println();
        fourth.printTeamT();

        System.out.println("print an universal Obj: ");
        System.out.println("print universal ct: ");
        for(int p=0; p < this.microObjectsCT.size(); p++) {
            System.out.println(this.microObjectsCT.get(p));
        }
        System.out.println("print universal t: ");
        for(int p=0; p < this.microObjectsT.size(); p++) {
            System.out.println(this.microObjectsT.get(p));
        }
    }

    public void countMicro(){
        System.out.println("enter a lvl to found");
        int lvl = in.nextInt();
        System.out.println("enter a side to found");
        String side = in.next();

        int countUni = 0;
        int countSites = 0;
        int countSpawns = 0;
        MacroObjSite firtst = (MacroObjSite) sites.get(0);
        MacroObjSite second = (MacroObjSite) sites.get(1);
        Iterator iteratorOne = firtst.ct.iterator();
        Iterator iteratorTwo = firtst.t.iterator();
        Iterator iteratorThree = second.ct.iterator();
        Iterator iteratorFour = second.t.iterator();

        MacroObjSpawn third = (MacroObjSpawn) spawns.get(0);
        MacroObjSpawn fourth = (MacroObjSpawn) spawns.get(1);
        Iterator iteratorFive = third.ct.iterator();
        Iterator iteratorSix = third.t.iterator();
        Iterator iteratorSeven = fourth.ct.iterator();
        Iterator iteratorEight = fourth.t.iterator();

        countSites += this.countInQueue(iteratorOne, lvl, side);
        countSites += this.countInQueue(iteratorTwo, lvl, side);
        countSites += this.countInQueue(iteratorThree, lvl, side);
        countSites += this.countInQueue(iteratorFour, lvl, side);

        countSpawns += this.countInQueue(iteratorFive, lvl, side);
        countSpawns += this.countInQueue(iteratorSix, lvl, side);
        countSpawns += this.countInQueue(iteratorSeven, lvl, side);
        countSpawns += this.countInQueue(iteratorEight, lvl, side);

        for(int p=0; p < this.microObjectsT.size(); p++) {
            MicroObject tmp = (MicroObject) this.microObjectsT.get(p);
            if(tmp.getLvl() == lvl && tmp.getSide().equals(side)){
                countUni++;
            }
        }

        for(int p=0; p < this.microObjectsCT.size(); p++) {
            MicroObject tmp = (MicroObject) this.microObjectsCT.get(p);
            if(tmp.getLvl() == lvl && tmp.getSide().equals(side)){
                countUni++;
            }
        }

        System.out.println("MicroObjects with this characteristics in Uni obj: " + countUni);
        System.out.println("MicroObjects with this characteristics in Sites: " + countSites);
        System.out.println("MicroObjects with this characteristics in Spawns: " + countSpawns);
    }

    public int countInQueue(Iterator iterator,int lvl, String side){
        int count = 0;
        while (iterator.hasNext()) {
            MicroObject element = (MicroObject) iterator.next();
            if(element.getLvl() == lvl && element.getSide().equals(side)){
                count++;
            }
        }
        return count;
    }

    public void deleteMicro() throws FileNotFoundException {
        System.out.println("enter the side of the unit: (t-ct) ");
        String side = in.next();
        System.out.println("enter the lvl of the unit: (1-3) ");
        int lvl = in.nextInt();
        System.out.println("where do you want to push an Micro: uni - universal, A - A plant, B - B plant, ct - ct spawn, t - t spawn");
        String choiceToAdd = in.next();
        MicroObject unit;
        switch (lvl){
            case 1:
                unit = new MicroObject("t");
                break;
            case 2:
                unit = new MicroObjectOne("t");
                break;
            case 3:
                unit = new MicroObjectTwo("t");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + lvl);
        }
        if(side.equals("t")) {
            switch (choiceToAdd) {
                case "uni":
                    microObjectsT.remove(unit);
                    break;
                case "A":
                    MacroObjSite at = (MacroObjSite) sites.get(0);
                    at.removeT(unit);
                    break;
                case "B":
                    MacroObjSite bt = (MacroObjSite) sites.get(1);
                    bt.removeT(unit);
                    break;
                case "t":
                    MacroObjSpawn t = (MacroObjSpawn) spawns.get(0);
                    t.removeT(unit);
                    break;
                case "ct":
                    MacroObjSpawn ct = (MacroObjSpawn) spawns.get(1);
                    ct.removeT(unit);
                    break;
            }
        }else{
            switch (choiceToAdd) {
                case "uni":
                    microObjectsCT.remove(unit);
                    break;
                case "A":
                    MacroObjSite at = (MacroObjSite) sites.get(0);
                    at.removeCt(unit);
                    break;
                case "B":
                    MacroObjSite bt = (MacroObjSite) sites.get(1);
                    bt.removeCt(unit);
                    break;
                case "t":
                    MacroObjSpawn t = (MacroObjSpawn) spawns.get(0);
                    t.removeCt(unit);
                    break;
                case "ct":
                    MacroObjSpawn ct = (MacroObjSpawn) spawns.get(1);
                    ct.removeCt(unit);
                    break;
            }
        }
    }

    public void interactWithMicro(){
        Map<Integer, MicroObject> teamT = new HashMap<>();
        Map<Integer, MicroObject> teamCT = new HashMap<>();
        System.out.println("enter the first lvl of the unit: (1-3) ");
        int lvl1 = in.nextInt();
        System.out.println("enter the second lvl of the unit: (1-3) ");
        int lvl2 = in.nextInt();

        MacroObjSite firtst = (MacroObjSite) sites.get(0);
        MacroObjSite second = (MacroObjSite) sites.get(1);

        MacroObjSpawn third = (MacroObjSpawn) spawns.get(0);
        MacroObjSpawn fourth = (MacroObjSpawn) spawns.get(1);

        findInQueue(firtst.ct, firtst.t, lvl1, lvl2, teamT, teamCT);
        findInQueue(second.ct, second.t, lvl1, lvl2, teamT, teamCT);
        findInQueue(third.ct, third.t, lvl1, lvl2, teamT, teamCT);
        findInQueue(fourth.ct, fourth.t, lvl1, lvl2, teamT, teamCT);

        findInUni(lvl1, lvl2, teamT, "t");
        findInUni(lvl1, lvl2, teamCT, "Ct");

        Map<String, MicroObject> resOfTheBattle = new HashMap<>();
        Iterator t = teamT.entrySet().iterator();
        Iterator ct = teamCT.entrySet().iterator();

        while (t.hasNext() && ct.hasNext()) {
            Map.Entry<Integer, MicroObject> unitT = (Map.Entry<Integer, MicroObject>) t.next();
            Map.Entry<Integer, MicroObject> unitCT = (Map.Entry<Integer, MicroObject>) ct.next();
            unitCT.getValue().interactWith(unitT.getValue());
            String result = unitCT.getValue().interactWith(unitT.getValue());
            if(result.equals("ct")){
                UUID uniqueKey = UUID.randomUUID();
                String id = "t_" + uniqueKey.toString();
                resOfTheBattle.put(id, unitCT.getValue());
            }else if(result.equals("t")){
                UUID uniqueKey = UUID.randomUUID();
                String id = "c_" + uniqueKey.toString();
                resOfTheBattle.put(id, unitT.getValue());
            }
        }

        checkHpMap(teamT);
        checkHpMap(teamCT);
        checkHpList(microObjectsT);
        checkHpList(microObjectsCT);
        checkHpQueue(firtst.ct, firtst.t);
        checkHpQueue(second.ct, second.t);
        checkHpQueue(third.ct, third.t);
        checkHpQueue(fourth.ct, fourth.t);


        if(teamT.isEmpty()){
            System.out.println("CT's has won the battle");
        }else if(teamCT.isEmpty()){
            System.out.println("T's has won the battle");
        }
    }

    public void checkHpList(LinkedList list){
        Iterator it = list.iterator();

        while(it.hasNext()){
            MicroObject element = (MicroObject) it.next();
            if(element.getHp() <= 0){
                it.remove();
            }
        }
    }

    public void checkHpQueue(Queue queue1, Queue queue2){
        Iterator it1 = queue1.iterator();
        Iterator it2 = queue2.iterator();

        while(it1.hasNext()){
            MicroObject element = (MicroObject) it1.next();
            if(element.getHp() <= 0){
                it1.remove();
            }
        }

        while(it2.hasNext()){
            MicroObject element = (MicroObject) it2.next();
            if(element.getHp() <= 0){
                it2.remove();
            }
        }
    }

    public void checkHpMap(Map mapToChange){
        Iterator iterator = mapToChange.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, MicroObject> entry = (Map.Entry<Integer, MicroObject>) iterator.next();
            if (entry.getValue().getHp() <= 0) {
                iterator.remove();
            }
        }
    }

    public void findInQueue(Queue queue1, Queue queue2,int lvlOne, int lvlTwo, Map arrToPushT, Map arrToPushCt){
        Iterator it1 = queue1.iterator();
        Iterator it2 = queue2.iterator();
        while(it1.hasNext()){
            MicroObject element = (MicroObject) it1.next();
            if((element.getLvl() == lvlOne || element.getLvl() == lvlTwo) && element.getSide().equals("t")){
                UUID uniqueKey = UUID.randomUUID();
                String id = "t_" + uniqueKey.toString();
                arrToPushT.put(id, element);
            } else if((element.getLvl() == lvlOne || element.getLvl() == lvlTwo) && element.getSide().equals("ct")){
                UUID uniqueKey = UUID.randomUUID();
                String id = "ct_" + uniqueKey.toString();
                arrToPushCt.put(id, element);
            }
        }

        while(it2.hasNext()){
            MicroObject element = (MicroObject) it2.next();
            if((element.getLvl() == lvlOne || element.getLvl() == lvlTwo) && element.getSide().equals("t")){
                UUID uniqueKey = UUID.randomUUID();
                String id = "t_" + uniqueKey.toString();
                arrToPushT.put(id, element);
            } else if((element.getLvl() == lvlOne || element.getLvl() == lvlTwo) && element.getSide().equals("ct")){
                UUID uniqueKey = UUID.randomUUID();
                String id = "ct_" + uniqueKey.toString();
                arrToPushCt.put(id, element);
            }
        }
    }

    public void findInUni(int lvlOne, int lvlTwo, Map arrToPush, String side){
        if(side.equals("t")){
            int i = 0;
            for(int p=0; p < this.microObjectsT.size(); p++) {
                MicroObject tmp = (MicroObject) this.microObjectsT.get(p);
                if(tmp.getLvl() == lvlOne || tmp.getLvl() == lvlTwo){
                    arrToPush.put(i, tmp);
                    i++;
                }
            }
        }else {
            int i = 0;
            for(int p=0; p < this.microObjectsCT.size(); p++) {
                MicroObject tmp = (MicroObject) this.microObjectsCT.get(p);
                if(tmp.getLvl() == lvlOne || tmp.getLvl() == lvlTwo){
                    arrToPush.put(i, tmp);
                    i++;
                }
            }
        }

    }

    public void InteractInMacro(){
        System.out.println("In which macro you wanna fight? (A or B)");
        String site = in.next();
        Queue<String> resOfTheBattle = new LinkedList<>();
        if(site.equals("A")){
            MacroObjSite A = (MacroObjSite) sites.get(0);
            Iterator t = A.t.iterator();
            Iterator ct = A.ct.iterator();

            while (t.hasNext() && ct.hasNext()) {
                MicroObject unitT = (MicroObject) t.next();
                MicroObject unitCT = (MicroObject) ct.next();
                String result = unitT.interactWith(unitCT);
                if(result.equals("t")){
                    resOfTheBattle.add("t");
                }else if(result.equals("ct")){
                    resOfTheBattle.add("ct");
                }
            }

            Iterator res = resOfTheBattle.iterator();
            while (res.hasNext()) {
                String resValue = (String) res.next();
                if(resValue.equals("t")){
                    removeFromTheQueue(A.ct);
                    moveFromTheQueue(A.t);
                }else if(resValue.equals("ct")){
                    removeFromTheQueue(A.t);
                    moveFromTheQueue(A.ct);
                }
            }
            while(t.hasNext() && ct.hasNext()){
                System.out.println(t.next() + "\t" + ct.next());
            }
            if(!t.hasNext()){
                System.out.println("CT's has took this spot");
            }else if(!ct.hasNext()){
                System.out.println("T's has took this spot");
            }

        }else if(site.equals("B")){
            MacroObjSite B = (MacroObjSite) sites.get(0);
            Iterator t = B.t.iterator();
            Iterator ct = B.ct.iterator();

            while (t.hasNext() && ct.hasNext()) {
                MicroObject unitT = (MicroObject) t.next();
                MicroObject unitCT = (MicroObject) ct.next();
                String result = unitT.interactWith(unitCT);
                if(result.equals("t")){
                    resOfTheBattle.add("t");
                }else if(result.equals("ct")){
                    resOfTheBattle.add("ct");
                }
            }

            Iterator res = resOfTheBattle.iterator();
            while (res.hasNext()) {
                String resValue = (String) res.next();
                if(resValue.equals("t")){
                    removeFromTheQueue(B.ct);
                    moveFromTheQueue(B.t);
                }else if(resValue.equals("ct")){
                    removeFromTheQueue(B.t);
                    moveFromTheQueue(B.ct);
                }
            }
            if(!t.hasNext()){
                System.out.println("CT's has took this spot");
            }else if(!ct.hasNext()){
                System.out.println("T's has took this spot");
            }
        }
    }

    public void removeFromTheQueue(Queue<MicroObject> obj){
        int size = obj.size();
        for (int i = 0; i <= size; i++) {
            if (i == 0) {
                obj.remove();
            }
        }
    }

    public void moveFromTheQueue(Queue<MicroObject> obj){
        int size = obj.size();
        for (int i = 0; i <= size; i++) {
            if (i == 0) {
                obj.add(obj.remove());
            }
        }
    }

    public boolean callBase(MicroObject obj){
        System.out.println("We have microobject: " + obj + ", lets call a base method from the parent, if we can");
        System.out.println("Call with msg? (yes > 0, no - 0)");
        int msg = in.nextInt();
        if(obj instanceof MicroObjectOne) {
            if (msg > 0) {
                obj.callBaseMethodWithMsg();
            } else if (msg == 0) {
                obj.callBaseMethod();
            } else {
                System.out.println("You have chosen wrong");
            }
        }else{
            System.out.println("Tmp is not the secondary micro");
            return true;
        }
        return false;
    }

}

