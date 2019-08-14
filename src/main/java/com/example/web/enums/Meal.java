package com.example.web.enums;

import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForOffsetDateTime;

/**
 * @Auther: 36560
 * @Date: 2019/8/12 :21:40
 * @Description:
 */
public enum Meal {
    APPETIZER(Food.Appitizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class);
    interface Food{
        enum Appitizer implements Food{
            SALAD,SOUP,SPRING_ROLLS;
        }
        enum MainCourse implements Food{
            LASAGNE,BURRUTO,PAD_THIAI,LENTILS,HUMMOUS,VINDALOO;
        }
        enum Dessert implements Food{
            TIRAMISU,GELATO,BLACK_FOREST_CAKE,FRUIT,CRANE_CARAMEL;
        }
    }
    private Food values[];
    private Meal(Class<? extends Food> kind){
        values=kind.getEnumConstants();
    }
    public Food randomSelection(){
        return EnumGenerator.random(values);
    }

    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            System.out.println("----");
            for (Meal meal:Meal.values()){
                Food food = meal.randomSelection();
                System.out.println(food+" ");
            }
        }
    }
}

