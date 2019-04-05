
import java.util.List;

public class Test {
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            List<Card> initCards = CardHelper.getCards();
            List<Card> card1 = CardHelper.deal(initCards);
            System.out.print("用户1:");
            for(Card c:card1){
                System.out.print(c.getSuit()+"_"+c.getNum()+" ");
            }
            System.out.println();
            System.out.print("用户2:");
            List<Card> card2 = CardHelper.deal(initCards);
            for(Card c:card2){
                System.out.print(c.getSuit()+"_"+c.getNum()+" ");
            }
            System.out.println();
            System.out.println(CardHelper.Compare(card1,card2));
            System.out.println("-----------------------");
        }


//        测试结果: 若用户1牌大于用户2的牌 则返回1 反之返回-1
//        用户1:Heart_5 Heart_Q Heart_8
//        用户2:Heart_Q Spade_5 Heart_2
//        1
//        -----------------------
//        用户1:Diamond_2 Diamond_6 Diamond_K
//        用户2:Diamond_6 Heart_2 Club_8
//        1
//        -----------------------
//        用户1:Spade_Q Diamond_K Spade_10
//        用户2:Diamond_K Diamond_2 Club_3
//        1
//        -----------------------
//        用户1:Spade_8 Spade_10 Diamond_A
//        用户2:Spade_10 Heart_4 Club_8
//        1
//        -----------------------
//        用户1:Club_5 Spade_10 Spade_7
//        用户2:Spade_10 Heart_8 Spade_A
//        -1
//        -----------------------
//        用户1:Heart_K Spade_J Spade_6
//        用户2:Spade_J Club_3 Spade_3
//        -1
//        -----------------------
//        用户1:Diamond_10 Heart_4 Heart_K
//        用户2:Heart_4 Diamond_5 Spade_K
//        1
//        -----------------------
//        用户1:Spade_5 Club_7 Diamond_2
//        用户2:Club_7 Diamond_10 Heart_5
//        -1
//        -----------------------
//        用户1:Spade_6 Club_2 Heart_K
//        用户2:Club_2 Heart_10 Spade_4
//        1
//        -----------------------
//        用户1:Diamond_A Club_A Spade_6
//        用户2:Club_A Heart_J Spade_7
//        1
    }
}
