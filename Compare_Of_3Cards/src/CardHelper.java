import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 扑克牌辅助类
 */
public class CardHelper {
    private static String[] suitArr = new String[]{"Diamond","Club","Heart", "Spade"};// 分别为方片 梅花 红心 黑桃
    private static String[] numArr = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};


    /**
     * 获得一副洗过的牌
     * @return 一副洗过的牌
     */
    public static List<Card> getCards(){
        List<Card> cards = new ArrayList<>(52);

        // 得到一副未洗的牌
        for(int i=0;i<suitArr.length;i++){
            for(int j=0;j<numArr.length;j++){
                Card c = new Card(suitArr[i],numArr[j]);
                cards.add(c);
            }
        }

        // 洗牌
        Collections.shuffle(cards);
        return cards;
    }

    /**
     *
     * @param cards 牌库
     * @return 牌表面的三张牌
     */
    public static List<Card> deal(List<Card> cards){
        List<Card> getCards = new ArrayList<>(3);
        for(int i=0;i<3;i++){
            getCards.add(cards.get(i));
        }
        cards.remove(0);
        cards.remove(1);
        cards.remove(2);
        return getCards;
    }

    /**
     * 得到三张牌的大小及最大牌的花色 的组合字符串
     * @param card 要进行确定三张牌
     * @return 用于确定大小的字符串 比如黑桃A 红桃K 方片10 则返回"EDA4"
     *          对于非对子型牌:前三位分别是牌值从大到小所对应的16进制数 2~9 10 J Q K A → 2~9 A B C D E
     *                      最后一位是三张牌中最大值 所对应的花色索引 "Diamond","Club","Heart", "Spade" → 1 2 3 4
*               对于对子型牌AAB:前两位分别是牌值相同的两张AA所对应的16进制数 + 最后一位是单张牌值B所对应的16进制数
     *                      最后一位是单张牌值B 所对应的花色索引 "Diamond","Club","Heart", "Spade" → 1 2 3 4
     */
    private static String getNumAndSuit(List<Card> card){
        StringBuilder numAddSuit = new StringBuilder();
        List<Integer> num = new ArrayList<>();
        int maxIndex = -1;
        Card maxCard = new Card();

        // 得到牌值在numArr数组中的索引的list以及牌值最大的牌
        for(Card tmp:card){
            int i = Arrays.asList(numArr).indexOf(tmp.getNum());
            if(maxIndex <= i){
                if (maxIndex == i)
                {
                    int m = Arrays.asList(suitArr).indexOf(maxCard.getSuit());
                    int n = Arrays.asList(numArr).indexOf(tmp.getSuit());
                    maxCard = m < n ? tmp : maxCard;
                    num.add(i);
                    continue;
                }
                maxIndex = i;
                maxCard = tmp;
            }
            num.add(i);
        }

        Collections.sort(num);

        // ("Diamond":1,"Club":2,"Heart":3, "Spade":4)
        // 如果有对子 比如:K(Heart) QQ 则返回 QQK+K的花色(QQK对应的十六进制数组合的字符串 + 单牌花色对应的十六进制数)  ccd3
        // 非对子牌型 比如:A(Club)  KQ 则返回 AKQ+A的花色(AKQ对应的十六进制数组合的字符串 + 最大牌的花色对应的十六进制数)  edc2
        if(num.get(0) == num.get(1)){
            numAddSuit.append(Integer.toHexString(num.get(0)+2));
            numAddSuit.append(Integer.toHexString(num.get(1)+2));
            numAddSuit.append(Integer.toHexString(num.get(2)+2));

            for(Card c:card){
                if(c.getNum().equals(numArr[num.get(2)])){
                    numAddSuit.append(Arrays.asList(suitArr).indexOf(c.getSuit())+1);
                    break;
                }
            }
        }else if(num.get(1) == num.get(2)){
            numAddSuit.append(Integer.toHexString(num.get(1)+2));
            numAddSuit.append(Integer.toHexString(num.get(2)+2));
            numAddSuit.append(Integer.toHexString(num.get(0)+2));
            for(Card c:card){
                if(c.getNum().equals(numArr[num.get(0)])){
                    numAddSuit.append(Arrays.asList(suitArr).indexOf(c.getSuit())+1);
                    break;
                }
            }
        }else{
            for(int i = num.size()-1;i >= 0;i--){
                numAddSuit.append(Integer.toHexString(num.get(i)+2));
            }
            numAddSuit.append(Arrays.asList(suitArr).indexOf(maxCard.getSuit())+1);
        }
        return numAddSuit.toString();
    }

    /**
     * 得到牌的牌型所对应的整型数级别
     * @param card 需判定的三张牌
     * @return 牌型对应的数字
     */
    public static int getCardsLevel(List<Card> card){
        String level = "";

        List<Integer> num = new ArrayList<>();
        for(Card tmp:card){
            num.add(Arrays.asList(numArr).indexOf(tmp.getNum()));
        }
        Collections.sort(num);

        if(num.get(0) == num.get(1) && num.get(1) == num.get(2)){ // 豹子
            level = "6";
        }else if(card.get(0).getSuit().equals(card.get(1).getSuit())
                && card.get(1).getSuit().equals(card.get(2).getSuit())){ // 同花
            if(num.get(0) == num.get(1)-1
                    && num.get(1) == num.get(2)-1){ // 同花顺
                level = "5";
            }else{ // 普通同花
                level = "4";
            }
        }else if(num.get(0) == num.get(1)-1
                && num.get(1) == num.get(2)-1){ // 顺子
            level = "3";
        }else if(num.get(0) == num.get(1) || num.get(1) == num.get(2)){ // 对子
            level = "2";
        }else{ // 单牌
            level = "1";
        }

        // 牌的级别(数值) = [基础类型(单牌,对子...) + 三张牌的大小及最大牌的花色的组合字符串]所代表的十六进制数的十进制数值
        String numAddSuit = getNumAndSuit(card);
        level = level + numAddSuit;
        return Integer.parseInt(level,16);
    }

    /**
     * 比牌
     * @param card1 用户1的三张牌
     * @param card2 用户2的三张牌
     * @return 若用户1牌大于用户2的牌 则返回1 反之返回-1
     */
    public static int Compare(List<Card> card1, List<Card> card2){
        int m = getCardsLevel(card1);
        int n = getCardsLevel(card2);

        // 不同花色的235>豹子
        if (m > 393216) // 0x60000 = 393216
        {
            if (getNumAndSuit(card2).startsWith("532")
                    && !card2.get(0).getSuit().equals(card2.get(1).getSuit())
                    && !card2.get(1).getSuit().equals(card2.get(2).getSuit())
                    && !card2.get(0).getSuit().equals(card2.get(2).getSuit()))
            {
                return -1;
            }
        }
        else if(n > 393216)
        {
            if (getNumAndSuit(card1).startsWith("532")
                    && !card2.get(0).getSuit().equals(card2.get(1).getSuit())
                    && !card2.get(1).getSuit().equals(card2.get(2).getSuit())
                    && !card2.get(0).getSuit().equals(card2.get(2).getSuit()))
            {
                return 1;
            }
        }
        return m > n ? 1 : -1;
    }
}
