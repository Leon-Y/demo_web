package com.example.web.genericity;

/**
 * @Auther: 36560
 * @Date: 2019/7/14 :11:21
 * @Description:
 */
interface superPower{
    void power();
}
interface smellPower{
    void smell();
}
class SuperHero implements superPower,smellPower{
    @Override
    public void power() {
        System.out.println("powerfull");
    }

    @Override
    public void smell() {
        System.out.println("smell more");
    }
}
class EdgeTest<T extends smellPower,F extends superPower>{
    private F superPower;
    private T smellPower;
    public EdgeTest(F superPower,T smellPower){
        this.smellPower = smellPower;
        this.superPower = superPower;
    }
    public void smellPower(){
        smellPower.smell();
    }
    public void powerFull(){
        superPower.power();
    }

    public static void main(String[] args) {
        EdgeTest<smellPower, superPower> smellPowersuperPowerEdgeTest = new EdgeTest<smellPower, superPower>(new SuperHero(),new SuperHero());
        smellPowersuperPowerEdgeTest.smellPower();
        smellPowersuperPowerEdgeTest.powerFull();

    }
}
public class Edge {
}
